package net.aer.gui.clickgui.elements;

import net.aer.render.render2D.Fonts;
import net.aer.render.render2D.RenderUtils2D;
import net.aer.utils.threads.ColourFadeThread;
import net.aer.utils.valuesystem.Value;

import java.awt.*;

public class ElementToggle extends Element {

	private boolean toggled;
	private Color fade;

	public ElementToggle(Value v, int width, int height, ModuleButton parent) {
		super();
		this.width = width;
		this.height = height;
		this.parent = parent;
		this.setting = v;
		fade = parent.parent.backgroundcol;
		this.toggled = (boolean) this.setting.getObject();
		if (this.toggled) {
			this.current = fade.brighter().brighter();
		} else {
			this.current = parent.parent.backgroundcol;
		}
	}

	public void drawScreen(int x, int y, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;
		if (this.toggled != (boolean) this.setting.getObject()) {
			this.toggled = (boolean) this.setting.getObject();
			try {
				Thread thread;
				fade = parent.parent.backgroundcol;
				if (this.toggled) {
					thread = new Thread(new ColourFadeThread(fade, fade.brighter().brighter(), 250, this));
					thread.start();
				} else {
					thread = new Thread(new ColourFadeThread(fade.brighter().brighter(), fade, 250, this));
					thread.start();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		RenderUtils2D.drawRect(x + 1, y, x + 2, y + height, 0xffffffff);
		RenderUtils2D.drawRect(x + width - 2, y, x + width - 1, y + height, 0xffffffff);
		RenderUtils2D.drawRect(x + 2, y + height - 1, x + width - 2, y + height, 0xff000000);
		RenderUtils2D.drawRect(x + 2, y, x + width - 2, y + 1, 0xff000000);
		RenderUtils2D.drawRect(x + 1, y, x + width - 1, y + height, this.current.getRGB());
		RenderUtils2D.drawCenteredString(Fonts.small, this.setting.getName(), x + width / 2, y + height / 2, parent.parent.textcol.getRGB(), false);


	}

	public void onMouseClicked(int mouseX, int mouseY, int button) {
		if (button == 0) {
			if (hovered(mouseX, mouseY)) {
				this.toggled = !this.toggled;
				this.setting.setObject(this.toggled);
				this.parent.ValueUpdated();
				fade = parent.parent.backgroundcol;
				try {
					Thread thread;
					if (this.toggled) {
						thread = new Thread(new ColourFadeThread(fade, fade.brighter().brighter(), 250, this));
						thread.start();
					} else {
						thread = new Thread(new ColourFadeThread(fade.brighter().brighter(), fade, 250, this));
						thread.start();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	private boolean hovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= y + this.height;
	}

	@Override
	public void resetCols() {
		fade = parent.parent.backgroundcol;
		if ((boolean) this.setting.getObject()) {
			this.current = fade.brighter().brighter();
		} else {
			this.current = parent.parent.backgroundcol;
		}
	}


}
