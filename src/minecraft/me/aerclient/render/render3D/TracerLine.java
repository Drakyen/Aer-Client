package me.aerclient.render.render3D;


/**
 * Tracer line class that has a start, end, colour, and width
 */
public class TracerLine {

    public double xStart;
    public double yStart;
    public double zStart;
    public double xEnd;
    public double yEnd;
    public double zEnd;
    public float red;
    public float green;
    public float blue;
    public float alpha;
    public float width;

    public TracerLine(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a, float w) {
        this.xStart = x1;
        this.xEnd = x2;
        this.yStart = y1;
        this.yEnd = y2;
        this.zStart = z1;
        this.zEnd = z2;
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
        this.width = w;

    }
}
	
	
	
