package me.aer.visual.render;


/**
 * Tracer line class that has a start, end, colour, and width
 */
public class TracerLine {

    public float xStart;
    public float yStart;
    public float zStart;
    public float xEnd;
    public float yEnd;
    public float zEnd;
    public int col;
    public float width;

    public TracerLine(float x1, float y1, float z1, float x2, float y2, float z2, int col, float w) {
        this.xStart = x1;
        this.xEnd = x2;
        this.yStart = y1;
        this.yEnd = y2;
        this.zStart = z1;
        this.zEnd = z2;
        this.col = col;
        this.width = w;

    }
}
	
	
	
