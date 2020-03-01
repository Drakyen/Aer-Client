package me.aerclient.style.click;

import me.aerclient.render.feather.animate.Animation;
import me.aerclient.render.render2D.font.Fonts;
import me.aerclient.ui.click.base.Panel;

public class ModernPanel extends Panel {

    public ModernPanel(int xIn, int yIn, int widthIn, int heightIn, String name, Animation.Transition type, int animationDuration) {
        super(xIn, yIn, widthIn, heightIn, name, type, animationDuration, 0);
    }


    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        rend2D.drawRect(x, y, x + width, y + height, 0xff33ffaa);
        rend2D.drawString(Fonts.mid, getName(), x + 3, y + 3, 0xffffffff, true);
        rend2D.drawString(Fonts.mid, (isExtended() ? "-" : "+"), x + width - 10, y + 2, 0xffffffff, true);
    }
}
