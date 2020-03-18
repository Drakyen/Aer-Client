package me.aerclient.visual.render.render2D;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import me.aerclient.implementation.utils.Utilities;
import me.aerclient.visual.render.feather.render.FactorTess;
import me.aerclient.visual.render.feather.render.Tessellator;
import me.aerclient.visual.render.feather.util.BufferUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.UUID;

public class RenderUtils2D implements Utilities {

    public float factor;
    public static final FactorTess tess;

    public RenderUtils2D() {
        this.factor = 0.5f;
    }

    static {
        tess = new FactorTess(256);
    }

    public void drawString(CustomFontRenderer renderer, String text, float x, float y, int colour, boolean shadow) {
        GlStateManager.enableTexture2D();
        if (shadow) {
            renderer.drawStringWithShadow(text, Math.round(x), Math.round(y), colour);
        } else {
            renderer.drawString(text, Math.round(x), Math.round(y), colour);
        }
        GlStateManager.disableTexture2D();
    }

    public void drawTotalCenteredString(CustomFontRenderer renderer, String text, float x, float y, int colour, boolean shadow) {
        ScaledResolution res = new ScaledResolution(minecraft);
        float x1 = res.getScaledWidth() / 2;
        float y1 = res.getScaledHeight() / 2;
        x1 += x;
        y1 += y;
        drawCenteredString(renderer, text, x1, y1, colour, shadow);
    }


