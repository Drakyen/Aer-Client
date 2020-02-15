package net.aer.gui.clickgui.elements;

import net.aer.gui.GuiStyle;
import net.aer.utils.valuesystem.NumberValue;
import net.aer.utils.valuesystem.Value;
import net.minecraft.util.math.MathHelper;

import java.text.DecimalFormat;

public class ElementSlider extends Element {

    private boolean dragging;
    private double value;
    private double percent;
    private DecimalFormat format = new DecimalFormat("0.00");

    public ElementSlider(Value v, ModuleButton parent, GuiStyle style) {
        super(v, parent, style);
    }

    public void drawScreen(int x, int y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;

		if (isDragging()) {
            double diff = ((NumberValue) setting).getMax().doubleValue() - ((NumberValue) setting).getMin().doubleValue();
            double val = ((NumberValue) setting).getMin().doubleValue() + (MathHelper.clamp((double) (mouseX - x) / (width - 2), 0, 1)) * diff;
            setting.setObject(Double.parseDouble(format.format(val)));
            getParent().ValueUpdated();
            value = val;
        }

        percent = (((NumberValue) setting).getValue().doubleValue() - ((NumberValue) setting).getMin().doubleValue()) / (((NumberValue) this.setting).getMax().doubleValue() - ((NumberValue) setting).getMin().doubleValue());

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

	public void onMouseClicked(int mouseX, int mouseY, int button) {
		if (button == 0) {
			if (hovered(mouseX, mouseY)) {
				this.dragging = true;
			}
		}
	}

	public void onMouseReleased(int mouseX, int mouseY, int state) {
		this.dragging = false;
	}

	@Override
	public void keyTyped(char typedChar, int key) {

	}

	@Override
	public void updateCols() {

    }

    private boolean hovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= y + this.height;
    }

    public boolean isDragging() {
        return dragging;
    }

    public double getValue() {
        return value;
    }

    public double getPercent() {
        return percent;
    }


}
