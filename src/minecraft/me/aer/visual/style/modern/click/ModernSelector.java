package me.aer.visual.style.modern.click;

import me.aer.config.valuesystem.ModeValue;
import me.aer.visual.gui.click.base.ModuleButton;
import me.aer.visual.gui.click.base.Selector;
import me.aer.visual.render.Fonts;

import java.awt.*;

public class ModernSelector extends Selector {
    public ModernSelector(int xIn, int yIn, int widthIn, int heightIn, ModeValue valueIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, valueIn, parentIn);
    }

    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(1, 1, 1, newCol.getAlpha());
        foregroundCol = new Color(70, 70, 70, newCol.getAlpha());
    }

    @Override
    public void renderElement(int mouseX, int mouseY) {
        rend.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        if (hovered(mouseX, mouseY) && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            rend.drawRect(x + 2, y + 1, x + width - 2, y + height, col.darker().darker().getRGB());
        } else {
            rend.drawRect(x + 2, y + 1, x + width - 2, y + height, col.darker().getRGB());
        }

        rend.drawString(Fonts.normal, value.getName() + " \u00A7p[\u00A7r" + value.getObject() + "\u00A7p]", x + 5, y + 5, 0xffffffff, false);
    }

}