    public void enableBlending() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
    }

    public void disableBlending() {
        GlStateManager.disableBlend();
        GL11.glDisable(2848);
    }

    public void enableLineBlending() {
        this.enableBlending();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
    }

    public void enableDepth() {
        GlStateManager.enableDepth();
        GL11.glDepthMask(true);
    }

    public void disableDepth() {
        GlStateManager.disableDepth();
        GL11.glDepthMask(false);
    }

    public void enableLighting() {
        GlStateManager.enableAlpha();
        GlStateManager.enableLighting();
    }

    public void disableLighting() {
        GlStateManager.disableLighting();
        GlStateManager.disableAlpha();
    }

    public void enableArray(int array) {
        GL11.glEnableClientState(array);
    }

    public void drawCenteredString(CustomFontRenderer renderer, String text, float x, float y, int colour, boolean shadow) {
        GlStateManager.enableTexture2D();
        x -= Math.round(renderer.getStringWidth(text) / 2);
        y -= Math.round(renderer.getStringHeight(text) / 2);
        if (shadow) {
            renderer.drawStringWithShadow(text, Math.round(x), Math.round(y), colour);
        } else {
            renderer.drawString(text, Math.round(x), Math.round(y), colour);
        }
        GlStateManager.disableTexture2D();
    }

    public void enableGradient() {
        this.enableArray(GL11.GL_COLOR_ARRAY);
        GL11.glShadeModel(GL11.GL_SMOOTH);
    }

    public void disableArray(int array) {
        GL11.glDisableClientState(array);
    }

    public void bindBuffer(int buffer) {
        GL15.glBindBuffer(34962, buffer);
    }

    public void disableGradient() {
        this.disableArray(GL11.GL_COLOR_ARRAY);
        GL11.glShadeModel(GL11.GL_FLAT);
    }

    public RenderUtils2D setColor(int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
        return this;
    }

    public RenderUtils2D setColor(int color, float opacity) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha * opacity);
        return this;
    }

    public void drawRect(float x, float y, float x1, float y1, int color) {
        setColor(color).drawRectangle(GL11.GL_QUADS, x, y, x1, y1);
    }

    public void drawRectangle(int mode, float x, float y, float x1, float y1) {
        enableBlending();
        this.bindRect(RenderUtils2D.tess, x, y, x1, y1);
        RenderUtils2D.tess.draw(mode).reset();
    }

    public void bindRect(Tessellator rend, float x, float y, float x1, float y1) {
        rend.vertex(x, y, 0.0f).vertex(x, y1, 0.0f).vertex(x1, y1, 0.0f).vertex(x1, y, 0.0f).bind();
    }

    public void drawHorizontalLine(int startX, int endX, int y, int color) {
        if (endX < startX) {
            int i = startX;
            startX = endX;
            endX = i;
        }
        setColor(color).drawRectangle(GL11.GL_QUADS, startX, y, endX + 1, y + 1);
    }

    public void drawVerticalLine(int x, int startY, int endY, int color) {
        if (endY < startY) {
            int i = startY;
            startY = endY;
            endY = i;
        }

        setColor(color).drawRectangle(GL11.GL_QUADS, x, startY, x + 1, endY);
    }

    public void drawGradVert(int mode, int left, int top, int right, int bottom, int topColor, int bottomColor) {
        this.bindGradVert(tess, left, top, right, bottom, topColor, bottomColor);
        this.enableGradient();
        RenderUtils2D.tess.draw(mode).reset();
        this.disableGradient();
    }

    public void drawGradHoriz(int mode, int left, int top, int right, int bottom, int leftColor, int rightColor) {
        this.bindHorGrad(tess, left, top, right, bottom, leftColor, rightColor);
        this.enableGradient();
        RenderUtils2D.tess.draw(mode).reset();
        this.disableGradient();
    }

    public void bindGradVert(Tessellator rend, float x, float y, float x1, float y1, int topColor, int bottomColor) {
        rend.color(topColor).vertex(x1, y, 0.0f).vertex(x, y, 0.0f);
        rend.color(bottomColor).vertex(x, y1, 0.0f).vertex(x1, y1, 0.0f).bind();
    }

    public void bindHorGrad(Tessellator rend, int x, int y, int x1, int y1, int leftColor, int rightColor) {
        rend.color(leftColor).vertex(x, y, 0.0f).vertex(x, y1, 0.0f);
        rend.color(rightColor).vertex(x1, y1, 0.0f).vertex(x1, y, 0.0f).bind();
    }

    public void drawTex(int mode, float x, float y, float u, float v, float width, float height) {
        enableBlending();
        this.bindTex(tess, x, y, u, v, width, height, 256.0f);
        GlStateManager.scale(this.factor, this.factor, this.factor);
        RenderUtils2D.tess.draw(mode).reset();
        GlStateManager.scale(1.0f / this.factor, 1.0f / this.factor, 1.0f / this.factor);
    }

    public void bindTex(Tessellator rend, float x, float y, float u, float v, float width, float height, float img) {
        x /= this.factor;
        y /= this.factor;
        rend.texture((u + width) / img, v / img).vertex(x + width, y, 0.0f).texture(u / img, v / img).vertex(x, y, 0.0f);
        rend.texture(u / img, (v + height) / img).vertex(x, y + height, 0.0f).texture((u + width) / img, (v + height) / img).vertex(x + width, y + height, 0.0f).bind();
    }

    public void bindColor(Tessellator tess, int r, int g, int b, int a) {
        if (BufferUtils.isBigEndian)
            tess.color(r << 24 | g << 16 | b << 8 | a);
        else
            tess.color(a << 24 | b << 16 | g << 8 | r);
    }

