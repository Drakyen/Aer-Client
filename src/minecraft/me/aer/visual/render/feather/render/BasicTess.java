package me.aer.visual.render.feather.render;

import me.aer.visual.render.feather.util.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;


public class BasicTess implements Tessellator {
	private int index;
	private int[] raw;
	private int colors;
	private float texU,texV;
	private ByteBuffer buffer;
	private FloatBuffer fBuffer;
	private IntBuffer iBuffer;
	private boolean color, texture;
	
	public BasicTess(int capacity)
	{
		capacity*=6;
		this.raw = new int[capacity];
		this.buffer = BufferUtils.createDirectBuffer(capacity * 4);
		this.fBuffer = this.buffer.asFloatBuffer();
		this.iBuffer = this.buffer.asIntBuffer();
	}
	
	@Override
	public Tessellator color(int color)
	{
		this.color = true;
		this.colors = color;
		return this;
	}

	@Override
	public Tessellator texture(float u, float v)
	{
		this.texture = true;
		this.texU = u;
		this.texV = v;
		return this;
	}
	
	@Override
	public Tessellator vertex(float x, float y, float z) {
        final int dex = this.index * 6;
        this.raw[dex] = Float.floatToRawIntBits(x);
        this.raw[dex + 1] = Float.floatToRawIntBits(y);
        this.raw[dex + 2] = Float.floatToRawIntBits(z);
        Color col = new Color(colors, true);
        Color flippedCol = new Color(col.getBlue(), col.getGreen(), col.getRed(), col.getAlpha());
        this.raw[dex + 3] = flippedCol.getRGB();
        this.raw[dex + 4] = Float.floatToRawIntBits(this.texU);
        this.raw[dex + 5] = Float.floatToRawIntBits(this.texV);
        this.index++;
        return this;
    }
	
	@Override
	public Tessellator bind()
	{
		final int dex = this.index*6;
		this.iBuffer.put(this.raw, 0, dex);
		this.buffer.position(0);
		this.buffer.limit(dex*4);
		if(this.color)
		{
			this.buffer.position(12);
			GL11.glColorPointer(4, true, 24, this.buffer);
		}
		if(this.texture)
		{
			this.fBuffer.position(4);
			GL11.glTexCoordPointer(2, 24, this.fBuffer);
		}
		this.fBuffer.position(0);
		GL11.glVertexPointer(3, 24, this.fBuffer);
		return this;
	}

	@Override
	public Tessellator draw(int mode)
	{
		GL11.glDrawArrays(mode, 0, this.index);
		return this;
	}

	@Override
	public Tessellator reset()
	{
		this.iBuffer.clear();
		this.index = 0;
		this.color = false;
		this.texture = false;
		return this;
	}

	@Override
	public Tessellator end(int mode)
	{
		return this.bind().draw(mode).reset();
	}

	@Override
	public int[] getRaw()
	{
		return this.raw;
	}

	@Override
	public Tessellator setRaw(int[] raw)
	{
		this.raw = raw;
		return this;
	}

	@Override
	public ByteBuffer getBuffer()
	{
		return this.buffer;
	}
	
	@Override
	public IntBuffer getIBuffer()
	{
		return this.iBuffer;
	}

	@Override
	public Tessellator setBuffer(ByteBuffer buffer)
	{
		this.buffer = buffer;
		this.iBuffer = buffer.asIntBuffer();
		this.fBuffer = buffer.asFloatBuffer();
		return this;
	}

	@Override
	public int getIndex() 
	{
		return this.index;
	}
	
	@Override
	public void setIndex(int index) 
	{
		this.index = index;
	}
}