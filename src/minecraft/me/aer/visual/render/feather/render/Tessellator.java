package me.aer.visual.render.feather.render;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public interface Tessellator {
    Tessellator color(int color);

    Tessellator texture(float u, float v);

    Tessellator vertex(float x, float y, float z);

    Tessellator draw(int mode);

    Tessellator bind();

    Tessellator reset();

    Tessellator end(int mode);

    int[] getRaw();

    Tessellator setRaw(int[] raw);

    ByteBuffer getBuffer();

    Tessellator setBuffer(ByteBuffer buffer);

    IntBuffer getIBuffer();

    int getIndex();

    void setIndex(int index);
}