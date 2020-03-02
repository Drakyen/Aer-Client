package me.aerclient.style.modern.click;

import me.aerclient.module.base.Category;
import me.aerclient.render.feather.animate.Animation;
import me.aerclient.render.render2D.font.Fonts;
import me.aerclient.gui.click.ClickGuiUI;
import me.aerclient.gui.click.base.Panel;

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
        rend2D.drawRect(x, y, x + width, y + height, col.getRGB());
        rend2D.drawString(Fonts.mid, getCat().name(), x + 3, y + 3, 0xffffffff, true);
        rend2D.drawString(Fonts.mid, (isExtended() ? "-" : "+"), x + width - 10, y + 2, 0xffffffff, true);
        rend2D.drawRect(x, getAbsoluteHeightOfChildren(), x + width, getAbsoluteHeightOfChildren() + 2, backgroundCol.getRGB());
    }
}
