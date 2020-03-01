package me.aerclient.ui.base.basic;

/**
 * A basic Gui class with position, size, and basic methods that a UI would want.
 */
public abstract class BasicGuiElement implements UI {

    protected int x,y,width,height;

    /**
     * Creates a new Basic gui element with specified position and size.
     * @param xIn
     * @param yIn
     * @param widthIn
     * @param heightIn
     */
    public BasicGuiElement(int xIn, int yIn, int widthIn, int heightIn){
        x = xIn; y = yIn; width = widthIn; height = heightIn;
    }


    public abstract void init();
    public abstract void render(int mouseX, int mouseY);
    public abstract void mouseClicked(int mouseX, int mouseY, int button);
    public abstract void mouseReleased(int mouseX, int mouseY, int button);
    public abstract void keyPressed(int key);

    public int getX(){return x;}
    public int getY(){return y;}
    public UI setX(int xIn){x = xIn; return this;}
    public UI setY(int yIn){y = yIn; return this;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}

    public boolean hovered(int mouseX, int mouseY){
        return x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height;
    }



}
