package me.aerclient.visual.gui.click.base;

import me.aerclient.config.valuesystem.ModeValue;
import me.aerclient.visual.render.feather.animate.Animation;
import me.aerclient.visual.render.feather.animate.TimeAnimation;
import me.aerclient.visual.render.render2D.font.Fonts;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public abstract class DropdownSelector extends Element<ModeValue> {

    private boolean extended = false;
    private TimeAnimation animation;
    private int offset = 0;

    public DropdownSelector(int xIn, int yIn, int widthIn, int heightIn, ModeValue valueIn, ModuleButton parentIn, Animation.Transition type, int animationDuration) {
        super(xIn, yIn, widthIn, heightIn, valueIn, parentIn);
        animation = new TimeAnimation();
        animation.setTransition(type);
        animation.setDuration(animationDuration);
        animation.setProgress(1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (hovered(mouseX, mouseY) && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            setExtended(!extended);
            if (parent.getParent().getParent().getSoundMode()) {
                minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        } else if (getHoveredOption(mouseX, mouseY) != "" && this.extended) {
            this.value.setObject(getHoveredOption(mouseX, mouseY));
            if (parent.getParent().getParent().getSoundMode()) {
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

    private String getHoveredOption(int mouseX, int mouseY) {
        int o = 0;
        for (String s : ((ModeValue) this.value).getModes()) {
            o += this.height;
            if (hovered(mouseX, mouseY - o)) {
                return s;
            }
        }
        return "";
    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        renderPieces();
    }

    private void renderPieces() {
        if (extended) {
            offset = height;
            for (String s : ((ModeValue) value).getModes()) {
                renderPiece(x, y + offset, (int)Fonts.normal.getStringHeight(s) + 2, s, value.getValue().equalsIgnoreCase(s));
                offset += 16;
            }
        }
    }

    public abstract void renderPiece(int x, int y, int height, String piece, boolean selected);

    @Override
    public int getHeight() {
        return super.getHeight() + offset;
    }
}
