package net.aer.gui.clickgui.elements;

import net.aer.Aer;
import net.aer.gui.GuiStyle;
import net.aer.module.Module;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import org.lwjgl.input.Keyboard;

public class ElementKeybinding extends Element {

    private Module module;

    private boolean listening = false;


    public ElementKeybinding(Module m, ModuleButton parent, GuiStyle styleIn) {
        super(null, parent, styleIn);
        this.module = m;
    }

    public void drawScreen(int xIn, int yIn, int mouseX, int mouseY) {
        this.x = xIn;
        this.y = yIn;

        this.hovered = hovered(mouseX, mouseY);

        style.drawElement(this);

        if (isHovered()) {
            hoverTimer++;
            if (hoverTimer >= style.getHoverTime()) {
                style.drawToolTip("null", mouseX, mouseY);
            }
        } else {
            hoverTimer = 0;
        }

    }

    public boolean isListening() {
        return listening;
    }

    public Module getModule() {
        return module;
    }

    public void onMouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0 && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            if (hovered(mouseX, mouseY)) {
                this.listening = !this.listening;
                if (parent.getParent().getParent().soundMode) {
                    Aer.minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                }
            }
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int state) {

    }

    public void keyTyped(char typedChar, int key) {
        if (listening) {
            if (key == Keyboard.KEY_ESCAPE) {
                this.module.setKeybind(Keyboard.KEY_NONE);
            } else {
                this.module.setKeybind(key);
            }
            this.listening = false;
        }
    }

    @Override
    public void updateCols() {

    }

    @Override
    public boolean hovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= y + this.height;
    }


}
