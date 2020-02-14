package net.aer.gui.clickgui.elements;

import net.aer.gui.GuiStyle;
import net.aer.utils.valuesystem.ModeValue;
import net.aer.utils.valuesystem.Value;

public class ElementSelector extends Element {

    public boolean extended = false;

    public ElementSelector(Value v, ModuleButton parent, GuiStyle style) {
        super(v, parent, style);
    }

    public void drawScreen(int x, int y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;

        style.drawElement(this);

    }

    public void onMouseClicked(int mouseX, int mouseY, int button) {
        if (hovered(mouseX, mouseY)) {
            this.extended = !this.extended;
        }
        if (getHoveredOption(mouseX, mouseY) != "" && this.extended) {
            this.setting.setObject(getHoveredOption(mouseX, mouseY));
            this.parent.ValueUpdated();
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void keyTyped(char typedChar, int key) {

    }

    @Override
    public void updateCols() {

    }

    private String getHoveredOption(int mouseX, int mouseY) {
        int o = 0;
        for (String s : ((ModeValue) this.setting).getModes()) {
            o += 16;
            if (hovered(mouseX, mouseY - o)) {
                return s;
            }
        }
        return "";
	}


	private boolean hovered(int mouseX, int mouseY) {
		return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
	}


}
