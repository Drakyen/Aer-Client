package net.aer.gui.clickgui.elements;

import net.aer.gui.ClickGui;
import net.aer.module.Category;
import net.aer.module.Module;
import net.aer.module.ModuleManager;
import net.aer.render.render2D.Fonts;
import net.aer.render.render2D.RenderUtils2D;

import java.awt.*;
import java.util.ArrayList;

public class Panel {

	public int x;
	public int y;
	private int xo;
	private int yo;
	private int width;
	private int height;
	public boolean extended;
	private boolean dragging;
	private ClickGui parent;
	public String name;
	private Category cat;
	public ArrayList<ModuleButton> modules = new ArrayList<>();
	public Color backgroundcol;
	public Color textcol;
	public Color col;
	public int stackPos = 0;

	public Panel(int x, int y, int width, int height, String name, boolean panelExtended, Category category, ClickGui parent) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.cat = category;
		this.parent = parent;
		this.extended = panelExtended;
		this.col = parent.col;
		backgroundcol = new Color(parent.col.darker().getRed() / 2, parent.col.darker().getGreen() / 2, parent.col.darker().getBlue() / 2, 170);
		textcol = new Color(parent.col.brighter().getRed(), parent.col.brighter().getGreen(), parent.col.brighter().getBlue(), 255);
		for (Module m : ModuleManager.visibleModuleList) {
			if (m.getCategory() == this.cat) {
				this.modules.add(new ModuleButton(m, m.getName(), this, this.width, this.height, Boolean.parseBoolean(parent.ClickGuiProps.getProperty(m.getName() + "extended"))));
			}
		}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (dragging) {
			this.x = mouseX + xo;
			this.y = mouseY + yo;
		}
		RenderUtils2D.drawGradientRectVert(x, y, x + width, y + height, 0, backgroundcol.getRGB(), 0xff121212);
		RenderUtils2D.drawGradientRectVert(x - 1, y, x, y + height, 0, 0xffffffff, 0xff000000);
		RenderUtils2D.drawGradientRectVert(x + width, y, x + width + 1, y + height, 0, 0xffffffff, 0xff000000);
		RenderUtils2D.drawCenteredString(Fonts.mid, this.name, x + width / 2, y + height / 2, textcol.brighter().brighter().getRGB(), true);


		if (this.extended) {
			int offset = this.height;
			for (ModuleButton em : modules) {
				em.drawScreen(this.x, this.y + offset, mouseX, mouseY);
				offset += this.height + em.offset;
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

	public void resetCols() {
		this.col = parent.col;
		backgroundcol = new Color(parent.col.darker().getRed() / 2, parent.col.darker().getGreen() / 2, parent.col.darker().getBlue() / 2, 170);
		textcol = new Color(parent.col.brighter().getRed(), parent.col.brighter().getGreen(), parent.col.brighter().getBlue(), 255);
		for (ModuleButton m : modules) {
			m.resetCols();
		}
	}

	public void setHoveredModule(ModuleButton moduleButton) {
		parent.setHoveredModule(moduleButton);
	}

	public void scroll(int scroll) {
		this.y -= (scroll / 10);
	}


}
