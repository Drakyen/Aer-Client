package me.aerclient.gui.click.base;

import me.aerclient.config.valuesystem.Value;
import me.aerclient.gui.base.basic.ClickableUI;
import me.aerclient.utils.world.TimerUtil;

import java.awt.*;

public abstract class Element<T extends Value> extends ClickableUI {

    protected ModuleButton parent;
    protected T value;
    private TimerUtil.Timer timer = new TimerUtil.Timer();

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
        if(timer.delay(400) && hovered(mouseX, mouseY)){
            parent.getParent().getParent().setHovered(this);
            timer.reset();
        } else if(!hovered(mouseX, mouseY)) {
            timer.reset();
        }
    }
}
