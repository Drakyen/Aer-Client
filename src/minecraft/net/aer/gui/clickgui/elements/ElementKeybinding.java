package net.aer.gui.clickgui.elements;

import net.aer.gui.GuiStyle;
import net.aer.module.Module;
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

        style.drawElement(this);

        if (this.hovered(mouseX, mouseY)) {
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
        if (button == 0) {
            if (hovered(mouseX, mouseY)) {
                this.listening = true;
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

    private boolean hovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= y + this.height;
    }


}
