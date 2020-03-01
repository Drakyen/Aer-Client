package me.aerclient.gui.clickgui.elements;

import me.aerclient.gui.GuiStyle;
import me.aerclient.utils.Utilities;
import me.aerclient.utils.threads.ColourFadeable;
import me.aerclient.config.valuesystem.Value;

public abstract class Element extends ColourFadeable implements Utilities {

    protected ModuleButton parent;
    protected Value setting;

    protected GuiStyle style;

    protected int x;
    protected int y;

    protected boolean hovered;

    protected int width;
    protected int height;

    protected int offset = 0;

    protected int hoverTimer = 0;

    public Element(Value val, ModuleButton parent, GuiStyle styleIn) {
        this.style = styleIn;
        this.setting = val;
        this.parent = parent;
        this.width = style.getOptionWidth();
        this.height = style.getOptionHeight();
    }

    public abstract void onMouseClicked(int mouseX, int mouseY, int button);

    public abstract void onMouseReleased(int mouseX, int mouseY, int state);

    public abstract void keyTyped(char typedChar, int key);

    public abstract void updateCols();

    public abstract void drawScreen(int x, int y, int mouseX, int mouseY);

    public abstract boolean hovered(int mouseX, int mouseY);

    public boolean isHovered() {
        return hovered;
    }

    public String getName() {
        return setting.getName();
    }

    public Value getSetting() {
        return setting;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public ModuleButton getParent() {
        return parent;
    }
}
