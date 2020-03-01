package me.aerclient.render.render2D;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import me.aerclient.Aer;
import me.aerclient.render.feather.render.*;
import me.aerclient.render.feather.util.*;
import me.aerclient.utils.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
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
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.UUID;

public class RenderUtils2D implements Utilities {

    public final Shapes shapes;
    public float factor;
    public static final FactorTess tess;

    public RenderUtils2D() {
        this.shapes = new Shapes();
        this.factor = 0.5f;
    }

    public static void drawString(CustomFontRenderer renderer, String text, float x, float y, int colour, boolean shadow) {
        if (shadow) {
            renderer.drawStringWithShadow(text, Math.round(x), Math.round(y), colour);
        } else {
            renderer.drawString(text, Math.round(x), Math.round(y), colour);
        }
    }

    public static void drawCenteredString(CustomFontRenderer renderer, String text, float x, float y, int colour, boolean shadow) {
        x -= Math.round(renderer.getStringWidth(text) / 2);
        y -= Math.round(renderer.getStringHeight(text) / 2);
        if (shadow) {
            renderer.drawStringWithShadow(text, Math.round(x), Math.round(y), colour);
        } else {
            renderer.drawString(text, Math.round(x), Math.round(y), colour);
        }
    }

    public void drawTotalCenteredString(CustomFontRenderer renderer, String text, float x, float y, int colour, boolean shadow) {
        ScaledResolution res = new ScaledResolution(minecraft);
        float x1 = res.getScaledWidth() / 2;
        float y1 = res.getScaledHeight() / 2;
        x1 += x;
        y1 += y;
        drawCenteredString(renderer, text, x1, y1, colour, shadow);
    }

    public static void drawHorizontalLine(int startX, int endX, int y, int color) {
        if (endX < startX) {
            int i = startX;
            startX = endX;
            endX = i;
        }

        drawRect(startX, y, endX + 1, y + 1, color, 0f);
    }

    public static void drawVerticalLine(int x, int startY, int endY, int color) {
        if (endY < startY) {
            int i = startY;
            startY = endY;
            endY = i;
        }

        drawRect(x, startY + 1, x + 1, endY, color, 0f);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        drawRect(left, top, right, bottom, color, 0f);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color, float zLevel) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glColor4f(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double) left, (double) bottom, zLevel).endVertex();
        bufferbuilder.pos((double) right, (double) bottom, zLevel).endVertex();
        bufferbuilder.pos((double) right, (double) top, zLevel).endVertex();
        bufferbuilder.pos((double) left, (double) top, zLevel).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawSquare(double x, double y, double size, int color) {
        double left = x - (size / 2);
        double right = x + (size / 2);
        double top = y - (size / 2);
        double bottom = y + (size / 2);

        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double) left, (double) bottom, 0.0D).endVertex();
        bufferbuilder.pos((double) right, (double) bottom, 0.0D).endVertex();
        bufferbuilder.pos((double) right, (double) top, 0.0D).endVertex();
        bufferbuilder.pos((double) left, (double) top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawGradientRectVert(int left, int top, int right, int bottom, float zLevel, int startColor, int endColor) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) right, (double) top, (double) zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) left, (double) top, (double) zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) left, (double) bottom, (double) zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double) right, (double) bottom, (double) zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradientRectHoriz(int left, int top, int right, int bottom, float zLevel, int startColor, int endColor) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) right, (double) bottom, (double) zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) right, (double) top, (double) zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) left, (double) top, (double) zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double) left, (double) bottom, (double) zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }


    public static void drawScaledCustomTexture(String texturePath, int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        minecraft.getTextureManager().bindTexture(new ResourceLocation(texturePath));
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) width) * f), (double) ((v + (float) height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) width) * f), (double) (v * f1)).endVertex();
        bufferbuilder.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator.draw();
    }


    public static void drawScaledCustomTexture(String texturePath, int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        minecraft.getTextureManager().bindTexture(new ResourceLocation(texturePath));
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) vHeight) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) uWidth) * f), (double) ((v + (float) vHeight) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) uWidth) * f), (double) (v * f1)).endVertex();
        bufferbuilder.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator.draw();
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

    public void enableGradient() {
        this.enableArray(32886);
        GL11.glShadeModel(7425);
    }

    public void disableGradient() {
        this.disableArray(32886);
        GL11.glShadeModel(7424);
    }

    public void disableArray(int array) {
        GL11.glDisableClientState(array);
    }

    public void bindBuffer(int buffer) {
        GL15.glBindBuffer(34962, buffer);
    }

    public void setColor(int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }

    public void setColor(int color, float opacity) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha * opacity);
    }

