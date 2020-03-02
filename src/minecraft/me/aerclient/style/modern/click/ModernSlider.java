package me.aerclient.style.modern.click;

import me.aerclient.config.valuesystem.NumberValue;
import me.aerclient.render.render2D.RenderUtils2D;
import me.aerclient.render.render2D.font.Fonts;
import me.aerclient.gui.click.base.ModuleButton;
import me.aerclient.gui.click.base.Slider;

import java.awt.*;

public class ModernSlider extends Slider {
    public ModernSlider(int xIn, int yIn, int widthIn, int heightIn, NumberValue valueIn, ModuleButton parentIn) {
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
    public void init() {

    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        rend2D.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        double stretch = x + (width * percent) - 2 <= x + 2 ? x + 3 : x + (width * percent) - 2;
        if (hovered(mouseX, mouseY) && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            rend2D.drawRect(x + 2, y + 1, (int) stretch, y + height, col.darker().darker().getRGB());
        } else {
            rend2D.drawRect(x + 2, y + 1, (int) stretch, y + height, col.darker().getRGB());
        }
        rend2D.drawString(Fonts.normal, value.getName() + " \u00A7p[\u00A7r" + value.getObject() + "\u00A7p]", x + 5, y + 5, 0xffffffff, false);
    }

    @Override
    public void keyPressed(int key) {

    }
}
