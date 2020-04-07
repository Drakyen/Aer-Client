package me.aer.visual.gui.base.basic;

/**
 * A slightly more sophisticated Gui class that can be dragged around
 */
public abstract class DraggableUI extends BasicGuiElement {

    private int dragButton;

    private boolean dragging = false;
    private int xOffset;
    private int yOffset;

    /**
     * Creates a new DraggableUI with the specified position, size, and drag button.
     * Make sure to super the mouseClicked(), mouseReleased(), and render() functions.
     * @param xIn
     * @param yIn
     * @param widthIn
     * @param heightIn
     * @param dragButtonIn
     */
    public DraggableUI(int xIn, int yIn, int widthIn, int heightIn, int dragButtonIn){
        super(xIn, yIn, widthIn, heightIn);
        dragButton = dragButtonIn;
    }

    /**
     * Creates a new DraggableUI with the specified position, size, and a default drag button of 0.
     * Make sure to super the mouseClicked(), mouseReleased(), and render() functions.
     * @param xIn
     * @param yIn
     * @param widthIn
     * @param heightIn
     */
    public DraggableUI(int xIn, int yIn, int widthIn, int heightIn){
        super(xIn, yIn, widthIn, heightIn);
        dragButton = 0;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(hovered(mouseX, mouseY) && button == dragButton){
            xOffset = x - mouseX; yOffset = y - mouseY;
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        dragging = false;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if(dragging) {
            x = mouseX + xOffset;
            y = mouseY + yOffset;
        }
    }
}
