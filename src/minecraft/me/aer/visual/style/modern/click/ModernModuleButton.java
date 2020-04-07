package me.aer.visual.style.modern.click;

import me.aer.implementation.module.base.Module;
import me.aer.visual.gui.click.base.ModuleButton;
import me.aer.visual.gui.click.base.Panel;
import me.aer.visual.render.Fonts;
import me.aer.visual.render.feather.animate.Animation;

import java.awt.*;

public class ModernModuleButton extends ModuleButton {

    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(1, 1, 1, newCol.getAlpha());
        foregroundCol = new Color(70, 70, 70, newCol.getAlpha());
    }

    public ModernModuleButton(int xIn, int yIn, int widthIn, int heightIn, boolean extendedIn, Module moduleIn, Panel parentIn, Animation.Transition type, int animationDuration) {
        super(xIn, yIn, widthIn, heightIn, extendedIn, moduleIn, parentIn, type, animationDuration);
    }

    @Override
    public void renderModuleButton(int mouseX, int mouseY) {
        rend.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        if (getModule().isActive()) {
            if (hovered(mouseX, mouseY) && getParent().getParent().allowAction(getParent(), mouseX, mouseY)) {
                rend.drawGradVert(7, x + 2, y, x + width - 2, y + height, col.darker().darker().darker().getRGB(), col.darker().darker().getRGB());
            } else {
                rend.drawGradVert(7, x + 2, y, x + width - 2, y + height, col.darker().getRGB(), col.darker().darker().getRGB());
            }
            rend.drawString(Fonts.normal, getName(), x + 5, y + 5, 0xffffffff, true);
        } else {
            if (hovered(mouseX,mouseY) && getParent().getParent().allowAction(getParent(), mouseX, mouseY)) {
                rend.drawGradVert(7, x + 2, y, x + width - 2, y + height, foregroundCol.darker().getRGB(), foregroundCol.getRGB());
            } else {
                rend.drawGradVert(7, x + 2, y, x + width - 2, y + height, foregroundCol.brighter().getRGB(), foregroundCol.getRGB());
            }
            rend.drawString(Fonts.normal, getName(), x + 5, y + 5, 0xffaaaaaa, true);
        }
        rend.drawString(Fonts.normal, (isExtended() ? "-" : "+"), x + width - 10, y + 5, 0xffffffff, true);
    }
}
