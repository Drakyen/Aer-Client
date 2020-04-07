package me.aer.visual.gui.base.basic;

/**
 * A basic Clickable Gui that has a size, position, and click method.
 */
public abstract class ClickableUI extends BasicGuiElement {

    public ClickableUI(int xIn, int yIn, int widthIn, int heightIn) {
        super(xIn, yIn, widthIn, heightIn);
    }

    public void mouseClicked(int mouseX, int mouseY, int button){
        if(hovered(mouseX, mouseY)){
            onClicked(button, mouseX, mouseY);
        }
    }

    public abstract void onClicked(int button, int mouseX, int mouseY);

}
