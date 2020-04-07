package me.aer.visual.gui.base.basic;

public abstract class ClickableDraggableUI extends BasicGuiElement {

    private int dragButton;

    private boolean dragging = false;
    private int xOffset;
    private int yOffset;

    public ClickableDraggableUI(int xIn, int yIn, int widthIn, int heightIn, int dragButtonIn) {
        super(xIn, yIn, widthIn, heightIn);
        this.dragButton = dragButtonIn;
    }

    public abstract void onClicked(int button);

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(hovered(mouseX, mouseY)) {
            onClicked(button);
        }
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
