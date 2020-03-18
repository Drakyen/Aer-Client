package me.aerclient.visual.style.minimal.click;

import me.aerclient.config.valuesystem.ModeValue;
import me.aerclient.visual.gui.click.base.ModuleButton;
import me.aerclient.visual.gui.click.base.Selector;
import me.aerclient.visual.render.render2D.font.Fonts;

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
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        rend2D.drawRect(x, y, x + width, y + height, foregroundCol.getRGB());
        rend2D.drawRect(x + 5, y, x + 7, y + height, col.getRGB());

        if (hovered(mouseX, mouseY)) {
            rend2D.drawString(Fonts.normal, value.getName() + " \u00A7p[\u00A7r" + value.getObject() + "\u00A7p]", x + 15, y + 5, 0xffcccccc, false);
        } else {
            rend2D.drawString(Fonts.normal, value.getName() + " \u00A7p[\u00A7r" + value.getObject() + "\u00A7p]", x + 15, y + 5, 0xffaaaaaa, false);
        }
    }

}
