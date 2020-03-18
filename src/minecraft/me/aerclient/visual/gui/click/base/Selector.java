package me.aerclient.visual.gui.click.base;

import me.aerclient.config.valuesystem.ModeValue;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public abstract class Selector extends Element<ModeValue> {


    public Selector(int xIn, int yIn, int widthIn, int heightIn, ModeValue valueIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, valueIn, parentIn);
    }

    @Override
    public void onClicked(int button, int mouseX, int mouseY) {
        if (parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            if (button == 0) {
                this.value.setObject(nextMode(this.value.getModes(), this.value.getValue()));
                if (parent.getParent().getParent().getSoundMode()) {
                    minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                }
            } else if (button == 1) {
                this.value.setObject(previousMode(this.value.getModes(), this.value.getValue()));
                if (parent.getParent().getParent().getSoundMode()) {
                    minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                }
            }
        }
    }

    private String nextMode(String[] modes, String mode) {
        int index = 1;
        for (String string : modes) {
            if (string.equals(mode)) {
                break;
            } else {
                index++;
            }
        }
        if (index == modes.length) {
            return modes[0];
        } else {
            return modes[index];
        }
    }

    private String previousMode(String[] modes, String mode) {
        int index = 1;
        for (String string : modes) {
            if (string.equals(mode)) {
                break;
            } else {
                index++;
            }
        }
        if (index == 1) {
            return modes[modes.length - 1];
        } else {
            return modes[index - 2];
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }

    @Override
    public void keyPressed(int key) {

    }

}
