package me.aerclient.gui.styles;

import me.aerclient.gui.GuiStyle;
import me.aerclient.gui.clickgui.elements.Panel;
import me.aerclient.gui.clickgui.elements.*;
import me.aerclient.render.render2D.RenderUtils2D;
import me.aerclient.render.render2D.font.Fonts;
import me.aerclient.render.TextUtils;
import me.aerclient.config.valuesystem.Value;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

public class ModernStyle extends GuiStyle {

    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    public ModernStyle() {
        super(90, 15, 90, 15, 90, 15, 700, false);

    }

    @Override
    public void setCol(Color colIn) {
        colour = colIn;
        col = new Color(colIn.getRGB());
        backgroundCol = new Color(1, 1, 1, colIn.getAlpha());
        foregroundCol = new Color(70, 70, 70, colIn.getAlpha());
    }

    @Override
    public void drawToolTip(String tooltip, int mouseX, int mouseY) {

    }

    @Override
    public void drawDescription(String descriptionIn, int mouseX, int mouseY) {

        ArrayList<String> toDraw = TextUtils.splitLines(Fonts.normal, " " + descriptionIn, ' ', getModuleWidth());

        int width = getModuleWidth();
        int height = 0;
        int boxHeight = getModuleHeight()*toDraw.size();
        RenderUtils2D.drawRect(mouseX, mouseY, mouseX - width, mouseY + boxHeight, backgroundCol.getRGB(), 0f);
        RenderUtils2D.drawGradientRectHoriz(mouseX - width - 2, mouseY, mouseX - width + 1, mouseY + boxHeight,0f, col.darker().getRGB(), col.getRGB());
        for(String s : toDraw){
            RenderUtils2D.drawString(Fonts.normal, s, mouseX - width, mouseY + 4 + height, 0xffffffff, false);
            height += getModuleHeight();
        }

    }

    @Override
    public Panel drawPanel(Panel panel) {
        int x = panel.x;
        int y = panel.y;
        int width = this.getPanelWidth();
        int height = this.getPanelHeight();
        RenderUtils2D.drawRect(x, y, x + width, y + height, col.getRGB());
        RenderUtils2D.drawString(Fonts.mid, panel.getName(), x + 3, y + 3, 0xffffffff, true);
        RenderUtils2D.drawString(Fonts.mid, (panel.isExtended() ? "-" : "+"), x + width - 10, y + 2, 0xffffffff, true);
        return panel;
    }

    @Override
    public ModuleButton drawModule(ModuleButton module) {
        int x = module.getX();
        int y = module.getY();
        int width = this.getModuleWidth();
        int height = this.getModuleHeight();

        RenderUtils2D.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        if (module.getModule().isActive()) {
            if (module.hovered && module.getParent().getParent().allowAction(module.getParent(), mouseX, mouseY)) {
                RenderUtils2D.drawGradientRectVert(x + 2, y, x + width - 2, y + height, 0f, col.darker().darker().darker().getRGB(), col.darker().darker().getRGB());
            } else {
                RenderUtils2D.drawGradientRectVert(x + 2, y, x + width - 2, y + height, 0f, col.darker().getRGB(), col.darker().darker().getRGB());
            }
            RenderUtils2D.drawString(Fonts.normal, module.getName(), x + 5, y + 5, 0xffffffff, true);
        } else {
            if (module.hovered && module.getParent().getParent().allowAction(module.getParent(), mouseX, mouseY)) {
                RenderUtils2D.drawGradientRectVert(x + 2, y, x + width - 2, y + height, 0, foregroundCol.darker().getRGB(), foregroundCol.getRGB());
            } else {
                RenderUtils2D.drawGradientRectVert(x + 2, y, x + width - 2, y + height, 0, foregroundCol.brighter().getRGB(), foregroundCol.getRGB());
            }
            RenderUtils2D.drawString(Fonts.normal, module.getName(), x + 5, y + 5, 0xffaaaaaa, true);
        }
        RenderUtils2D.drawString(Fonts.normal, (module.isExtended() ? "-" : "+"), x + width - 10, y + 5, 0xffffffff, true);

        return module;
    }

