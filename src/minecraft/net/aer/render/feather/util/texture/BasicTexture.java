package net.aer.render.feather.util.texture;

import net.aer.render.feather.render.Tessellator;
import org.lwjgl.opengl.GL11;


public class BasicTexture implements Texture {
	private int texID;
	private float u, v, u1, v1, width, height;

	@Override
	public void load() {
		texID = GL11.glGenTextures();
	}

	@Override
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
	}

	@Override
	public void draw(Tessellator tess, int mode, float x, float y) {
		tess.texture(u+u1, v).vertex(x+width, y, 0).texture(u, v).vertex(x, y, 0);
		tess.texture(u, v1).vertex(x, y+height, 0).texture(u1, v1).vertex(x+width, y+height, 0).bind();
		tess.draw(mode).reset();
	}
	
	@Override
	public int getID() {
		return this.texID;
	}

	@Override
	public float getU() {
		return this.u;
	}

	@Override
	public float getV() {
		return this.v;
	}
	
	@Override
	public float getU1() {
		return this.u1;
	}

	@Override
	public float getV1() {
		return this.v1;
	}

	@Override
	public float getWidth() {
		return this.width;
	}

	@Override
	public float getHeight() {
		return this.height;
	}
	
	@Override
	public void setID(int id) {
		this.texID = id;
	}
	
	@Override
	public void setU(float u) {
		this.u = u;
	}

	@Override
	public void setV(float v) {
		this.v = v;
	}
	
	@Override
	public void setU1(float u1) {
		this.u1 = u1;
	}

	@Override
	public void setV1(float v1) {
		this.v1 = v1;
	}

	@Override
	public void setWidth(float width) {
		this.width = width;
	}

	@Override
	public void setHeight(float height) {
		this.height = height;
	}
}