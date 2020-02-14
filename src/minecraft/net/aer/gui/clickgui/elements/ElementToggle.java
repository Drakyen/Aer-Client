package net.aer.gui.clickgui.elements;

import net.aer.gui.GuiStyle;
import net.aer.utils.threads.ColourFadeThread;
import net.aer.utils.valuesystem.Value;

import java.awt.*;

public class ElementToggle extends Element {

	private boolean toggled;
	private Color current = style.getColour();

	public ElementToggle(Value v, ModuleButton parent, GuiStyle style) {
		super(v, parent, style);
	}

	public void drawScreen(int x, int y, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;
		if (toggled != (boolean) setting.getObject()) {
			toggled = (boolean) setting.getObject();
			fade();
		}

		this.current = this.colOut;

		style.drawElement(this);
	}

	private void fade() {
		if (this.isToggled()) {
			new Thread(new ColourFadeThread(this.current == null ? new Color(0x00000000) : this.current, style.getColour().darker(), 300, this)).start();
		} else {
			new Thread(new ColourFadeThread(this.current == null ? new Color(0x00000000) : this.current, style.getColour().darker().darker().darker(), 300, this)).start();
		}
	}

	public void onMouseClicked(int mouseX, int mouseY, int button) {
		if (button == 0) {
			if (hovered(mouseX, mouseY)) {
				this.toggled = !this.toggled;
				this.setting.setObject(this.toggled);
				this.parent.ValueUpdated();
				fade();
			}
		}
	}

	private boolean hovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= y + this.height;
	}

	public boolean isToggled() {
		return toggled;
	}

	@Override
	public void onMouseReleased(int mouseX, int mouseY, int state) {

	}

	@Override
	public void keyTyped(char typedChar, int key) {

	}

	@Override
	public void updateCols() {
		if (this.isToggled()) {
			this.colOut = style.getColour().darker();
		} else {
			this.colOut = style.getColour().darker().darker().darker();
		}
	}

	public Color getCurrent() {
		return current;
	}


}
