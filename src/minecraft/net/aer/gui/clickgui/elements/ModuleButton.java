package net.aer.gui.clickgui.elements;

import net.aer.gui.GuiStyle;
import net.aer.module.Module;
import net.aer.utils.valuesystem.*;

import java.util.ArrayList;

public class ModuleButton {

    public ArrayList<Element> menuElements = new ArrayList<>();

    private GuiStyle style;

    private Panel parent;
    private String name;
    private Module module;

    private int y;
    private int x;
    private int width;
    private int height;

    private int hoverTimer = 0;
    private int offset = 0;
    private boolean extended;


    public ModuleButton(Module moduleIn, Panel parentIn, GuiStyle styleIn, boolean extendedIn) {
        this.style = styleIn;
        this.module = moduleIn;
        this.name = module.getName();
        this.parent = parentIn;
        this.width = style.getModuleWidth();
        this.height = style.getModuleHeight();
        this.extended = extendedIn;

        this.addElements();
    }


    private void addElements() {
        for (Value v : ValueManager.getAllValuesFrom(module.getName())) {
            addElement(v);
        }
        menuElements.add(new ElementKeybinding(module, this, style));
    }


	private void addElement(Value v) {
		if (v instanceof BooleanValue) {
            menuElements.add(new ElementToggle(v, this, style));
        }
        if (v instanceof ModeValue) {
            menuElements.add(new ElementSelector(v, this, style));
        }
        if (v instanceof NumberValue) {
            menuElements.add(new ElementSlider(v, this, style));
        }

    }


    public void drawScreen(int xIn, int yIn, int mouseX, int mouseY) {
        this.x = xIn;
        this.y = yIn;

        style.drawModule(this);

        if (this.hovered(mouseX, mouseY)) {
            hoverTimer++;
            if (hoverTimer >= style.getHoverTime()) {
                style.drawDescription(this);
            }
        } else {
            hoverTimer = 0;
        }

        this.offset = style.getModuleHeight();

        if (this.extended) {
            for (Element e : this.menuElements) {
                e.drawScreen(x, y + offset, mouseX, mouseY);
                this.offset += style.getOptionHeight() + e.offset;
            }
        }
    }


	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (hovered(mouseX, mouseY) && mouseButton == 0) {
			this.module.toggle();
		}
		if (hovered(mouseX, mouseY) && mouseButton == 1) {
			this.extended = !this.extended;
		}
		if (this.extended) {
			for (Element e : menuElements) {
				e.onMouseClicked(mouseX, mouseY, mouseButton);
			}
		}
	}


	public void mouseReleased(int mouseX, int mouseY, int state) {
		if (this.extended) {
			for (Element e : menuElements) {
				e.onMouseReleased(mouseX, mouseY, state);
			}
		}
	}

	public void keyTyped(char typedChar, int keyCode) {
		if (this.extended) {
			for (Element e : menuElements) {
				e.keyTyped(typedChar, keyCode);
			}
		}
	}

    public boolean hovered(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }


    public void ValueUpdated() {
        this.module.onGuiValueUpdate();
    }

    public Panel getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public Module getModule() {
        return module;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isExtended() {
        return extended;
    }

    public int getOffset() {
        return offset;
    }
}
