package net.aer.gui.clickgui.elements;

import net.aer.render.render2D.Fonts;
import net.aer.render.render2D.RenderUtils2D;
import net.aer.utils.valuesystem.NumberValue;
import net.aer.utils.valuesystem.Value;
import net.minecraft.util.math.MathHelper;

import java.text.DecimalFormat;

public class ElementSlider extends Element {

	private double percent = 0;
	private boolean dragging;
	private DecimalFormat format = new DecimalFormat("0.00");


	public ElementSlider(Value v, int width, int height, ModuleButton parent) {
		super();
		this.width = width;
		this.height = height;
		this.parent = parent;
		this.setting = v;
	}

	public void drawScreen(int x, int y, int mouseX, int mouseY) {
		this.x = x;
		this.y = y;
		double val = ((NumberValue) this.setting).getValue().doubleValue();
		if (dragging) {
			double diff = ((NumberValue) this.setting).getMax().doubleValue() - ((NumberValue) this.setting).getMin().doubleValue();
			val = ((NumberValue) this.setting).getMin().doubleValue() + (MathHelper.clamp((double) (mouseX - x) / (width - 2), 0, 1)) * diff;
			this.setting.setObject(Double.parseDouble(format.format(val)));
			this.parent.ValueUpdated();
		}

		percent = ((NumberValue) this.setting).getValue().doubleValue() / ((NumberValue) this.setting).getMax().doubleValue();
		RenderUtils2D.drawRect(x + 1, y, x + 2, y + height, 0xffffffff);
		RenderUtils2D.drawRect(x + width - 2, y, x + width - 1, y + height, 0xffffffff);
		RenderUtils2D.drawRect(x + 2, y + height - 1, x + width - 2, y + height, 0xff000000);
		RenderUtils2D.drawRect(x + 2, y, x + width - 2, y + 1, 0xff000000);
		RenderUtils2D.drawRect(x + 1, y, x + width - 1, y + height, parent.parent.backgroundcol.darker().getRGB());
		RenderUtils2D.drawGradientRectHoriz(x + 2, y, (int) (x + (width * percent) - 2 <= x + 2 ? x + 2 : x + (width * percent) - 2), y + height, 0f, parent.parent.backgroundcol.brighter().brighter().getRGB(), parent.parent.backgroundcol.getRGB());
		RenderUtils2D.drawRect((int) (x + (width * percent) - 2 <= x + 2 ? x + 2 : x + (width * percent) - 2) - 1, y, (int) (x + (width * percent) - 2 <= x + 2 ? x + 2 : x + (width * percent) - 2), y + height, parent.parent.backgroundcol.brighter().getRGB());
		RenderUtils2D.drawCenteredString(Fonts.small, this.setting.getName() + " [" + ((NumberValue) this.setting).getObject() + "]", x + width / 2, y + height / 2, parent.parent.textcol.getRGB(), false);

	}

	public void onMouseClicked(int mouseX, int mouseY, int button) {
		if (button == 0) {
			if (hovered(mouseX, mouseY)) {
				this.dragging = true;
			}
		}
	}

	public void onMouseReleased(int mouseX, int mouseY, int state) {
		this.dragging = false;
	}

	private boolean hovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= y + this.height;
	}


}
