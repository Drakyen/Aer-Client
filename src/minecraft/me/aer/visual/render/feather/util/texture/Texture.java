package me.aer.visual.render.feather.util.texture;


import me.aer.visual.render.feather.render.Tessellator;
import me.aer.visual.render.feather.util.Utils;

public interface Texture extends Utils {
    int getID();

    void setID(int id);

    float getU();

    void setU(float u);

    float getV();

    void setV(float v);

    float getU1();

    void setU1(float u1);

    float getV1();

    void setV1(float v1);

    float getWidth();

    void setWidth(float width);

    float getHeight();

    void setHeight(float height);

    void load();

    void bind();

    void draw(Tessellator tess, int mode, float x, float y);
}