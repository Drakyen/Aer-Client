package me.aer.visual.style.minimal.click;

import me.aer.config.valuesystem.BooleanValue;
import me.aer.visual.gui.click.base.ModuleButton;
import me.aer.visual.gui.click.base.Toggle;
import me.aer.visual.render.Fonts;

import java.awt.*;

public class MinimalToggle extends Toggle {

    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    public MinimalToggle(int xIn, int yIn, int widthIn, int heightIn, BooleanValue value, ModuleButton parent) {
        super(xIn, yIn, widthIn, heightIn, value, parent);
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
        if (value.getObject()) {
            rend.drawRect(x + width - 8, y + 1, x + width - 6, y + height, 0xff0f6e00);
        } else {
            rend.drawRect(x + width - 8, y + 1, x + width - 6, y + height, 0xff5c2020);
        }
        if (hovered(mouseX, mouseY)) {
            rend.drawString(Fonts.normal, value.getName(), x + 15, y + 5, 0xffffffff, true);
        } else {
            rend.drawString(Fonts.normal, value.getName(), x + 15, y + 5, 0xffaaaaaa, true);
        }
    }

}
