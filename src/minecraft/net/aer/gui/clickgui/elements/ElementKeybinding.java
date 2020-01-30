package net.aer.gui.clickgui.elements;

import net.aer.module.Module;
import net.aer.render.render2D.Fonts;
import net.aer.render.render2D.RenderUtils2D;
import org.lwjgl.input.Keyboard;

public class ElementKeybinding extends Element {

	private Module module;
	public boolean listening = false;


	public ElementKeybinding(Module m, int width, int height, ModuleButton parent) {
		super();
		this.module = m;
		this.width = width;
		this.height = height;
		this.parent = parent;
	}

	public void drawScreen(int x, int y, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;
		RenderUtils2D.drawGradientRectVert(x + 1, y, x + 2, y + height, 0, 0xffffffff, 0xff000000);
		RenderUtils2D.drawGradientRectVert(x + width - 2, y, x + width - 1, y + height, 0, 0xffffffff, 0xff000000);
		RenderUtils2D.drawRect(x + 1, y + height - 1, x + width - 1, y + height, 0xff000000);
		RenderUtils2D.drawRect(x + 1, y, x + width - 1, y + height, parent.parent.backgroundcol.darker().getRGB());
		if (listening) {
			RenderUtils2D.drawCenteredString(Fonts.small, "Listening..", x + width / 2, y + height / 2, parent.parent.textcol.getRGB(), false);
		} else {
			RenderUtils2D.drawCenteredString(Fonts.small, "Keybind [" + (this.module.getKeybind() != Keyboard.KEY_ESCAPE ? Keyboard.getKeyName(this.module.getKeybind()) : "None") + "]", x + width / 2, y + height / 2, parent.parent.textcol.getRGB(), false);
		}
	}

	public void onMouseClicked(int mouseX, int mouseY, int button) {
		if (button == 0) {
			if (hovered(mouseX, mouseY)) {
				this.listening = true;
			}
		}
	}

	public void keyTyped(char typedChar, int key) {
		if (listening) {
			if (key == Keyboard.KEY_ESCAPE) {
				this.module.setKeybind(Keyboard.KEY_NONE);
			} else {
				this.module.setKeybind(key);
			}
			this.listening = false;
		}
	}

	private boolean hovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= y + this.height;
	}

}
