package net.aer.module.modules.util;

import net.aer.Aer;
import net.aer.gui.styles.ModernStyle;
import net.aer.gui.styles.SciFiStyle;
import net.aer.module.Category;
import net.aer.module.Module;
import net.aer.utils.valuesystem.BooleanValue;
import net.aer.utils.valuesystem.ModeValue;
import net.aer.utils.valuesystem.NumberValue;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;


public class ClickGui extends Module {

	private ModeValue mode = new ModeValue("Style", "SciFi", "SciFi", "Modern");
	private NumberValue red = new NumberValue("Red", 0.5f, 0.0f, 1f);
	private NumberValue green = new NumberValue("Green", 0.9f, 0.0f, 1f);
	private NumberValue blue = new NumberValue("Blue", 0.6f, 0.0f, 1f);
	private NumberValue alpha = new NumberValue("Alpha", 0.8f, 0.5f, 1f);
	private BooleanValue blur = new BooleanValue("Blur", true);

	private String currentMode = mode.getValue();

	public ClickGui() {
		super("ClickGui", Category.UTIL, "Gui!", Keyboard.KEY_RCONTROL);
	}

	public void onEnable() {
		reloadGui();
		Aer.clickGui.setCol(new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), alpha.getValue().floatValue()));
		minecraft.displayGuiScreen(Aer.clickGui);
		this.setActiveState(false);
	}

	public void onGuiValueUpdate() {
		Aer.clickGui.setCol(new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), alpha.getValue().floatValue()));
		if (mode.getValue() != currentMode) {
			reloadGui();
			currentMode = mode.getValue();
		}
	}

	private void reloadGui() {
		int mx = Mouse.getX();
		int my = Mouse.getY();
		Aer.minecraft.displayGuiScreen(null);
		if (mode.getValue() == "SciFi") {
			Aer.clickGui = new net.aer.gui.ClickGui(new SciFiStyle());
		}
		if (mode.getValue() == "Modern") {
			Aer.clickGui = new net.aer.gui.ClickGui(new ModernStyle());
		}
		Aer.clickGui.setCol(new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), alpha.getValue().floatValue()));
		Aer.minecraft.displayGuiScreen(Aer.clickGui);
		Mouse.setCursorPosition(mx, my);
	}

}
