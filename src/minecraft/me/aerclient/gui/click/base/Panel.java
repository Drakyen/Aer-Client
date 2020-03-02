package me.aerclient.gui.click.base;


import me.aerclient.module.base.Category;
import me.aerclient.module.base.Module;
import me.aerclient.module.base.ModuleManager;
import me.aerclient.render.feather.animate.Animation;
import me.aerclient.render.feather.animate.TimeAnimation;
import me.aerclient.gui.base.basic.UI;
import me.aerclient.gui.base.container.ClickableDraggableGuiContainer;
import me.aerclient.gui.click.ClickGuiUI;

import java.awt.*;

public abstract class Panel extends ClickableDraggableGuiContainer {

    private TimeAnimation animation;
    private boolean extended = false;
    private ClickGuiUI parent;

    private Category cat;

    public Panel(int xIn, int yIn, int widthIn, int heightIn, boolean extendedIn, Animation.Transition type, int animationDuration, int dragButtonIn, ClickGuiUI parentIn, Category catIn) {
        super(xIn, yIn, widthIn, heightIn, dragButtonIn);
        parent = parentIn;
        cat = catIn;
        extended = extendedIn;
        animation = new TimeAnimation();
        animation.setTransition(type);
        animation.setDuration(animationDuration);
        animation.setProgress(extended ? 0 : 1);
        createModuleButtons();
    }

    private void createModuleButtons() {
        for (Module m : ModuleManager.visibleModuleList) {
            if (m.getCategory() == this.cat) {
                add(parent.getStyle().getNewModuleButton(0, 0, Boolean.parseBoolean(parent.getProps().getProperty(m.getName() + "extended")), m, this));
            }
        }
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

    @Override
    public void onClicked(int button) {
        if(button == 1) {
            setExtended(!extended);
        }
    }

    @Override
    public void renderChildren(int mouseX, int mouseY) {
        int totalOffset = getHeightOfChildren();
        int offset = (int) (this.getHeight() - (totalOffset * animation.get()));
        for(UI ui : getChildren()){
            ui.setX(this.getX()).setY(this.getY() + offset);
            ui.render(mouseX, mouseY);
            offset += ui.getHeight();
        }
    }

    public boolean anythingHovered(int mouseX, int mouseY) {
        if (hovered(mouseX, mouseY)) {
            return true;
        } else if(doChildrenTakeInput()) {
            for (UI button : getChildren()) {
                if (button.hovered(mouseX, mouseY)) {
                    return true;
                } else if (((ModuleButton) button).doChildrenTakeInput()) {
                    for (UI element : ((ModuleButton) button).getChildren()) {
                        if (element.hovered(mouseX, mouseY)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void keyPressed(int key) {
        if(animation.get() < 0.5 && extended){
            setChildrenTakeInput(true);
        }
        else if(animation.get() > 0.5 && !extended){
            setChildrenTakeInput(false);
        }
        super.keyPressed(key);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(animation.get() < 0.5 && extended){
            setChildrenTakeInput(true);
        }
        else if(animation.get() > 0.5 && !extended){
            setChildrenTakeInput(false);
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        if(animation.get() < 0.5 && extended){
            setChildrenTakeInput(true);
        }
        else if(animation.get() > 0.5 && !extended){
            setChildrenTakeInput(false);
        }
        super.mouseReleased(mouseX, mouseY, button);
    }

    public ClickGuiUI getParent() {
        return parent;
    }


    public Category getCat() {
        return cat;
    }

    public void updateCols(Color newCol){}
}
