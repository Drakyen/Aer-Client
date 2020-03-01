package me.aerclient.style.click;

import me.aerclient.config.valuesystem.BooleanValue;
import me.aerclient.render.render2D.RenderUtils2D;
import me.aerclient.render.render2D.font.Fonts;
import me.aerclient.ui.click.base.ModuleButton;
import me.aerclient.ui.click.base.Toggle;

public class ModernToggle extends Toggle {

    public ModernToggle(int xIn, int yIn, int widthIn, int heightIn, BooleanValue value, ModuleButton parent) {
        super(xIn, yIn, widthIn, heightIn, value, parent);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        rend2D.drawRect(x, y, x + width, y + height, 0xff3388aa);
        RenderUtils2D.drawString(Fonts.normal, "Option!", x + 5, y + 5, 0xffffffff, true);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
    }

}