    @Override
    protected Element drawElementKeybinding(ElementKeybinding option) {
        int x = option.getX();
        int y = option.getY();
        int width = this.getOptionWidth();
        int height = this.getOptionHeight();

        RenderUtils2D.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        if (option.isHovered() && option.getParent().getParent().getParent().allowAction(option.getParent().getParent(), mouseX, mouseY)) {
            RenderUtils2D.drawRect(x + 2, y + 1, x + width - 2, y + height - 1, foregroundCol.brighter().getRGB());
        }

        if (option.isListening()) {
            RenderUtils2D.drawString(Fonts.normal, "Listening..", x + 5, y + 4, 0xffffffff, false);
        } else {
            RenderUtils2D.drawString(Fonts.normal, "Keybind \u00A7p[\u00A7r" + (option.getModule().getKeybind() != Keyboard.KEY_ESCAPE ? Keyboard.getKeyName(option.getModule().getKeybind()) : "NONE") + "\u00A7p]", x + 5, y + 4, 0xffffffff, false);
        }


        return option;
    }

    @Override
    protected Element drawElementSelector(ElementSelector option) {
        int x = option.getX();
        int y = option.getY();
        int width = this.getOptionWidth();
        int height = this.getOptionHeight();

        RenderUtils2D.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        if (option.isHovered() && option.getParent().getParent().getParent().allowAction(option.getParent().getParent(), mouseX, mouseY)) {
            RenderUtils2D.drawRect(x + 2, y + 1, x + width - 2, y + height, col.darker().darker().getRGB());
        } else {
            RenderUtils2D.drawRect(x + 2, y + 1, x + width - 2, y + height, col.darker().getRGB());
        }

        RenderUtils2D.drawString(Fonts.normal, option.getName() + " \u00A7p[\u00A7r" + option.getSetting().getObject() + "\u00A7p]", x + 5, y + 5, 0xffffffff, false);

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

        RenderUtils2D.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        double stretch = x + (width * percent) - 2 <= x + 2 ? x + 3 : x + (width * percent) - 2;
        if (option.isHovered() && option.getParent().getParent().getParent().allowAction(option.getParent().getParent(), mouseX, mouseY)) {
            RenderUtils2D.drawRect(x + 2, y + 1, (int) stretch, y + height, col.darker().darker().getRGB());
        } else {
            RenderUtils2D.drawRect(x + 2, y + 1, (int) stretch, y + height, col.darker().getRGB());
        }
        RenderUtils2D.drawString(Fonts.normal, setting.getName() + " \u00A7p[\u00A7r" + setting.getObject() + "\u00A7p]", x + 5, y + 5, 0xffffffff, false);

        return option;
    }

    @Override
    protected Element drawElementToggle(ElementToggle option) {
        int x = option.getX();
        int y = option.getY();
        int width = this.getOptionWidth();
        int height = this.getOptionHeight();

        RenderUtils2D.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        if (option.isToggled()) {
            if (option.isHovered() && option.getParent().getParent().getParent().allowAction(option.getParent().getParent(), mouseX, mouseY)) {
                RenderUtils2D.drawRect(x + 2, y + 1, x + width - 2, y + height, col.darker().darker().getRGB());
            } else {
                RenderUtils2D.drawRect(x + 2, y + 1, x + width - 2, y + height, col.darker().getRGB());
            }
        } else {
            if (option.isHovered() && option.getParent().getParent().getParent().allowAction(option.getParent().getParent(), mouseX, mouseY)) {
                RenderUtils2D.drawRect(x + 2, y + 1, x + width - 2, y + height, foregroundCol.brighter().getRGB());
            }

        }
        RenderUtils2D.drawString(Fonts.normal, option.getName(), x + 5, y + 5, 0xffffffff, true);

        return option;
    }

    @Override
    public void drawFinal(int x, int y) {
        RenderUtils2D.drawRect(x, y, x + getModuleWidth(), y + 2, backgroundCol.getRGB());
    }

}
