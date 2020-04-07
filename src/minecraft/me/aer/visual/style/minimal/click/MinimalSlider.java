package me.aer.visual.style.minimal.click;

import me.aer.config.valuesystem.NumberValue;
import me.aer.visual.gui.click.base.ModuleButton;
import me.aer.visual.gui.click.base.Slider;
import me.aer.visual.render.Fonts;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class MinimalSlider extends Slider {
    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;
    public Color transparentCol;

    public MinimalSlider(int xIn, int yIn, int widthIn, int heightIn, NumberValue valueIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, valueIn, parentIn);
    }

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(15, 15, 15, newCol.getAlpha());
        foregroundCol = new Color(28, 28, 28, newCol.getAlpha());
        transparentCol = new Color(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha() / 2).darker();
    }


    @Override
    public void renderElement(int mouseX, int mouseY) {
        rend.drawRect(x, y, x + width, y + height, foregroundCol.getRGB());
        rend.drawRect(x + 5, y, x + 7, y + height, col.getRGB());

        double stretch = x + 12 + ((width - 18) * percent) <= x + 14 ? x + 14 : x + 12 + ((width - 18) * percent);
        rend.drawRect(x + 12, y + 1, x + width - 6, y + height, foregroundCol.brighter().getRGB());
        if (hovered(mouseX, mouseY) && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            rend.drawRect(x + 12, y + 1, (int) stretch, y + height, transparentCol.darker().getRGB());
        } else {
            rend.drawRect(x + 12, y + 1, (int) stretch, y + height, transparentCol.getRGB());
        }

        if (hovered(mouseX, mouseY)) {
            rend.drawString(Fonts.normal, value.getName() + " \u00A7p[\u00A7r" + value.getObject() + "\u00A7p]", x + 15, y + 5, 0xffcccccc, false);
        } else {
            rend.drawString(Fonts.normal, value.getName() + " \u00A7p[\u00A7r" + value.getObject() + "\u00A7p]", x + 15, y + 5, 0xffaaaaaa, false);
        }
    }

    @Override
    public void logic(int mouseX, int mouseY) {
        if (isDragging()) {
            if (value.getDefault() instanceof Double) {
                double diff = value.getMax().doubleValue() - value.getMin().doubleValue();
                double val = value.getMin().doubleValue() + (MathHelper.clamp((double) (mouseX - x - 12) / (width - 19), 0, 1)) * diff;
                value.setObject(Double.parseDouble(format.format(val)));
            } else if (value.getDefault() instanceof Float) {
                float diff = value.getMax().floatValue() - value.getMin().floatValue();
                float val = (float) (value.getMin().floatValue() + (MathHelper.clamp((double) (mouseX - x - 12) / (width - 19), 0, 1)) * diff);
                value.setObject(Float.parseFloat(format.format(val)));
            } else if (value.getDefault() instanceof Integer) {
                int diff = value.getMax().intValue() - value.getMin().intValue();
                int val = (int) (value.getMin().intValue() + (MathHelper.clamp((double) (mouseX - x - 12) / (width - 19), 0, 1)) * diff);
                value.setObject(val);
            } else if (value.getDefault() instanceof Long) {
                long diff = value.getMax().longValue() - value.getMin().longValue();
                long val = (long) (value.getMin().longValue() + (MathHelper.clamp((double) (mouseX - x - 12) / (width - 19), 0, 1)) * diff);
                value.setObject(val);
            }
        }
        percent = Math.min((value.getValue().doubleValue() - value.getMin().doubleValue()) / (this.value.getMax().doubleValue() - value.getMin().doubleValue()), 1D);
    }
}