//    public boolean isOver(int i, int j, int x, int y, int x1, int y1) {
//        return i >= x && i <= x1 && j >= y && j <= y1;
//    }
//
//    public boolean isOverBySize(int i, int j, int x, int y, int width, int height) {
//        return i >= x && i <= x+width && j >= y && j <= y+height;
//    }

    public void drawItem(int xPos, int yPos, ItemStack stack) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        final RenderItem iRend = minecraft.getRenderItem();
        iRend.zLevel = -150.0F;
        this.enableDepth();
        GlStateManager.clear(256);
        iRend.renderItemAndEffectIntoGUI(stack, xPos + 2, yPos + 2);
        iRend.renderItemOverlays(minecraft.fontRendererObj, stack, xPos + 2, yPos + 2);
        minecraft.getTextureManager().bindTexture(minecraft.fontRendererObj.locationFontTexture);
        minecraft.getRenderItem().zLevel = 0.0F;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public void bindColor(Tessellator tess, float r, float g, float b, float a) {
        this.bindColor(tess, (int) (r * 255.0f), (int) (g * 255.0f), (int) (b * 255.0f), (int) (a * 255.0f));
    }

    private int loadTexture(String location, int size) {
        try {
            final int tex = GL11.glGenTextures();
            final BufferedImage img = ImageIO.read(RenderUtils2D.class.getResourceAsStream("/assets/minecraft/" + location));
            GL11.glBindTexture(3553, tex);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9987);
            GL11.glTexParameteri(3553, 33169, 1);

            GL11.glTexImage2D(3553, 0, 6408, size, size, 0, 32993, 5121, (IntBuffer) null);
            final int[] data = new int[size * size];
            img.getRGB(0, 0, size, size, data, 0, size);
            textureUtils.buffer = BufferUtils.createDirectBuffer((size * size) * 4).asIntBuffer();
            textureUtils.buffer.clear();
            textureUtils.buffer.put(data);
            textureUtils.buffer.flip();
            GL11.glTexSubImage2D(3553, 0, 0, 0, size, size, 32993, 5121, textureUtils.buffer);
            return tex;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int loadTexture64(String location) {
        return loadTexture(location, 64);
    }

    public int loadTexture128(String location) {
        return loadTexture(location, 128);
    }

    public int loadTexture256(String location) {
        return loadTexture(location, 256);
    }

    public String getHiddenString(String str) {
        return StringUtils.repeat('*', str.length());
    }

    public void downloadSkin(GameProfile profile) {
        ResourceLocation var10 = DefaultPlayerSkin.getDefaultSkinLegacy();
        if (profile != null) {
            Minecraft var2 = Minecraft.getMinecraft();
            Map var3 = var2.getSkinManager().loadSkinFromCache(profile);
            if (var3.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                var10 = var2.getSkinManager().loadSkin((MinecraftProfileTexture) var3.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
            } else {
                UUID var4 = EntityPlayer.getUUID(profile);
                var10 = DefaultPlayerSkin.getDefaultSkin(var4);
            }
        }
        minecraft.getTextureManager().bindTexture(var10);
    }

    public void downloadSkin(String name) {
        name = net.minecraft.util.StringUtils.stripControlCodes(name);
        final ResourceLocation localSkin = new ResourceLocation("skins/" + name);
        final TextureManager var4 = minecraft.getTextureManager();
        Object var2 = var4.getTexture(localSkin);
        if (var2 == null) {
            var2 = new ThreadDownloadImageData(null,
                    String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", name),
                    DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getOfflineUUID(name)), new ImageBufferDownload());
            var4.loadTexture(localSkin, (ITextureObject) var2);
        }
        var4.bindTexture(localSkin);
    }

    public void setIcon(String path) {
        final Util.EnumOS var1 = Util.getOSType();
        if (var1 != Util.EnumOS.OSX) {
            InputStream var2 = null;
            InputStream var3 = null;
            InputStream var4 = null;
            try {
                var2 = RenderUtils2D.class.getResourceAsStream(path + "icon_16x16.png");
                var3 = RenderUtils2D.class.getResourceAsStream(path + "icon_32x32.png");
                var4 = RenderUtils2D.class.getResourceAsStream(path + "icon_64x64.png");
                if (var2 != null && var3 != null) {
                    Display.setIcon(new ByteBuffer[]{this.readImageToBuffer(var2), this.readImageToBuffer(var3), this.readImageToBuffer(var4)});
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(var2);
                IOUtils.closeQuietly(var3);
                IOUtils.closeQuietly(var4);
            }
        }
    }

    public int loadTexture512(String location) {
        return loadTexture(location, 512);
    }

    private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
        BufferedImage var2 = ImageIO.read(imageStream);
        int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), null, 0, var2.getWidth());
        ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
        int[] var5 = var3;
        int var6 = var3.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            int var8 = var5[var7];
            var4.putInt(var8 << 8 | var8 >> 24 & 255);
        }

        var4.flip();
        return var4;
    }

    public String getShaderLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, 35716));
    }

    public int createShader(String shaderCode, int shaderType) {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
            if (shader == 0) {
                return 0;
            }
            ARBShaderObjects.glShaderSourceARB(shader, shaderCode);
            ARBShaderObjects.glCompileShaderARB(shader);
            if (ARBShaderObjects.glGetObjectParameteriARB(shader, 35713) == 0) {
                throw new RuntimeException("Error creating shader: " + this.getShaderLogInfo(shader));
            }
            return shader;
        } catch (Exception exc) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            return 0;
        }
    }

}
