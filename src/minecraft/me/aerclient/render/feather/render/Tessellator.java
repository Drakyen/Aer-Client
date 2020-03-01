package me.aerclient.render.feather.render;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public abstract interface Tessellator {
	public Tessellator color(int color);
	public Tessellator texture(float u, float v);
	public Tessellator vertex(float x, float y, float z);
	public Tessellator draw(int mode);
	public Tessellator bind();
	public Tessellator reset();
	public Tessellator end(int mode);
	public int[] getRaw();
	public Tessellator setRaw(int[] raw);
	public ByteBuffer getBuffer();
	public IntBuffer getIBuffer();
	public Tessellator setBuffer(ByteBuffer buffer);
	public int getIndex();
	public void setIndex(int index);
}