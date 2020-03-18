package me.aerclient.visual.style.modern.click;

import me.aerclient.implementation.module.base.Module;
import me.aerclient.visual.gui.click.base.ModuleButton;
import me.aerclient.visual.gui.click.base.Panel;
import me.aerclient.visual.render.feather.animate.Animation;
import me.aerclient.visual.render.render2D.font.Fonts;

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
        rend2D.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        if (getModule().isActive()) {
            if (hovered(mouseX, mouseY) && getParent().getParent().allowAction(getParent(), mouseX, mouseY)) {
                rend2D.drawGradVert(7, x + 2, y, x + width - 2, y + height, col.darker().darker().darker().getRGB(), col.darker().darker().getRGB());
            } else {
                rend2D.drawGradVert(7, x + 2, y, x + width - 2, y + height, col.darker().getRGB(), col.darker().darker().getRGB());
            }
            rend2D.drawString(Fonts.normal, getName(), x + 5, y + 5, 0xffffffff, true);
        } else {
            if (hovered(mouseX,mouseY) && getParent().getParent().allowAction(getParent(), mouseX, mouseY)) {
                rend2D.drawGradVert(7, x + 2, y, x + width - 2, y + height, foregroundCol.darker().getRGB(), foregroundCol.getRGB());
            } else {
                rend2D.drawGradVert(7, x + 2, y, x + width - 2, y + height, foregroundCol.brighter().getRGB(), foregroundCol.getRGB());
            }
            rend2D.drawString(Fonts.normal, getName(), x + 5, y + 5, 0xffaaaaaa, true);
        }
        rend2D.drawString(Fonts.normal, (isExtended() ? "-" : "+"), x + width - 10, y + 5, 0xffffffff, true);
    }
}