//    public void drawRect(int mode, float x, float y, float x1, float y1) {
//        this.bindRect(RenderUtils2D.tess, x, y, x1, y1);
//        RenderUtils2D.tess.draw(mode);
//    }
//
//    public void bindRect(Testing.feather.render.Tessellator rend, float x, float y, float x1, float y1) {
//        rend.vertex(x, y, 0.0f).vertex(x, y1, 0.0f).vertex(x1, y1, 0.0f).vertex(x1, y, 0.0f).bind();
//    }

//    public void drawGrad(int mode, float x, float y, float x1, float y1, int topColor, int bottomColor) {
//        this.bindGrad(RenderUtils2D.tess, x, y, x1, y1, topColor, bottomColor);
//        this.enableGradient();
//        RenderUtils2D.tess.draw(mode);
//        this.disableGradient();
//    }
//
//    public void bindGrad(Testing.feather.render.Tessellator rend, float x, float y, float x1, float y1, int topColor, int bottomColor) {
//        rend.color(topColor).vertex(x1, y, 0.0f).vertex(x, y, 0.0f);
//        rend.color(bottomColor).vertex(x, y1, 0.0f).vertex(x1, y1, 0.0f).bind();
//    }
//
//    public void bindHorGrad(Testing.feather.render.Tessellator rend, int x, int y, int x1, int y1, int leftColor, int rightColor) {
//        rend.color(leftColor).vertex(x, y, 0.0f).vertex(x, y1, 0.0f);
//        rend.color(rightColor).vertex(x1, y1, 0.0f).vertex(x1, y, 0.0f).bind();
//    }

    public void drawCircle(me.aerclient.render.feather.render.Tessellator tess, int mode, float x, float y, float radius, float degree, float quality) {
        final float twicePi = 2F * (float) Math.PI;
        if (degree != 1)
            tess.vertex(x, y, 0);
        for (int i = MathHelper.ceil(quality * degree); i > -1; i--)
            tess.vertex(x + (radius * MathHelper.cos(i * twicePi / quality)), y + (radius * MathHelper.sin(i * twicePi / quality)), 0);
        tess.end(mode);
    }

//    public void drawTex(int mode, float x, float y, float u, float v, float width, float height) {
//        this.bindTex(RenderUtils2D.tess, x, y, u, v, width, height, 256.0f);
//        GlStateManager.scale(this.factor, this.factor, this.factor);
//        RenderUtils2D.tess.draw(mode);
//        GlStateManager.scale(1.0f / this.factor, 1.0f / this.factor, 1.0f / this.factor);
//    }
//
//    public void bindTex(Testing.feather.render.Tessellator rend, float x, float y, float u, float v, float width, float height, float img) {
//        x /= this.factor;
//        y /= this.factor;
//        rend.texture((u + width) / img, v / img).vertex(x + width, y, 0.0f).texture(u / img, v / img).vertex(x, y, 0.0f);
//        rend.texture(u / img, (v + height) / img).vertex(x, y + height, 0.0f).texture((u + width) / img, (v + height) / img).vertex(x + width, y + height, 0.0f).bind();
//    }

//    public void bindColor(Testing.feather.render.Tessellator tess, int r, int g, int b, int a) {
//        if (BufferUtils.isBigEndian)
//            tess.color(r << 24 | g << 16 | b << 8 | a);
//        else
//            tess.color(a << 24 | b << 16 | g << 8 | r);
//    }
//
//    public void bindColor(Testing.feather.render.Tessellator tess, float r, float g, float b, float a) {
//        this.bindColor(tess, (int) (r * 255.0f), (int) (g * 255.0f), (int) (b * 255.0f), (int) (a * 255.0f));
//    }

