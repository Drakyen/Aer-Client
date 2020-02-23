package net.aer.gui.clickgui.elements;

import net.aer.Aer;
import net.aer.gui.GuiStyle;
import net.aer.utils.valuesystem.NumberValue;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

import java.text.DecimalFormat;

public class ElementSlider extends Element {

    private boolean dragging;
    private double percent;
    private DecimalFormat format = new DecimalFormat("0.00");

    public ElementSlider(NumberValue v, ModuleButton parent, GuiStyle style) {
        super(v, parent, style);
    }

    public void drawScreen(int x, int y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;

        this.hovered = hovered(mouseX, mouseY);

        if (isDragging()) {
            if (setting.getDefault() instanceof Double) {
                double diff = ((NumberValue) setting).getMax().doubleValue() - ((NumberValue) setting).getMin().doubleValue();
                double val = ((NumberValue) setting).getMin().doubleValue() + (MathHelper.clamp((double) (mouseX - x) / (width - 2), 0, 1)) * diff;
                setting.setObject(Double.parseDouble(format.format(val)));
            } else if (setting.getDefault() instanceof Float) {
                float diff = ((NumberValue) setting).getMax().floatValue() - ((NumberValue) setting).getMin().floatValue();
                float val = (float) (((NumberValue) setting).getMin().floatValue() + (MathHelper.clamp((double) (mouseX - x) / (width - 2), 0, 1)) * diff);
                setting.setObject(Float.parseFloat(format.format(val)));
            } else if (setting.getDefault() instanceof Integer) {
                int diff = ((NumberValue) setting).getMax().intValue() - ((NumberValue) setting).getMin().intValue();
                int val = (int) (((NumberValue) setting).getMin().intValue() + (MathHelper.clamp((double) (mouseX - x) / (width - 2), 0, 1)) * diff);
                setting.setObject(val);
            } else if (setting.getDefault() instanceof Long) {
                long diff = ((NumberValue) setting).getMax().longValue() - ((NumberValue) setting).getMin().longValue();
                long val = (long) (((NumberValue) setting).getMin().longValue() + (MathHelper.clamp((double) (mouseX - x) / (width - 2), 0, 1)) * diff);
                setting.setObject(val);
            }

            getParent().ValueUpdated();

        }

        percent = (((NumberValue) setting).getValue().doubleValue() - ((NumberValue) setting).getMin().doubleValue()) / (((NumberValue) this.setting).getMax().doubleValue() - ((NumberValue) setting).getMin().doubleValue());

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

	public void onMouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0 && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            if (hovered(mouseX, mouseY)) {
                this.dragging = true;
                if (parent.getParent().getParent().soundMode) {
                    Aer.minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                }
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

    @Override
    public boolean hovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= y + this.height;
    }

    public boolean isDragging() {
        return dragging;
    }

    public double getValue() {
        return ((NumberValue) setting).getValue().doubleValue();
    }

    public double getPercent() {
        return percent;
    }


}
