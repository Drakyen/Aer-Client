package net.aer.gui.clickgui.elements;

import net.aer.module.Module;
import net.aer.render.render2D.Fonts;
import net.aer.render.render2D.RenderUtils2D;
import net.aer.utils.threads.ColourFadeThread;
import net.aer.utils.threads.ColourFadeable;
import net.aer.utils.valuesystem.*;

import java.awt.*;
import java.util.ArrayList;

public class ModuleButton extends ColourFadeable {

	public Panel parent;
	public String name;
	public Module module;
	private int width;
	private int height;
	int offset = 0;
	private Color fade;
	public boolean extended;
	private int y;
	private int x;
	private int alpha;
	public ArrayList<Element> menuElements = new ArrayList<>();
	public int hoveredTimer = 0;


	public ModuleButton(Module m, String name, Panel elementPanel, int width, int height, boolean extended) {
		this.module = m;
		this.name = name;
		this.parent = elementPanel;
		this.width = width;
		this.height = height;
		this.current = parent.backgroundcol;
		this.extended = extended;

		this.setupCols();


		this.addElements();
	}


	private void setupCols() {
		this.alpha = parent.col.getAlpha();
		fade = parent.col;

		this.runFade();

	}


	private void runFade() {
		try {
			fade = parent.col;
			if (this.module.isActive()) {
				Thread thread = new Thread(new ColourFadeThread(parent.backgroundcol, fade.brighter().brighter(), 250, this));
				thread.start();
			} else {
				Thread thread = new Thread(new ColourFadeThread(fade.brighter().brighter(), parent.backgroundcol, 250, this));
				thread.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void addElements() {
		for (Value v : ValueManager.getAllValuesFrom(module.getName())) {
			addElement(v);
		}
		menuElements.add(new ElementKeybinding(module, width, height - 2, this));
	}


	private void addElement(Value v) {
		if (v instanceof BooleanValue) {
			menuElements.add(new ElementToggle(v, width, height - 2, this));
		}
		if (v instanceof ModeValue) {
			menuElements.add(new ElementSelector(v, width, height - 2, this));
		}
		if (v instanceof NumberValue) {
			menuElements.add(new ElementSlider(v, width, height - 2, this));
		}

	}


	public void drawScreen(int x, int y, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;
		Color col = this.current;
		Color backgroundcol = new Color(col.getRed(), col.getGreen(), col.getBlue(), 200).darker();
		Color textcol = new Color(col.brighter().getRed(), col.brighter().getGreen(), col.brighter().getBlue(), 255);


		RenderUtils2D.drawRect(x, y, x + 1, y + height, 0xff000000);
		RenderUtils2D.drawRect(x + width - 1, y, x + width, y + height, 0xff000000);
		RenderUtils2D.drawGradientRectVert(x + 1, y, x + width - 1, y + height, 0, backgroundcol.getRGB(), backgroundcol.darker().getRGB());
		RenderUtils2D.drawCenteredString(Fonts.normal, this.name, x + width / 2, y + height / 2, textcol.brighter().getRGB(), true);
		this.offset = 0;
		if (this.extended) {
			for (Element e : this.menuElements) {
				e.drawScreen(x, y + this.height + this.offset, mouseX, mouseY);
				this.offset += e.height + e.offset - 1;
			}
		}
		if (this.hovered(mouseX, mouseY)) {
			hoveredTimer++;
		} else {
			hoveredTimer = 0;
		}

		if (hoveredTimer > 50) {
			parent.setHoveredModule(this);
		}

	}


	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (hovered(mouseX, mouseY) && mouseButton == 0) {
			this.module.toggle();
			try {
				fade = parent.col;
				if (this.module.isActive()) {
					Thread thread = new Thread(new ColourFadeThread(parent.backgroundcol, fade.brighter().brighter(), 250, this));
					thread.start();
				} else {
					Thread thread = new Thread(new ColourFadeThread(fade.brighter().brighter(), parent.backgroundcol, 250, this));
					thread.start();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
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

	public void resetCols() {
		fade = parent.col;
		if (module.isActive()) {
			this.current = fade.brighter().brighter();
		} else {
			this.current = parent.backgroundcol;
		}
		for (Element e : menuElements) {
			e.resetCols();
		}

	}


	public void ValueUpdated() {
		this.module.onGuiValueUpdate();
	}


}
