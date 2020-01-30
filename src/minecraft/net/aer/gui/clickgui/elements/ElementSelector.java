package net.aer.gui.clickgui.elements;

import net.aer.render.render2D.Fonts;
import net.aer.render.render2D.RenderUtils2D;
import net.aer.utils.valuesystem.ModeValue;
import net.aer.utils.valuesystem.Value;

public class ElementSelector extends Element {

	public boolean extended = false;

	public ElementSelector(Value v, int width, int height, ModuleButton parent) {
		super();
		this.width = width;
		this.height = height;
		this.parent = parent;
		this.setting = v;
	}

	public void drawScreen(int x, int y, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;

		this.offset = 1;
		if (this.extended) {
			for (String s : ((ModeValue) this.setting).getModes()) {
				this.offset += 16;
				RenderUtils2D.drawRect(x + 3, y + offset, x + width - 3, y + 16 + offset, parent.parent.backgroundcol.getRGB());
				if (s.equalsIgnoreCase((String) this.setting.getObject())) {
					RenderUtils2D.drawRect(x + 3, y + offset, x + width - 3, y + 16 + offset, parent.parent.backgroundcol.brighter().getRGB());
				}
				RenderUtils2D.drawCenteredString(Fonts.small, s, x + width / 2, y + (height / 2) + offset, parent.parent.textcol.getRGB(), false);
				RenderUtils2D.drawRect(x + 3, y + offset, x + 4, y + 16 + offset, 0x88ffffff);
				RenderUtils2D.drawRect(x + width - 3, y + offset, x + width - 4, y + 16 + offset, 0x88ffffff);
			}
			RenderUtils2D.drawRect(x + 3, y + 16 + offset, x + width - 3, y + 16 + offset + 1, 0xff000000);
			this.offset -= 1;
		}
		RenderUtils2D.drawRect(x + 1, y, x + 2, y + height, 0xffffffff);
		RenderUtils2D.drawRect(x + width - 2, y, x + width - 1, y + height, 0xffffffff);
		RenderUtils2D.drawRect(x + 2, y + height - 1, x + width - 2, y + height, 0xff000000);
		RenderUtils2D.drawRect(x + 2, y, x + width - 2, y + 1, 0xff000000);
		RenderUtils2D.drawRect(x + 1, y, x + width - 1, y + height, parent.parent.backgroundcol.getRGB());
		RenderUtils2D.drawCenteredString(Fonts.small, this.setting.getName() + " [" + (String) this.setting.getObject() + "]", x + width / 2, y + height / 2, parent.parent.textcol.getRGB(), false);


	}

	public void onMouseClicked(int mouseX, int mouseY, int button) {
		if (hovered(mouseX, mouseY)) {
			this.extended = !this.extended;
		}
		if (getHoveredOption(mouseX, mouseY) != "" && this.extended) {
			this.setting.setObject(getHoveredOption(mouseX, mouseY));
			this.parent.ValueUpdated();
		}
	}

	private String getHoveredOption(int mouseX, int mouseY) {
		int o = 0;
		for (String s : ((ModeValue) this.setting).getModes()) {
			o += 16;
			if (hovered(mouseX, mouseY - o)) {
				return s;
			}
		}
		return "";
	}


	private boolean hovered(int mouseX, int mouseY) {
		return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
	}


}
