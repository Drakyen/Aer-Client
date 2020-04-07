package me.aer.visual.gui.click.base;


import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;
import me.aer.implementation.module.base.ModuleManager;
import me.aer.visual.gui.base.basic.UI;
import me.aer.visual.gui.base.container.ClickableDraggableGuiContainer;
import me.aer.visual.gui.click.ClickGuiUI;
import me.aer.visual.render.feather.animate.Animation;
import me.aer.visual.render.feather.animate.TimeAnimation;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public abstract class Panel extends ClickableDraggableGuiContainer {

    protected TimeAnimation animation;
    private boolean extended;
    private ClickGuiUI parent;
    private boolean forcePosition;

    private Category cat;

    public Panel(int xIn, int yIn, int widthIn, int heightIn, boolean extendedIn, Animation.Transition type, int animationDuration, int dragButtonIn, ClickGuiUI parentIn, Category catIn) {
        super(xIn, yIn, widthIn, heightIn, dragButtonIn);
        parent = parentIn;
        cat = catIn;
        extended = extendedIn;
        forcePosition = true;
        animation = new TimeAnimation();
        animation.setTransition(type);
        animation.setDuration(animationDuration);
        animation.setProgress(extended ? 0 : 1);
        createModuleButtons();
    }

    private void createModuleButtons() {
        int yo = 0;
        for (Module m : ModuleManager.visibleModuleList) {
            if (m.getCategory() == this.cat) {
                ModuleButton m2 = parent.getStyle().getNewModuleButton(x, getY() + yo, Boolean.parseBoolean(parent.getProps().getProperty(m.getName() + "extended")), m, this);
                add(m2);
                yo += m2.getHeight();
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
        int totalOffset = getTotalHeightOfChildren();
        int offset = (int) (this.getHeight() - (totalOffset * animation.get()));
        for (UI ui : getChildren()) {
            ui.setX(this.getX()).setY(this.getY() + offset);
            if (!(animation.get() == 1)) {
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                resetScissor();
                ui.render(mouseX, mouseY);
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
            }
            offset += ui.getHeight();
        }
    }

    public void resetScissor() {
        ScaledResolution res = new ScaledResolution(minecraft);
        int scl = res.getScaleFactor();
        GL11.glScissor(x * scl, (res.getScaledHeight() - (y + getTotalHeightOfChildren() + height + 1)) * scl, width * scl, (getTotalHeightOfChildren() + 1) * scl);
    }


    public boolean anythingHovered(int mouseX, int mouseY) {
        if (hovered(mouseX, mouseY)) {
            return true;
        } else if (doChildrenTakeInput()) {
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
    public void keyPressed(char key, int keycode) {
        if (animation.get() < 0.5 && extended) {
            setChildrenTakeInput(true);
        } else if (animation.get() > 0.5 && !extended) {
            setChildrenTakeInput(false);
        }
        super.keyPressed(key, keycode);
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
        if (animation.get() < 0.5 && extended) {
            setChildrenTakeInput(true);
        } else if (animation.get() > 0.5 && !extended) {
            setChildrenTakeInput(false);
        }
        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void scrolled(int amount) {
        final ScaledResolution res = new ScaledResolution(minecraft);
        if (y + amount > res.getScaledHeight() - height) {
            this.y = res.getScaledHeight() - height;
        } else if (y + amount < -100) {
            this.y = -100;
        } else {
            this.y += amount;
        }
    }

    public ClickGuiUI getParent() {
        return parent;
    }


    public Category getCat() {
        return cat;
    }

    public void updateCols(Color newCol){}
}
