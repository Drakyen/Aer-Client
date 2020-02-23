package net.aer.gui.clickgui.elements;

import net.aer.Aer;
import net.aer.gui.GuiStyle;
import net.aer.utils.threads.ColourFadeThread;
import net.aer.utils.valuesystem.Value;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

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

        this.hovered = hovered(mouseX, mouseY);

        if (toggled != (boolean) setting.getObject()) {
            toggled = (boolean) setting.getObject();
            fade();
        }

        this.current = this.colOut;

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

	private void fade() {
		if (this.isToggled()) {
			new Thread(new ColourFadeThread(this.current == null ? new Color(0x00000000) : this.current, style.getColour().darker(), 300, this)).start();
		} else {
			new Thread(new ColourFadeThread(this.current == null ? new Color(0x00000000) : this.current, style.getColour().darker().darker().darker(), 300, this)).start();
		}
	}

	public void onMouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0 && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            if (hovered(mouseX, mouseY)) {
                this.toggled = !this.toggled;
                this.setting.setObject(this.toggled);
                this.parent.ValueUpdated();
                fade();
                if (parent.getParent().getParent().soundMode) {
                    Aer.minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                }
            }
        }
    }

    @Override
    public boolean hovered(int mouseX, int mouseY) {
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
