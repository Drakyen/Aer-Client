package me.aerclient.ui.click.base;

import me.aerclient.config.valuesystem.Value;
import me.aerclient.ui.base.basic.ClickableUI;

public abstract class Element<T extends Value> extends ClickableUI {

    protected ModuleButton parent;
    protected T value;

    /**
     * A base class for Click Gui elements that have a value and parent.
     */
    public Element(int xIn, int yIn, int widthIn, int heightIn, T valueIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn);
        value = valueIn;
        parent = parentIn;
    }

    public void updateCols(){}

}
