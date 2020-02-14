package net.aer.gui.clickgui.elements;

import net.aer.gui.ClickGui;
import net.aer.gui.GuiStyle;
import net.aer.module.Category;
import net.aer.module.Module;
import net.aer.module.ModuleManager;

import java.util.ArrayList;

public class Panel {

    private GuiStyle style;

    private ArrayList<ModuleButton> modules = new ArrayList<>();

    public int x;
    public int y;
    public int xo;
    public int yo;

    private int width;
    private int height;

    private boolean extended;
    private boolean dragging;
    private ClickGui parent;
    private String name;
    private Category cat;

    public Panel(int xIn, int yIn, GuiStyle styleIn, String name, boolean panelExtended, Category category, ClickGui parent) {
        this.style = styleIn;
        this.x = xIn;
        this.y = yIn;
        this.width = style.getPanelWidth();
        this.height = style.getPanelHeight();
        this.name = name;
        this.cat = category;
        this.parent = parent;
        this.extended = panelExtended;

        createModuleButtons();
    }

    private void createModuleButtons() {
        for (Module m : ModuleManager.visibleModuleList) {
            if (m.getCategory() == this.cat) {
                this.modules.add(new ModuleButton(m, this, style, Boolean.parseBoolean(parent.ClickGuiProps.getProperty(m.getName() + "extended"))));
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY) {
        if (isDragging()) {
            x = mouseX + xo;
            y = mouseY + yo;
        }
        style.drawPanel(this, mouseX, mouseY);
        int offset = 0;
        if (this.isExtended()) {
            for (ModuleButton button : modules) {
                button.drawScreen(x, y + height + offset, mouseX, mouseY);
                offset += button.getOffset();
            }
        }
    }


	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (hovered(mouseX, mouseY) && mouseButton == 0) {
			this.dragging = true;
			this.xo = x - mouseX;
			this.yo = y - mouseY;
		}
		if (hovered(mouseX, mouseY) && mouseButton == 1) {
			this.extended = !this.extended;
		}
		if (this.extended) {
			for (ModuleButton em : modules) {
				em.mouseClicked(mouseX, mouseY, mouseButton);
			}
		}
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {
		this.dragging = false;
		if (this.extended) {
			for (ModuleButton em : modules) {
				em.mouseReleased(mouseX, mouseY, state);
			}
		}
	}

	public void keyTyped(char typedChar, int keyCode) {
		if (this.extended) {
			for (ModuleButton em : modules) {
				em.keyTyped(typedChar, keyCode);
			}
		}
    }

    private boolean hovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= y + this.height;
    }

    public void scroll(int scroll) {
        this.y -= (scroll / 10);
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

    public boolean isDragging() {
        return dragging;
    }

    public ClickGui getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public Category getCat() {
        return cat;
    }


    public ArrayList<ModuleButton> getModules() {
        return modules;
    }
}
