package me.aerclient.render.feather.render;

import me.aerclient.render.feather.util.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Created by Travis Stone on 11/20/2014.
 */
public class Vbo {
    private final int id;
    protected final int dimensions;
    private int size = 0;

    public Vbo() {
        this(GL15.glGenBuffers());
    }

    public Vbo(int id) {
        this(3, id);
    }
    
    public Vbo(int dimensions, int id) {
    	this.id = id;
    	this.dimensions = dimensions;
    }

    public void compile(float... points) {
        if(points != null && points.length > 0) {
            final FloatBuffer buffer = BufferUtils.createDirectBuffer(points.length*4).asFloatBuffer();
            buffer.put(points).flip();
            this.compile(buffer);
        }
    }

    public void compile(FloatBuffer buffer) {
        this.size = buffer.capacity();
        BufferUtils.bindBuffer(this.id);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        BufferUtils.bindBuffer(0);
    }

    public void bind() {
        BufferUtils.bindBuffer(this.id);
        GL11.glVertexPointer(this.dimensions, GL11.GL_FLOAT, 0, 0L);
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
    }

    public void draw(int mode) {
        GL11.glDrawArrays(mode, 0, this.size);
    }

    public void draw(int mode, ByteBuffer order) {
        GL11.glDrawElements(mode, order);
    }
}
