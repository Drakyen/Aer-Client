package me.aer.visual.gui.click.base;

import me.aer.config.valuesystem.*;
import me.aer.implementation.module.base.Module;
import me.aer.implementation.utils.world.TimerUtil;
import me.aer.visual.gui.base.basic.UI;
import me.aer.visual.gui.base.container.ClickableGuiContainer;
import me.aer.visual.render.feather.animate.Animation;
import me.aer.visual.render.feather.animate.TimeAnimation;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.SoundEvents;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public abstract class ModuleButton extends ClickableGuiContainer {

    private Panel parent;
    private Module module;
    protected TimeAnimation animation;
    private boolean extended;
    private int offset;
    private TimerUtil timer = new TimerUtil();

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

        calculateOffset();
    }

    private void addElements() {
        int yo = 0;
        for (Value v : ValueManager.getAllValuesFrom(module.getName())) {
            if (v instanceof ModeValue || v instanceof NumberValue || v instanceof BooleanValue || v instanceof ColourValue) {
                Element e = parent.getParent().getStyle().getNewElement(getX(), getY() + yo, v, this);
                add(e);
                yo += e.getHeight();
            }
        }
        add(parent.getParent().getStyle().getNewKeybinding(getX(), getY() + yo, this));
    }

    private void calculateOffset() {
        int totalOffset = getTotalHeightOfChildren();
        offset = (int) (height - (totalOffset * animation.get()));
        for (UI ui : getChildren()) {
            ui.setX(this.getX()).setY(this.getY() + offset);
            offset += ui.getHeight();
        }
        offset -= height;
    }


    @Override
    public void renderChildren(int mouseX, int mouseY) {
        ScaledResolution res = new ScaledResolution(minecraft);
        int scl = res.getScaleFactor();
        int totalOffset = getTotalHeightOfChildren();
        offset = (int) (height - (totalOffset * animation.get()));
        for (UI ui : getChildren()) {
            ui.setX(this.getX()).setY(this.getY() + offset);
            if (!(animation.get() == 1)) {

                boolean ownScissorBox = y > parent.getY();
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                if (ownScissorBox) {
                    GL11.glScissor(
                            x * scl,
                            ((res.getScaledHeight() - (y + height + getTotalHeightOfChildren() + 1)) * scl) - 1,
                            width * scl,
                            ((getTotalHeightOfChildren() + 1) * scl) + 1);
                } else {
                    GL11.glScissor(
                            x * scl,
                            (res.getScaledHeight() - (parent.getY() + getTotalHeightOfChildren() + height + 1)) * scl,
                            width * scl,
                            (getTotalHeightOfChildren() + 1) * scl);
                }
                ui.render(mouseX, mouseY);
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
            }
            offset += ui.getHeight();
        }
        offset -= height;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        if (parent.getParent().isShowTooltips() && timer.delay(parent.getParent().getTooltipDelay()) && hovered(mouseX, mouseY)) {
            parent.getParent().setHovered(this);
            timer.reset();
        } else if (!hovered(mouseX, mouseY)) {
            timer.reset();
        }
        if (y + height < parent.getY()) {
            return;
        }
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        parent.resetScissor();
        renderModuleButton(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public abstract void renderModuleButton(int mouseX, int mouseY);

    @Override
    public void onClicked(int button, int mouseX, int mouseY) {
        if(button == 0 && parent.getParent().allowAction(parent, mouseX, mouseY)) {
            this.module.toggle();
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

    public Module getModule() {
        return module;
    }

    @Override
    public int getHeight() {
        return height + offset;
    }

    public int getActualHeight() {
        return height;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (animation.get() < 0.5 && extended) {
            setChildrenTakeInput(true);
        } else if (animation.get() > 0.5 && !extended) {
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
    public void keyPressed(char key, int keycode) {
        if (animation.get() < 0.5 && extended) {
            setChildrenTakeInput(true);
        } else if (animation.get() > 0.5 && !extended) {
            setChildrenTakeInput(false);
        }
        super.keyPressed(key, keycode);
    }

    @Override
    public void scrolled(int amount) {

    }
}
