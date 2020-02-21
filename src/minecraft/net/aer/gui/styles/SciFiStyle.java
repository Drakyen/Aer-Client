package net.aer.gui.styles;

import net.aer.gui.GuiStyle;
import net.aer.gui.clickgui.elements.Panel;
import net.aer.gui.clickgui.elements.*;
import net.aer.render.render2D.RenderUtils2D;
import net.aer.render.render2D.font.Fonts;
import net.aer.utils.valuesystem.ModeValue;
import net.aer.utils.valuesystem.NumberValue;
import net.aer.utils.valuesystem.Value;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class SciFiStyle extends GuiStyle {

    public Color backgroundCol;
    public Color textCol;
    public Color col;

    public SciFiStyle() {
        super(100, 20, 100, 20, 100, 20, 20, true);
    }

    @Override
    public void setCol(Color colIn) {
        colour = colIn;
        col = new Color(colIn.getRGB());
        backgroundCol = colIn.darker().darker();
        textCol = col.brighter().brighter();
    }

    @Override
    public void drawToolTip(String tooltip, int mouseX, int mouseY) {

    }

    @Override
    public void drawDescription(String description, int mouseX, int mouseY) {

    }

    @Override
    public Panel drawPanel(Panel panel) {
        int x = panel.x;
        int y = panel.y;
        int width = this.getPanelWidth();
        int height = this.getPanelHeight();
        RenderUtils2D.drawGradientRectVert(x, y, x + width, y + height, 0, backgroundCol.getRGB(), 0xff121212);
        RenderUtils2D.drawGradientRectVert(x - 1, y, x, y + height, 0, 0xffffffff, 0xff000000);
        RenderUtils2D.drawGradientRectVert(x + width, y, x + width + 1, y + height, 0, 0xffffffff, 0xff000000);
        RenderUtils2D.drawCenteredString(Fonts.mid, panel.getName(), x + width / 2, y + height / 2, textCol.brighter().brighter().getRGB(), true);

        return panel;
    }

    @Override
    public ModuleButton drawModule(ModuleButton module) {
        int x = module.getX();
        int y = module.getY();
        int width = this.getModuleWidth();
        int height = this.getModuleHeight();

        RenderUtils2D.drawRect(x, y, x + 1, y + height, 0xff000000);
        RenderUtils2D.drawRect(x + width - 1, y, x + width, y + height, 0xff000000);
        RenderUtils2D.drawGradientRectVert(x + 1, y, x + width - 1, y + height, 0, backgroundCol.getRGB(), backgroundCol.darker().getRGB());
        RenderUtils2D.drawGradientRectVert(x + 1, y, x + width - 1, y + height, 0, module.getCurrent().darker().getRGB(), module.getCurrent().getRGB());
        RenderUtils2D.drawCenteredString(Fonts.normal, module.getName(), x + width / 2, y + height / 2, textCol.brighter().getRGB(), true);

        return module;
    }

    @Override
    protected Element drawElementKeybinding(ElementKeybinding option) {
        int x = option.getX();
        int y = option.getY();
        int width = this.getOptionWidth();
        int height = this.getOptionHeight();
        RenderUtils2D.drawGradientRectVert(x + 1, y, x + 2, y + height, 0, 0xffffffff, 0xff000000);
        RenderUtils2D.drawGradientRectVert(x + width - 2, y, x + width - 1, y + height, 0, 0xffffffff, 0xff000000);
        RenderUtils2D.drawRect(x + 1, y + height - 1, x + width - 1, y + height, 0xff000000);
        RenderUtils2D.drawRect(x + 1, y, x + width - 1, y + height, backgroundCol.darker().getRGB());

        if (option.isListening()) {
            RenderUtils2D.drawCenteredString(Fonts.normal, "Listening..", x + width / 2, y + height / 2, textCol.getRGB(), false);
        } else {
            RenderUtils2D.drawCenteredString(Fonts.normal, "Keybind [" + (option.getModule().getKeybind() != Keyboard.KEY_ESCAPE ? Keyboard.getKeyName(((ElementKeybinding) option).getModule().getKeybind()) : "None") + "]", x + width / 2, y + height / 2, textCol.getRGB(), false);
        }
        return option;
    }

    @Override
    protected Element drawElementSelector(ElementSelector option) {
        int x = option.getX();
        int y = option.getY();
        int width = this.getOptionWidth();
        int height = this.getOptionHeight();
        int offset = 0;
        Value setting = option.getSetting();

        if (option.extended) {
            offset = height;
            for (String s : ((ModeValue) option.getSetting()).getModes()) {
                RenderUtils2D.drawRect(x + 3, y + offset, x + width - 3, y + 16 + offset, backgroundCol.getRGB());
                if (s.equalsIgnoreCase((String) setting.getObject())) {
                    RenderUtils2D.drawRect(x + 3, y + offset, x + width - 3, y + 16 + offset, backgroundCol.brighter().getRGB());
                }
                RenderUtils2D.drawCenteredString(Fonts.normal, s, x + width / 2, y + (height / 2) + offset, textCol.getRGB(), false);
                RenderUtils2D.drawRect(x + 3, y + offset, x + 4, y + 16 + offset, 0x88ffffff);
                RenderUtils2D.drawRect(x + width - 3, y + offset, x + width - 4, y + 16 + offset, 0x88ffffff);
                offset += 16;
            }
            RenderUtils2D.drawRect(x + 3, y + offset, x + width - 3, y + offset + 1, 0xff000000);
            offset += 1;
            offset -= height;
        }
        RenderUtils2D.drawRect(x + 1, y, x + 2, y + height, 0xffffffff);
        RenderUtils2D.drawRect(x + width - 2, y, x + width - 1, y + height, 0xffffffff);
        RenderUtils2D.drawRect(x + 2, y + height - 1, x + width - 2, y + height, 0xff000000);
        RenderUtils2D.drawRect(x + 2, y, x + width - 2, y + 1, 0xff000000);
        RenderUtils2D.drawRect(x + 1, y, x + width - 1, y + height, backgroundCol.getRGB());
        RenderUtils2D.drawCenteredString(Fonts.normal, setting.getName() + " [" + (String) setting.getObject() + "]", x + width / 2, y + height / 2, textCol.getRGB(), false);
        option.setOffset(offset);
        return option;
    }

    @Override
    protected Element drawElementSlider(ElementSlider option) {
        int x = option.getX();
        int y = option.getY();
        int width = this.getOptionWidth();
        int height = this.getOptionHeight();
        Value setting = option.getSetting();
        double percent = option.getPercent();

        RenderUtils2D.drawRect(x + 1, y, x + 2, y + height, 0xffffffff);
        RenderUtils2D.drawRect(x + width - 2, y, x + width - 1, y + height, 0xffffffff);
        RenderUtils2D.drawRect(x + 2, y + height - 1, x + width - 2, y + height, 0xff000000);
        RenderUtils2D.drawRect(x + 2, y, x + width - 2, y + 1, 0xff000000);
        RenderUtils2D.drawRect(x + 1, y, x + width - 1, y + height, backgroundCol.darker().getRGB());
        double stretch = x + (width * percent) - 2 <= x + 2 ? x + 3 : x + (width * percent) - 2;
        RenderUtils2D.drawGradientRectHoriz(x + 2, y + 1, (int) stretch, y + height - 1, 0f, backgroundCol.brighter().brighter().getRGB(), backgroundCol.getRGB());
        RenderUtils2D.drawRect((int) stretch - 1, y + 1, (int) stretch, y + height - 1, backgroundCol.brighter().getRGB());
        RenderUtils2D.drawCenteredString(Fonts.normal, setting.getName() + " [" + ((NumberValue) setting).getObject() + "]", x + width / 2, y + height / 2, textCol.getRGB(), false);

        return option;
    }

    @Override
    protected Element drawElementToggle(ElementToggle option) {
        int x = option.getX();
        int y = option.getY();
        int width = this.getOptionWidth();
        int height = this.getOptionHeight();
        RenderUtils2D.drawRect(x + 1, y, x + 2, y + height, 0xffffffff);
        RenderUtils2D.drawRect(x + width - 2, y, x + width - 1, y + height, 0xffffffff);
        RenderUtils2D.drawRect(x + 1, y, x + 2, y + height, backgroundCol.getRGB());
        RenderUtils2D.drawRect(x + width - 2, y, x + width - 1, y + height, backgroundCol.getRGB());
        RenderUtils2D.drawRect(x + 2, y + height - 1, x + width - 2, y + height, 0xff000000);
        RenderUtils2D.drawRect(x + 2, y, x + width - 2, y + 1, 0xff000000);
        RenderUtils2D.drawRect(x + 2, y, x + width - 2, y + height, option.getCurrent().getRGB());
        RenderUtils2D.drawCenteredString(Fonts.normal, option.getName(), x + width / 2, y + height / 2, textCol.getRGB(), false);

        return option;
    }

    @Override
    public void drawFinal(int x, int y) {

    }

}