//    public float getEntityRatio(Entity e) {
//        return Math.min(e.ticksExisted, 20.0f) / 20.0f;
//    }
//
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
        this.disableDepth();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public int loadTexture(String location) {
        try {
            final int tex = GL11.glGenTextures();
            final BufferedImage img = ImageIO.read(RenderUtils2D.class.getResourceAsStream("/assets/minecraft/" + location));
            GL11.glBindTexture(3553, tex);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10241, 9987);
            GL11.glTexParameteri(3553, 33169, 1);
            GL11.glTexImage2D(3553, 0, 6408, 256, 256, 0, 32993, 5121, (IntBuffer) null);
            final int[] data = new int[65536];
            img.getRGB(0, 0, 256, 256, data, 0, 256);
            TextureUtils.buffer.clear();
            TextureUtils.buffer.put(data);
            TextureUtils.buffer.flip();
            GL11.glTexSubImage2D(3553, 0, 0, 0, 256, 256, 32993, 5121, TextureUtils.buffer);
            return tex;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
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

    private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
        BufferedImage var2 = ImageIO.read(imageStream);
        int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), (int[]) null, 0, var2.getWidth());
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

    public int createShader(String shaderCode, int shaderType) {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
            if (shader == 0) {
                return 0;
            }
            ARBShaderObjects.glShaderSourceARB(shader, (CharSequence) shaderCode);
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

    public String getShaderLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, 35716));
    }

    static {
        tess = new FactorTess(4);
    }

    public static class Shapes {
        public final Shape cube;
        public final Shape pyramid;
        public final Shape octosquare;

        private Shapes() {
            (this.cube = new Shape(3, new byte[]{0, 3, 2, 1, 2, 5, 6, 1, 6, 7, 0, 1, 0, 7, 4, 3, 4, 7, 6, 5, 2, 3, 4, 5})).compile(this.getBox(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
            final float root = (float) (Math.sqrt(3.0) / 2.0);
            final float a = 0.5f / root;
            final float b = a / 2.0f;
            final float c = (float) Math.sqrt(root * root - b * b) / 2.0f;
            (this.pyramid = new Shape(3, new byte[]{3, 1, 0, 3, 0, 2, 0, 1, 2, 3, 2, 1})).compile(0.0f, -c, a, -0.5f, -c, -b, 0.5f, -c, -b, 0.0f, c, 0.0f);
            (this.octosquare = new Shape(2, new byte[]{0, 9, 6, 3})).compile(-0.5f, -0.5f, -0.3f, -0.5f, 0.3f, -0.5f, 0.5f, -0.5f, 0.5f, -0.3f, 0.5f, 0.3f, 0.5f, 0.5f, 0.3f, 0.5f, -0.3f, 0.5f, -0.5f, 0.5f, -0.5f, 0.3f, -0.5f, -0.3f);
        }

        private FloatBuffer getBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
            FloatBuffer vertices = BufferUtils.createDirectBuffer(96).asFloatBuffer();
            vertices.put(new float[]{minX, minY, minZ, maxX, minY, minZ, maxX, maxY, minZ, minX, maxY, minZ, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, minY, maxZ, minX, minY, maxZ}).flip();
            return vertices;
        }

        public class Shape extends Vbo {
            private FloatBuffer points;
            private final ByteBuffer order;

            private Shape(int dimensions, byte[] order) {
                super(dimensions, GL15.glGenBuffers());
                this.points = null;
                this.order = BufferUtils.createDirectBuffer(order.length);
                this.order.put(order).flip();
            }

            @Override
            public void compile(FloatBuffer buffer) {
                super.compile(buffer);
                this.points = buffer;
            }

            @Override
            public void bind() {
                if (OpenGlHelper.useVbo())
                    super.bind();
                else {
                    GL11.glVertexPointer(this.dimensions, 0, this.points);
                    GL11.glEnableClientState(32884);
                }
            }

            @Override
            public void draw(int mode) {
                this.draw(mode, this.order);
            }
        }
    }
}
