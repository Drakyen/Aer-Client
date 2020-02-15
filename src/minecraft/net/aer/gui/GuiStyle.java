package net.aer.gui;

import net.aer.gui.clickgui.elements.Panel;
import net.aer.gui.clickgui.elements.*;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public abstract class GuiStyle {

    protected ScaledResolution resolution;
    protected Color colour;

    protected int panelWidth;
    protected int panelHeight;

    protected int moduleWidth;
    protected int moduleHeight;

    protected int optionWidth;
    protected int optionHeight;

    protected int mouseX;
    protected int mouseY;

    protected int hoverTime;

    public GuiStyle(int panelWidthIn, int panelHeightIn, int moduleWidthIn, int moduleHeightIn, int optionWidthIn, int optionHeightIn, int hoverTimeIn) {
        panelWidth = panelWidthIn;
        panelHeight = panelHeightIn;
        moduleWidth = moduleWidthIn;
        moduleHeight = moduleHeightIn;
        optionWidth = optionWidthIn;
        optionHeight = optionHeightIn;
        hoverTime = hoverTimeIn;
    }

    public abstract void setCol(Color col);

    public abstract void drawToolTip(String tooltip, int mouseX, int mouseY);

    public abstract void drawDescription(String description, int mouseX, int mouseY);

    public abstract Panel drawPanel(Panel panel);

    public abstract ModuleButton drawModule(ModuleButton button);

    public Element drawElement(Element option) {
        if (option instanceof ElementKeybinding) {
            return drawElementKeybinding((ElementKeybinding) option);
        } else if (option instanceof ElementToggle) {
            return drawElementToggle((ElementToggle) option);
        } else if (option instanceof ElementSlider) {
            return drawElementSlider((ElementSlider) option);
        } else if (option instanceof ElementSelector) {
            return drawElementSelector((ElementSelector) option);
        } else {
            return option;
        }
    }

    protected abstract Element drawElementKeybinding(ElementKeybinding option);

    protected abstract Element drawElementSelector(ElementSelector option);

    protected abstract Element drawElementSlider(ElementSlider option);

    protected abstract Element drawElementToggle(ElementToggle option);

    public int getPanelWidth() {
        return panelWidth;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public int getModuleWidth() {
        return moduleWidth;
    }

    public int getModuleHeight() {
        return moduleHeight;
    }

    public int getOptionWidth() {
        return optionWidth;
    }

    public int getOptionHeight() {
        return optionHeight;
    }

    public Color getColour() {
        return colour;
    }

    public ScaledResolution getResolution() {
        return resolution;
    }

    public int getHoverTime() {
        return hoverTime;
    }

    public void setRes(ScaledResolution res) {
        this.resolution = res;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

}
