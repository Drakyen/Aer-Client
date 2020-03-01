package me.aerclient.ui.click.base;


import me.aerclient.gui.clickgui.elements.Element;
import me.aerclient.render.feather.animate.Animation;
import me.aerclient.render.feather.animate.TimeAnimation;
import me.aerclient.ui.base.basic.UI;
import me.aerclient.ui.base.container.ClickableDraggableGuiContainer;
import me.aerclient.ui.click.ClickGui;

public class Panel extends ClickableDraggableGuiContainer {

    private TimeAnimation animation;
    private boolean extended = false;
    private String name;
    private ClickGui parent;

    public Panel(int xIn, int yIn, int widthIn, int heightIn, String nameIn, Animation.Transition type, int animationDuration, int dragButtonIn, ClickGui parentIn) {
        super(xIn, yIn, widthIn, heightIn, dragButtonIn);
        name = nameIn;
        parent = parentIn;
        animation = new TimeAnimation();
        animation.setTransition(type);
        animation.setDuration(animationDuration);
        animation.setProgress(1);
    }

    public void setExtended(boolean extendedIn){
        if(extendedIn != extended){
            extended = extendedIn;
            if(extended){
                animation.reverse();
            } else{
                animation.forward();
            }
        }
    }

    public boolean isExtended(){
        return extended;
    }

    public String getName(){
        return name;
    }

    @Override
    public void onClicked(int button) {
        if(button == 1) {
            setExtended(!extended);
        }
    }


    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
    }

    @Override
    public void renderChildren(int mouseX, int mouseY) {
        int totalOffset = getTotalHeightOfChildren();
        int offset = (int) (this.getHeight() - (totalOffset * animation.get()));
        for(UI ui : getChildren()){
            ui.setX(this.getX()).setY(this.getY() + offset);
            if(ui.getY() > this.getY()){
                ui.render(mouseX, mouseY);
            }
            offset += ui.getHeight();
        }
    }

    public boolean anythingHovered(int mouseX, int mouseY) {
        if (hovered(mouseX, mouseY)) {
            return true;
        } else
            for (UI button : getChildren()) {
                if (button.hovered(mouseX, mouseY)) {
                    return true;
                } else
                    for (UI element : ((ModuleButton)button).getChildren()) {
                        if (element.hovered(mouseX, mouseY)) {
                            return true;
                        }
                    }
            }
        return false;
    }

    @Override
    public void keyPressed(int key) {

    }

    public ClickGui getParent() {
        return parent;
    }
}
