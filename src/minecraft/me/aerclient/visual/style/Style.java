package me.aerclient.visual.style;

import me.aerclient.config.valuesystem.BooleanValue;
import me.aerclient.config.valuesystem.ModeValue;
import me.aerclient.config.valuesystem.NumberValue;
import me.aerclient.config.valuesystem.Value;
import me.aerclient.visual.gui.click.base.Panel;
import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.module.base.Module;
import me.aerclient.visual.gui.click.ClickGuiUI;
import me.aerclient.visual.gui.click.base.*;
import me.aerclient.implementation.utils.Utilities;

import java.awt.*;

public abstract class Style implements Utilities  {
    public final String name;

    public Style(String name) {
        this.name = name;
    }

    public abstract Panel getNewPanel(int x, int y, String title, boolean extended, ClickGuiUI parent, Category catIn);
    public abstract ModuleButton getNewModuleButton(int x, int y, boolean extended, Module module, Panel parent);
    public Element getNewElement(int x, int y, Value value, ModuleButton parent){
        if (value instanceof BooleanValue) {
            return getNewToggle(x, y, value, parent);
        }
        if (value instanceof ModeValue) {
            return getNewSelector(x, y, value, parent);
        }
        if (value instanceof NumberValue) {
            return getNewSlider(x, y, value, parent);
        }
        return null;
    }

    protected abstract Slider getNewSlider(int x, int y, Value value, ModuleButton parent);

    protected abstract Selector getNewSelector(int x, int y, Value value, ModuleButton parent);

    protected abstract Toggle getNewToggle(int x, int y, Value value, ModuleButton parent);

    public abstract Keybinding getNewKeybinding(int x, int y, ModuleButton parent);

    public abstract void renderTooltip(String tooltip, int mouseX, int mouseY, Color col);
}
