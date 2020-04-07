package me.aer.visual.render.feather.render;

import me.aer.visual.render.feather.util.BufferUtils;
import me.aer.visual.render.feather.util.texture.Texture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.util.List;
import java.util.*;

public class TextRend {
	private static Color CLEAR = new Color(255, 255, 255, 1);
	private final GrowingTess rend;
	private final List<WeakHashMap<String, Entry>> caches;
	private final LinkedHashMap<Font, Integer> fontCache;
	private final LinkedHashMap<Long, Texture> glyphCache;
	private final BufferedImage glyphImage;
	private final int[] imageData;
	private final IntBuffer imageBuffer;
	private final Graphics2D glyphGraphics;
	private final FontRenderContext fontContext;
	private BufferedImage stringImage;
	private Graphics2D stringGraphics;
	private int cacheX, cacheY, cacheLineHeight;
	private int texture;
	private Font font;
	private int size;
	private int style;
	private boolean antialias;
	private boolean pending;
	public float posX;
	public float posY;
	public int advance;
	
	public TextRend(String font, int fontStyle, int fontSize, boolean antialias) {
		this();
		this.setFont(font, fontStyle, fontSize, antialias);
	}
	
	public TextRend() {
		this.rend = new GrowingTess(new BasicTess(4));
		this.caches = new ArrayList<WeakHashMap<String, Entry>>();
		this.caches.add(new WeakHashMap<String, Entry>());
		this.fontCache = new LinkedHashMap<Font, Integer>();
		this.glyphCache = new LinkedHashMap<Long, Texture>();
		this.glyphImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
		this.imageData = new int[65536];
		this.imageBuffer = BufferUtils.createDirectBuffer(262144).asIntBuffer();
		this.glyphGraphics = this.glyphImage.createGraphics();
		this.glyphGraphics.setBackground(CLEAR);
		this.glyphGraphics.setComposite(AlphaComposite.Src);
		this.allocateTexture();
		this.allocateStringImage(256, 64);
		
		this.fontContext = this.glyphGraphics.getFontRenderContext();
		GraphicsEnvironment.getLocalGraphicsEnvironment().preferLocaleFonts();
		this.size = 18;
		this.font = new Font(Font.SANS_SERIF, 0, this.size);
	}
	
	public void setSize(int fontSize) {
		this.size = fontSize;
		this.pending = true;
	}
	
	public void setStyle(int fontStyle) {
		this.style = fontStyle;
		this.pending = true;
	}
	
	public void setAntialias(boolean antialias) {
		this.antialias = antialias;
		this.setRenderingHints();
		this.pending = true;
	}
	
	public void setFont(Font font) {
		this.font = font;
		this.pending = true;
	}
	
	public void setFont(String font, int fontStyle, int fontSize, boolean antialias) {
		this.setSize(fontSize);
		this.setStyle(fontStyle);
		this.setAntialias(antialias);
		this.setFont(new Font(font, 0, fontSize));
	}
	
	public Font getFont() {
		return this.pending ? this.cacheFont() : this.font;
	}
	
	private Font cacheFont() {
		this.pending = false;
		return this.font = this.font.deriveFont(this.style, this.size);
	}
	
	public TextRend renderString(String str, float x, float y) {
		final Entry entry = this.cacheString(str);
		int boundTex = 0;
		for(Glyph glyph : entry.glyphs) {
			final Texture texture = glyph.texture;
			if(boundTex != texture.getID()) {
				if(boundTex != 0)
					rend.end(GL11.GL_QUADS);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
				boundTex = texture.getID();
			}
			final float x1 = x + glyph.x;
			final float x2 = x1 + texture.getWidth();
			final float y1 = y + glyph.y;
			final float y2 = y1 + texture.getHeight();
			rend.texture(texture.getU(), texture.getV()).vertex(x1, y1, 0);
			rend.texture(texture.getU(), texture.getV1()).vertex(x1, y2, 0);
			rend.texture(texture.getU1(), texture.getV1()).vertex(x2, y2, 0);
			rend.texture(texture.getU1(), texture.getV()).vertex(x2, y1, 0);
		}
		rend.end(GL11.GL_QUADS);
		this.posX = x;
		this.posY = y;
		this.advance = entry.width;
		return this;
	}
	
	public int getStringWidth(String str) {
		return this.cacheString(str).width;
	}
	
	protected Entry cacheString(String str) {
		Integer font = this.fontCache.get(this.getFont());
		if(font == null) {
			font = this.fontCache.size();
			this.fontCache.put(this.font, font);
			this.caches.add(new WeakHashMap<String, Entry>());
		}
		final WeakHashMap<String, Entry> stringCache = this.caches.get(font);
		Entry entry = stringCache.get(str);
		if(entry == null) {
            entry = new Entry(str);
            final char[] text = entry.str.toCharArray();
            entry.glyphs = new Glyph[text.length];
            entry.width = this.cacheGlyphs(entry.glyphs, text);
            Arrays.sort(entry.glyphs);

            stringCache.put(entry.str, entry);
        }
		return entry;
	}
	
