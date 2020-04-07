package me.aer.visual.gui.click.base;

import me.aer.config.valuesystem.Value;
import me.aer.implementation.utils.world.TimerUtil;
import me.aer.visual.gui.base.basic.ClickableUI;

import java.awt.*;

public abstract class Element<T extends Value> extends ClickableUI {

    protected ModuleButton parent;
    protected T value;
    private TimerUtil timer = new TimerUtil();

    /**
     * A base class for Click Gui elements that have a value and parent.
     */
    public Element(int xIn, int yIn, int widthIn, int heightIn, T valueIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn);
        value = valueIn;
        parent = parentIn;
    }

    public void updateCols(Color newCol){}

    @Override
    public void render(int mouseX, int mouseY) {
        if (parent.getParent().getParent().isShowTooltips() && timer.delay(parent.getParent().getParent().getTooltipDelay()) && hovered(mouseX, mouseY)) {
            parent.getParent().getParent().setHovered(this);
            timer.reset();
        } else if (!hovered(mouseX, mouseY)) {
            timer.reset();
        }
        if (y + height < parent.getY() + parent.getActualHeight()) {
            return;
        }
        renderElement(mouseX, mouseY);
    }

    @Override
    public void scrolled(int amount) {

    }

    public abstract void renderElement(int mouseX, int mouseY);

    public T getSetting() {
        return value;
    }
}
