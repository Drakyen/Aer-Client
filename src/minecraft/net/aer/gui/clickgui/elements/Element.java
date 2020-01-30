package net.aer.gui.clickgui.elements;

import net.aer.utils.threads.ColourFadeable;
import net.aer.utils.valuesystem.Value;

public class Element extends ColourFadeable {

	protected ModuleButton parent;
	protected Value setting;
	public int x;
	public int y;
	public int height;
	public int width;
	public int offset = 0;


	public Element(Value v, int width, int height, ModuleButton parent) {
		this.setting = v;
		this.width = width;
		this.height = height;
		this.parent = parent;
	}

	protected Element() {

	}

	public void onMouseClicked(int mouseX, int mouseY, int button) {

	}

	public void onMouseReleased(int mouseX, int mouseY, int state) {

	}

	public void keyTyped(char typedChar, int key) {

	}

	public void drawScreen(int x, int y, int mouseX, int mouseY) {

	}

	public void resetCols() {

	}


}
