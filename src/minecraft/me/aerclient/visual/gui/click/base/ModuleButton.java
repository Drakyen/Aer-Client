package me.aerclient.visual.gui.click.base;

import me.aerclient.config.valuesystem.*;
import me.aerclient.implementation.module.base.Module;
import me.aerclient.visual.render.feather.animate.Animation;
import me.aerclient.visual.render.feather.animate.TimeAnimation;
import me.aerclient.visual.gui.base.basic.UI;
import me.aerclient.visual.gui.base.container.ClickableGuiContainer;
import me.aerclient.implementation.utils.world.TimerUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

import java.awt.*;

public abstract class ModuleButton extends ClickableGuiContainer {

    private Panel parent;
    private Module module;
    private TimeAnimation animation;
    private boolean extended;
    private boolean currentState;
    private int offset;
    private TimerUtil.Timer timer = new TimerUtil.Timer();

    public ModuleButton(int xIn, int yIn, int widthIn, int heightIn, boolean extendedIn, Module moduleIn, Panel parentIn, Animation.Transition type, int animationDuration) {
        super(xIn, yIn, widthIn, heightIn);
        this.extended = extendedIn;
        setChildrenTakeInput(extended);
        module = moduleIn;
        parent = parentIn;
        animation = new TimeAnimation();
        animation.setTransition(type);
        animation.setDuration(animationDuration);
        animation.setProgress(extendedIn ? 0 : 1);
        addElements();
    }

    private void addElements() {
        for (Value v : ValueManager.getAllValuesFrom(module.getName())) {
            if(v instanceof ModeValue || v instanceof NumberValue || v instanceof BooleanValue) {
                add(parent.getParent().getStyle().getNewElement(0, 0, v, this));
            }
        }
        add(parent.getParent().getStyle().getNewKeybinding(0, 0, this));
    }


    @Override
    public void renderChildren(int mouseX, int mouseY) {
        int totalOffset = getTotalHeightOfChildren();
        offset = (int) (height - (totalOffset * animation.get()));
        for(UI ui : getChildren()){
            ui.setX(this.getX()).setY(this.getY() + offset);
            if(ui.getY() > this.getY() && ui.getY() > parent.getY()){
                ui.render(mouseX, mouseY);
            }
            offset += height;
        }
        offset -= height;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        if(timer.delay(400) && hovered(mouseX, mouseY)){
            parent.getParent().setHovered(this);
            timer.reset();
        } else if(!hovered(mouseX, mouseY)) {
            timer.reset();
        }
        if(this.getY() < parent.getY()){
            return;
        }
        renderModuleButton(mouseX, mouseY);
    }

    public abstract void renderModuleButton(int mouseX, int mouseY);

    @Override
    public void onClicked(int button, int mouseX, int mouseY) {
        if(button == 0 && parent.getParent().allowAction(parent, mouseX, mouseY)) {
            this.module.toggle();
            currentState = module.isActive();
            if (parent.getParent().getSoundMode()) {
                minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
        else if(button == 1 && parent.getParent().allowAction(parent, mouseX, mouseY)){
            setExtended(!extended);
            if (parent.getParent().getSoundMode()) {
                minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
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


    public Panel getParent(){
        return parent;
    }

    public boolean isExtended() {
        return extended;
    }

    public String getName(){
        return module.getName();
    }

    public void updateCols(Color newCol){}

    public Module getModule(){
        return module;
    }

    @Override
    public int getHeight() {
        return height + offset;
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
}
