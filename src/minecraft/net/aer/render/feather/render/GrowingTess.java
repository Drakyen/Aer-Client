package net.aer.render.feather.render;

import net.aer.render.feather.util.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;


public class GrowingTess implements Tessellator
{
	private final Tessellator tess;
	private final float ratio, factor;
	
	public GrowingTess(Tessellator tess)
	{
		this(tess, 1, 2);
	}
	
	public GrowingTess(Tessellator tess, float ratio, float factor)
	{
		this.tess = tess;
		this.ratio = ratio;
		this.factor = factor;
	}
	
	@Override
	public Tessellator color(int color)
	{
		this.tess.color(color);
		return this;
	}

	@Override
	public Tessellator texture(float u, float v)
	{
		this.tess.texture(u, v);
		return this;
	}

	@Override
	public Tessellator vertex(float x, float y, float z)
	{
		int capacity = this.tess.getRaw().length;
		if(this.tess.getIndex()*6 >= capacity*this.ratio)
		{
			capacity *= factor;
			final int[] newBuffer = new int[capacity];
			System.arraycopy(this.tess.getRaw(), 0, newBuffer, 0, this.tess.getRaw().length);
			this.tess.setRaw(newBuffer);
			this.tess.setBuffer(BufferUtils.createDirectBuffer(capacity*4));
		}
		this.tess.vertex(x, y, z);
		return this;
	}

	@Override
	public Tessellator draw(int mode)
	{
		return this.tess.draw(mode);
	}

	@Override
	public Tessellator bind()
	{
		return this.tess.bind();
	}

	@Override
	public Tessellator reset()
	{
		return this.tess.reset();
	}

	@Override
	public Tessellator end(int mode)
	{
		return this.tess.end(mode);
	}

	@Override
	public int[] getRaw()
	{
		return this.tess.getRaw();
	}

	@Override
	public Tessellator setRaw(int[] raw)
	{
		return this.tess.setRaw(raw);
	}

	@Override
	public ByteBuffer getBuffer()
	{
		return this.tess.getBuffer();
	}
	
	@Override
	public IntBuffer getIBuffer()
	{
		return this.tess.getIBuffer();
	}

	@Override
	public Tessellator setBuffer(ByteBuffer buffer)
	{
		return this.tess.setBuffer(buffer);
	}

	@Override
	public int getIndex()
	{
		return this.tess.getIndex();
	}
	
	@Override
	public void setIndex(int index)
	{
		this.tess.setIndex(index);
	}
	
}