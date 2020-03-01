package me.aerclient.render.feather.util;

import org.lwjgl.opengl.GL15;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BufferUtils
{
	public static boolean isBigEndian = true;
	
	public static synchronized ByteBuffer createDirectBuffer(int capacity)
	{
		final ByteBuffer buffer = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
		BufferUtils.isBigEndian = buffer.order() == ByteOrder.BIG_ENDIAN;
		return buffer;
	}

    public static void bindBuffer(int id)
    {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
    }
}