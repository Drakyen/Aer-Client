package me.aerclient.render.feather.util.texture;


import me.aerclient.render.feather.render.Tessellator;
import me.aerclient.render.feather.util.Utils;

public abstract interface Texture extends Utils {
	public abstract int getID();
	public abstract float getU();
	public abstract float getV();
	public abstract float getU1();
	public abstract float getV1();
	public abstract float getWidth();
	public abstract float getHeight();
	public abstract void setID(int id);
	public abstract void setU(float u);
	public abstract void setV(float v);
	public abstract void setU1(float u1);
	public abstract void setV1(float v1);
	public abstract void setWidth(float width);
	public abstract void setHeight(float height);
	public abstract void load();
	public abstract void bind();
	public abstract void draw(Tessellator tess, int mode, float x, float y);
}