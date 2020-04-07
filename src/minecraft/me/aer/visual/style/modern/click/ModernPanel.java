package me.aer.visual.style.modern.click;

import me.aer.implementation.module.base.Category;
import me.aer.visual.gui.click.ClickGuiUI;
import me.aer.visual.gui.click.base.Panel;
import me.aer.visual.render.Fonts;
import me.aer.visual.render.feather.animate.Animation;

import java.awt.*;

public class ModernPanel extends Panel {

    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(1, 1, 1, newCol.getAlpha());
        foregroundCol = new Color(70, 70, 70, newCol.getAlpha());
    }

    public ModernPanel(int xIn, int yIn, int widthIn, int heightIn, boolean extendedIn, Animation.Transition type, int animationDuration, ClickGuiUI parent, Category catIn) {
        super(xIn, yIn, widthIn, heightIn, extendedIn, type, animationDuration, 0, parent, catIn);
    }


    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        rend.setColor(col.getRGB(), col.getAlpha());
        rend.drawRect(x, y, x + width, y + height, col.getRGB());
        rend.drawString(Fonts.mid, getCat().name(), x + 3, y + 3, 0xffffffff, true);
        rend.drawString(Fonts.mid, (isExtended() ? "-" : "+"), x + width - 10, y + 2, 0xffffffff, true);
        rend.drawRect(x, getAbsoluteHeightOfChildren(), x + width, getAbsoluteHeightOfChildren() + 2, backgroundCol.getRGB());
    }
}
