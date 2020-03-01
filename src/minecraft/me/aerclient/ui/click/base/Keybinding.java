package me.aerclient.ui.click.base;

import me.aerclient.config.valuesystem.Value;
import me.aerclient.module.base.Module;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class Keybinding extends Element {

    private boolean listening = false;

    public Keybinding(int xIn, int yIn, int widthIn, int heightIn, Value valueIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, valueIn, parentIn);
    }

    @Override
    public void onClicked(int button, int mouseX, int mouseY) {
        if (button == 0 && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            if (hovered(mouseX, mouseY)) {
                this.listening = !this.listening;
                if (parent.getParent().getParent().getSoundMode()) {
                    minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                }
            }
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void render(int mouseX, int mouseY) {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }

    @Override
    public void keyPressed(int key) {

    }

    public boolean isListening(){
        return false;
    }

}
