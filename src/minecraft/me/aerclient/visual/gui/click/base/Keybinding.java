package me.aerclient.visual.gui.click.base;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import org.lwjgl.input.Keyboard;

public abstract class Keybinding extends Element {

    private boolean listening = false;

    public Keybinding(int xIn, int yIn, int widthIn, int heightIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, null, parentIn);
    }

    @Override
    public void onClicked(int button, int mouseX, int mouseY) {
        if (button == 0 && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            this.listening = !this.listening;
            if (parent.getParent().getParent().getSoundMode()) {
                minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
    }

    @Override
    public void keyPressed(int key) {
        if (listening) {
            if (key == Keyboard.KEY_ESCAPE) {
                this.parent.getModule().setKeybind(Keyboard.KEY_NONE);
            } else {
                this.parent.getModule().setKeybind(key);
            }
            this.listening = false;
        }
    }

    public boolean isListening() {
        return listening;
    }

    @Override
    public void init() {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }
}
