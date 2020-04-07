package me.aer.visual.style.minimal.click;

import me.aer.config.valuesystem.ModeValue;
import me.aer.visual.gui.click.base.ModuleButton;
import me.aer.visual.gui.click.base.Selector;
import me.aer.visual.render.Fonts;

import java.awt.*;

public class MinimalSelector extends Selector {
    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    public MinimalSelector(int xIn, int yIn, int widthIn, int heightIn, ModeValue valueIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, valueIn, parentIn);
    }

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(15, 15, 15, newCol.getAlpha());
        foregroundCol = new Color(28, 28, 28, newCol.getAlpha());
    }

    @Override
    public void renderElement(int mouseX, int mouseY) {
        rend.drawRect(x, y, x + width, y + height, foregroundCol.getRGB());
        rend.drawRect(x + 5, y, x + 7, y + height, col.getRGB());

        if (hovered(mouseX, mouseY)) {
            rend.drawString(Fonts.normal, value.getName() + " \u00A7p[\u00A7r" + value.getObject() + "\u00A7p]", x + 15, y + 5, 0xffcccccc, false);
        } else {
            rend.drawString(Fonts.normal, value.getName() + " \u00A7p[\u00A7r" + value.getObject() + "\u00A7p]", x + 15, y + 5, 0xffaaaaaa, false);
        }
    }

}
