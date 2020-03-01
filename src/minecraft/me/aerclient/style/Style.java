package me.aerclient.style;

import me.aerclient.config.valuesystem.Value;
import me.aerclient.module.base.Module;
import me.aerclient.ui.click.ClickGui;
import me.aerclient.ui.click.base.Element;
import me.aerclient.ui.click.base.ModuleButton;
import me.aerclient.ui.click.base.Panel;

import java.util.ArrayList;

public abstract class Style {
    public final String name;

    public Style(String name) {
        this.name = name;
    }

    public abstract Panel getNewPanel(int x, int y, String title, boolean extended, ClickGui parent);
    public abstract ModuleButton getNewModuleButton(int x, int y,  Module module, Panel parent);
    public abstract Element getNewElement(int x, int y, Value value, ModuleButton parent);

}
