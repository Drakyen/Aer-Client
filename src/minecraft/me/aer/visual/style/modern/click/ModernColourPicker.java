package me.aer.visual.style.modern.click;

import me.aer.config.valuesystem.ColourValue;
import me.aer.visual.gui.click.base.ColourPicker;
import me.aer.visual.gui.click.base.ModuleButton;
import me.aer.visual.render.Fonts;

import java.awt.*;

public class ModernColourPicker extends ColourPicker {

    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    public ModernColourPicker(int xIn, int yIn, int widthIn, int heightIn, int areaWidthIn, int areaHeightIn, ColourValue valueIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, areaWidthIn, areaHeightIn, valueIn, parentIn);
        this.hexCode.setDefaultCol(0xffffffff);
        this.hexCode.setSelectedCol(0x99999999);
        this.hexCode.setCursorCol(0xffffffff);
    }

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(1, 1, 1, newCol.getAlpha());
        foregroundCol = new Color(70, 70, 70, newCol.getAlpha());
    }

    @Override
    public void renderElement(int mouseX, int mouseY) {
        rend.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        rend.drawRect(x + 2, y + 1, x + width - 2, y + height, col.darker().getRGB());
        rend.drawRect(x + 2, y + 12, x + 4, y + height, col.darker().getRGB());
        rend.drawRect(x + 2, y + 12, x + width - 2, y + height, col.darker().getRGB());

        rend.drawRect(x + width - 13, y + 3, x + width - 5, y + 11, value.getObject().getRGB());

        rend.drawString(Fonts.normal, value.getName(), x + 5, y + 4, 0xffffffff, false);
        drawColourPicker(x + 4, (y + 14), mouseX, mouseY);
    }

    @Override
    protected void drawSelectorIcon(int xpos, int ypos) {
        rend.drawRect(xpos - 2.5f, ypos - 0.5f, xpos + 3.5f, ypos + 1.5f, 0xff000000);
        rend.drawRect(xpos - 0.5f, ypos - 2.5f, xpos + 1.5f, ypos + 3.5f, 0xff000000);
        rend.drawRect(xpos - 3, ypos - 1, xpos + 3, ypos + 1, 0xffffffff);
        rend.drawRect(xpos - 1, ypos - 3, xpos + 1, ypos + 3, 0xffffffff);
    }

    @Override
    protected void drawSliderBar(int xpos, int ypos, int width) {
        rend.drawRect(xpos - 0.5f, ypos - 1, width, ypos + 0.5f, 0xff000000);
    }

    @Override
    protected void drawHexBox(float xpos, float ypos, float width, float height) {
        rend.drawRect(xpos, ypos, xpos + width, ypos + height, col.darker().darker().getRGB());
    }

}
