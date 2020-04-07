package me.aer.visual.gui.click.base;

import me.aer.config.valuesystem.NumberValue;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

import java.text.DecimalFormat;

public abstract class Slider extends Element<NumberValue> {

    private boolean dragging;
    protected double percent;
    protected DecimalFormat format = new DecimalFormat("0.00");

    public Slider(int xIn, int yIn, int widthIn, int heightIn, NumberValue valueIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, valueIn, parentIn);
    }

    @Override
    public void onClicked(int button, int mouseX, int mouseY) {
        if (button == 0 && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            this.dragging = true;
            if (parent.getParent().getParent().getSoundMode()) {
                minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        logic(mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        this.dragging = false;
    }

    public boolean isDragging() {
        return dragging;
    }

    public double getValue() {
        return value.getValue().doubleValue();
    }

    public double getPercent() {
        return percent;
    }

    public void logic(int mouseX, int mouseY) {
        if (isDragging()) {
            if (value.getDefault() instanceof Double) {
                double diff = value.getMax().doubleValue() - value.getMin().doubleValue();
                double val = value.getMin().doubleValue() + (MathHelper.clamp((double) (mouseX - x) / (width - 2), 0, 1)) * diff;
                value.setObject(Double.parseDouble(format.format(val)));
            } else if (value.getDefault() instanceof Float) {
                float diff = value.getMax().floatValue() - value.getMin().floatValue();
                float val = (float) (value.getMin().floatValue() + (MathHelper.clamp((double) (mouseX - x) / (width - 2), 0, 1)) * diff);
                value.setObject(Float.parseFloat(format.format(val)));
            } else if (value.getDefault() instanceof Integer) {
                int diff = value.getMax().intValue() - value.getMin().intValue();
                int val = (int) (value.getMin().intValue() + (MathHelper.clamp((double) (mouseX - x) / (width - 2), 0, 1)) * diff);
                value.setObject(val);
            } else if (value.getDefault() instanceof Long) {
                long diff = value.getMax().longValue() - value.getMin().longValue();
                long val = (long) (value.getMin().longValue() + (MathHelper.clamp((double) (mouseX - x) / (width - 2), 0, 1)) * diff);
                value.setObject(val);
            }
        }
        percent = Math.min((value.getValue().doubleValue() - value.getMin().doubleValue()) / (this.value.getMax().doubleValue() - value.getMin().doubleValue()), 1D);
    }

    @Override
    public void init() {

    }

    @Override
    public void keyPressed(char key, int keycode) {

    }
}
