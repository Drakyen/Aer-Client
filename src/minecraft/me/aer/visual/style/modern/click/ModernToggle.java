package me.aer.visual.style.modern.click;

import me.aer.config.valuesystem.BooleanValue;
import me.aer.visual.gui.click.base.ModuleButton;
import me.aer.visual.gui.click.base.Toggle;
import me.aer.visual.render.Fonts;

import java.awt.*;

public class ModernToggle extends Toggle {

    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(1, 1, 1, newCol.getAlpha());
        foregroundCol = new Color(70, 70, 70, newCol.getAlpha());
    }

    public ModernToggle(int xIn, int yIn, int widthIn, int heightIn, BooleanValue value, ModuleButton parent) {
        super(xIn, yIn, widthIn, heightIn, value, parent);
    }


    @Override
    public void renderElement(int mouseX, int mouseY) {
        rend.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        if (isToggled()) {
            if (hovered(mouseX, mouseY) && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
                rend.drawRect(x + 2, y + 1, x + width - 2, y + height, col.darker().darker().getRGB());
            } else {
                rend.drawRect(x + 2, y + 1, x + width - 2, y + height, col.darker().getRGB());
            }
        } else {
            if (hovered(mouseX, mouseY) && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
                rend.drawRect(x + 2, y + 1, x + width - 2, y + height, foregroundCol.brighter().getRGB());
            }

        }
        rend.drawString(Fonts.normal, value.getName(), x + 5, y + 5, 0xffffffff, true);
    }

}
