package me.aer.visual.render.feather.util;

import me.aer.visual.render.feather.util.texture.BasicTexture;
import me.aer.visual.render.feather.util.texture.Texture;

import java.nio.IntBuffer;


public final class TextureUtils implements Utils {
    public IntBuffer buffer = BufferUtils.createDirectBuffer(262144).asIntBuffer();

    public Texture createTexture() {
        return new BasicTexture();
    }

    public Texture createTexture(float u, float v, float u1, float v1, float width, float height) {
        final Texture texture = this.createTexture();
        texture.setU(u);
        texture.setV(v);
        texture.setU1(u1);
		texture.setV1(v1);
		texture.setWidth(width);
		texture.setHeight(height);
		return texture;
	}
	
	public Texture createTexture(float u, float v, float width, float height, float dimensions) {
		return this.createTexture(u/dimensions, v/dimensions, (u+width)/dimensions, (v+height)/dimensions, width, height);
	}
	
	public Texture createTexture(int id, float u, float v, float width, float height, float dimensions) {
		final Texture texture = this.createTexture(u, v, width, height, dimensions);
		texture.setID(id);
		return texture;
	}
}