package me.aerclient.visual.style.modern.click;

import me.aerclient.config.valuesystem.ModeValue;
import me.aerclient.visual.render.render2D.font.Fonts;
import me.aerclient.visual.gui.click.base.ModuleButton;
import me.aerclient.visual.gui.click.base.Selector;

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
    public void init() {

    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        rend2D.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        if (hovered(mouseX, mouseY) && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            rend2D.drawRect(x + 2, y + 1, x + width - 2, y + height, col.darker().darker().getRGB());
        } else {
            rend2D.drawRect(x + 2, y + 1, x + width - 2, y + height, col.darker().getRGB());
        }

        rend2D.drawString(Fonts.normal, value.getName() + " \u00A7p[\u00A7r" + value.getObject() + "\u00A7p]", x + 5, y + 5, 0xffffffff, false);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }

    @Override
    public void keyPressed(int key) {

    }
}
