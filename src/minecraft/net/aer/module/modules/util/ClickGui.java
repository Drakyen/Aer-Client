package net.aer.module.modules.util;

import net.aer.Aer;
import net.aer.module.Category;
import net.aer.module.Module;
import net.aer.utils.valuesystem.BooleanValue;
import net.aer.utils.valuesystem.NumberValue;
import org.lwjgl.input.Keyboard;

import java.awt.*;


public class ClickGui extends Module {

	private NumberValue red = new NumberValue("Red", 0f, 0f, 1f);
	private NumberValue green = new NumberValue("Green", 0.6f, 0f, 1f);
	private NumberValue blue = new NumberValue("Blue", 0.4f, 0f, 1f);
	private NumberValue alpha = new NumberValue("Alpha", 0.8f, 0.5f, 1f);
	private BooleanValue blur = new BooleanValue("Blur", true);

	public ClickGui() {
		super("ClickGui", Category.UTIL, "Gui!", Keyboard.KEY_RCONTROL);
	}

	public void onEnable() {
		Aer.clickGui.setCol(new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), alpha.getValue().floatValue()));
		minecraft.displayGuiScreen(Aer.clickGui);
		this.setActiveState(false);
	}

	public void onGuiValueUpdate() {
		Aer.clickGui.setCol(new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), alpha.getValue().floatValue()));
	}

}
