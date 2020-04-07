package me.aer.visual.style.minimal.click;

import me.aer.config.valuesystem.ColourValue;
import me.aer.visual.gui.click.base.ColourPicker;
import me.aer.visual.gui.click.base.ModuleButton;
import me.aer.visual.render.Fonts;

import java.awt.*;

public class MinimalColourPicker extends ColourPicker {

    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    public MinimalColourPicker(int xIn, int yIn, int widthIn, int heightIn, int areaWidthIn, int areaHeightIn, ColourValue valueIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, areaWidthIn, areaHeightIn, valueIn, parentIn);
    }

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(15, 15, 15, newCol.getAlpha());
        foregroundCol = new Color(28, 28, 28, newCol.getAlpha());
    }

    @Override
    protected void drawSelectorIcon(int xpos, int ypos) {
        rend.drawRect(xpos - 2.5f, ypos - 0.5f, xpos + 3.5f, ypos + 1.5f, 0xffffffff);
        rend.drawRect(xpos - 0.5f, ypos - 2.5f, xpos + 1.5f, ypos + 3.5f, 0xffffffff);
        rend.drawRect(xpos - 3, ypos - 1, xpos + 3, ypos + 1, 0xff000000);
        rend.drawRect(xpos - 1, ypos - 3, xpos + 1, ypos + 3, 0xff000000);
    }

    @Override
    protected void drawSliderBar(int xpos, int ypos, int x2) {
        rend.drawRect(xpos, ypos - 1, x2, ypos + 1, 0xff000000);
    }

    @Override
    protected void drawHexBox(float xpos, float ypos, float width, float height) {
        rend.drawRect(xpos, ypos, xpos + width, ypos + height, foregroundCol.getRGB());
    }

    @Override
    public void renderElement(int mouseX, int mouseY) {
        rend.drawRect(x, y, x + width, y + height, foregroundCol.getRGB());
        rend.drawRect(x + 5, y, x + 7, y + height, col.getRGB());

        rend.drawRect(x + width - 14, y + 3, x + width - 6, y + 11, value.getObject().getRGB());

        rend.drawRect(x + 12, y + 13, x + width - 6, y + height - 2, foregroundCol.brighter().getRGB());

        rend.drawString(Fonts.normal, value.getName(), x + 15, y + 5, 0xffaaaaaa, true);

        drawColourPicker(x + 15, y + 16, mouseX, mouseY);
    }
}