	private int cacheGlyphs(Glyph[] glyphs, char[] text) {
		final GlyphVector vector = this.font.layoutGlyphVector(this.fontContext, text, 0, text.length, Font.LAYOUT_LEFT_TO_RIGHT);
		final GlyphVector vector2 = this.font.layoutGlyphVector(this.fontContext, text, 0, text.length, Font.LAYOUT_LEFT_TO_RIGHT);
		Rectangle vectorBounds = null;
		Rectangle dirty = null;
		boolean vectorRendered = false;
		final long fontKey = (long) fontCache.get(this.font) << 32;
		final int numGlyphs = vector.getNumGlyphs();
		for(int index = 0; index < numGlyphs; index++) {
			final int glyphCode = vector.getGlyphCode(index);
			Texture tex = this.glyphCache.get(fontKey | glyphCode);
			if(tex == null) {
				if(!vectorRendered) {
					vectorRendered = true;
					for(int i = 0; i < numGlyphs; i++) {
						final Point2D pos = vector.getGlyphPosition(i);
	                    pos.setLocation(pos.getX() + 2 * i, pos.getY());
	                    vector.setGlyphPosition(i, pos);
					}
					vectorBounds = vector.getPixelBounds(this.fontContext, 0, 0);
					if(this.stringImage == null || vectorBounds.width > this.stringImage.getWidth() || vectorBounds.height > this.stringImage.getHeight())
	                    this.allocateStringImage(Math.max(vectorBounds.width, this.stringImage.getWidth()), Math.max(vectorBounds.height, this.stringImage.getHeight()));
					this.stringGraphics.clearRect(0, 0, vectorBounds.width, vectorBounds.height);
					this.stringGraphics.drawGlyphVector(vector, -vectorBounds.x, -vectorBounds.y);
				}
				final Rectangle rect = vector.getGlyphPixelBounds(index, null, -vectorBounds.x, -vectorBounds.y);
				if(this.cacheX + rect.width + 1 > 256) {
	                this.cacheX = 1;
	                this.cacheY += cacheLineHeight + 1;
	                this.cacheLineHeight = 0;
	            }
				if(this.cacheY + rect.height + 1 > 256) {
	                this.updateTexture(dirty);
	                dirty = null;

	                this.allocateTexture();
	                this.cacheY = this.cacheX = 1;
	                cacheLineHeight = 0;
	            }
	            this.cacheLineHeight = Math.max(rect.height, this.cacheLineHeight);
	            this.glyphGraphics.drawImage(this.stringImage, this.cacheX, this.cacheY, this.cacheX + rect.width, this.cacheY + rect.height, rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, null);
	            rect.setLocation(this.cacheX, this.cacheY);
	            tex = Texture.tex.createTexture(this.texture, rect.x, rect.y, rect.width, rect.height, 256);
	            this.glyphCache.put(fontKey | glyphCode, tex);
	            if(dirty == null)
	                dirty = new Rectangle(this.cacheX, this.cacheY, rect.width, rect.height);
	            else 
	            	dirty.add(rect);
	            this.cacheX += rect.width + 1;
			}
			final Point point = vector2.getGlyphPixelBounds(index, null, 0, 0).getLocation();
			glyphs[index] = new Glyph(tex, point.x, point.y);
		}
		updateTexture(dirty);
		return (int)vector2.getGlyphPosition(numGlyphs).getX();
	}
	
	private void updateTexture(Rectangle dirty){
        if(dirty != null) {
            this.updateBuffer(dirty.x, dirty.y, dirty.width, dirty.height);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture);
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, dirty.x, dirty.y, dirty.width, dirty.height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageBuffer);
        }
    }
	
	private void allocateStringImage(int width, int height) {
		this.stringImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.stringGraphics = this.stringImage.createGraphics();
		this.setRenderingHints();
		this.stringGraphics.setBackground(CLEAR);
		this.stringGraphics.setPaint(Color.WHITE);
	}
	
	private void setRenderingHints() {
		stringGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.antialias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
	    stringGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.antialias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	    stringGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
	}
	
	private void allocateTexture() {
		this.glyphGraphics.clearRect(0, 0, 256, 256);
		this.texture = GL11.glGenTextures();
		this.updateBuffer(0, 0, 256, 256);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, 256, 256, 0, GL12.GL_BGRA, GL11.GL_UNSIGNED_BYTE, this.imageBuffer);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	}
	
	private void updateBuffer(int x, int y, int width, int height) {
		this.glyphImage.getRGB(x, y, width, height, this.imageData, 0, width);
		this.imageBuffer.clear();
		this.imageBuffer.put(this.imageData);
		this.imageBuffer.flip();
	}
	
	//
	
	protected static class Entry {
		public final String str;
		public Glyph[] glyphs;
		public int width;
		public Entry(String str) {
			this.str = str;
		}
	}
	
	protected static class Glyph implements Comparable<Glyph> {
		final int x, y;
		public final Texture texture;
		public Glyph(Texture texture, int x, int y) {
			this.texture = texture;
			this.x = x;
			this.y = y;
		}
		@Override
        public int compareTo(Glyph o) {
            return (this.texture.getID() == o.texture.getID()) ? 0 : 1;
        }
	}
}