package me.aerclient.visual.style.minimal;

import me.aerclient.config.valuesystem.BooleanValue;
import me.aerclient.config.valuesystem.ModeValue;
import me.aerclient.config.valuesystem.NumberValue;
import me.aerclient.config.valuesystem.Value;
import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.module.base.Module;
import me.aerclient.visual.gui.click.ClickGuiUI;
import me.aerclient.visual.gui.click.base.Panel;
import me.aerclient.visual.gui.click.base.*;
import me.aerclient.visual.render.TextUtils;
import me.aerclient.visual.render.feather.animate.Animation;
import me.aerclient.visual.render.render2D.font.Fonts;
import me.aerclient.visual.style.Style;
import me.aerclient.visual.style.minimal.click.*;

import java.awt.*;
import java.util.ArrayList;

public class MinimalStyle extends Style {

    public MinimalStyle(String name) {
        super(name);
    }

    @Override
    public Panel getNewPanel(int x, int y, String title, boolean extended, ClickGuiUI parent, Category catIn) {
        return new MinimalPanel(x, y, 110, 15, extended, Animation.Transition.INVERSE_STEEP_CURVE, 600, parent, catIn);
    }

    @Override
    public ModuleButton getNewModuleButton(int x, int y, boolean extended, Module module, Panel parent) {
        return new MinimalModuleButton(x, y, 110, 15, extended, module, parent, Animation.Transition.INVERSE_STEEP_CURVE, 500);
    }

    @Override
    protected Slider getNewSlider(int x, int y, Value value, ModuleButton parent) {
        return new MinimalSlider(x, y, 110, 15, (NumberValue) value, parent);
    }

    @Override
    protected Selector getNewSelector(int x, int y, Value value, ModuleButton parent) {
        return new MinimalSelector(x, y, 110, 15, (ModeValue) value, parent);
    }

    @Override
    protected Toggle getNewToggle(int x, int y, Value value, ModuleButton parent) {
        return new MinimalToggle(x, y, 110, 15, (BooleanValue) value, parent);
    }

    @Override
    public Keybinding getNewKeybinding(int x, int y, ModuleButton parent) {
        return new MinimalKeybinding(x, y, 110, 15, parent);
    }

    @Override
    public void renderTooltip(String tooltip, int mouseX, int mouseY, Color col) {
        ArrayList<String> toDraw = TextUtils.splitLines(Fonts.normal, tooltip, ' ', 90);
        if (mouseX - 95 > 0) {
            rend2D.drawRect(mouseX - 95, mouseY, mouseX, mouseY + 15 * toDraw.size(), new Color(1, 1, 1, col.getAlpha()).getRGB());
            int index = 0;
            for (String s : toDraw) {
                rend2D.drawString(Fonts.normal, s, mouseX - 90, mouseY + (15 * index) + 3, 0xffffffff, false);
                index++;
            }
            rend2D.drawGradHoriz(7, mouseX - 95, mouseY, mouseX - 91, mouseY + (15 * toDraw.size()), col.brighter().getRGB(), col.darker().getRGB());
        } else {
            rend2D.drawRect(mouseX, mouseY, mouseX + 95, mouseY + 15 * toDraw.size(), new Color(1, 1, 1, col.getAlpha()).getRGB());
            int index = 0;
            for (String s : toDraw) {
                rend2D.drawString(Fonts.normal, s, (mouseX + 90) - (Fonts.normal.getStringWidth(s)), mouseY + (15 * index) + 3, 0xffffffff, false);
                index++;
            }
            rend2D.drawGradHoriz(7, mouseX + 91, mouseY, mouseX + 95, mouseY + (15 * toDraw.size()), col.darker().getRGB(), col.brighter().getRGB());
        }
    }
}
