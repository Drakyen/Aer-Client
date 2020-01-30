package net.aer.module;

import net.aer.gui.AerGui;
import net.aer.utils.valuesystem.BooleanValue;
import org.lwjgl.input.Keyboard;

public class Testing extends Module {


	private BooleanValue test;

	public Testing() {
		super("Testing", Category.UTIL, "Dev tests", Keyboard.KEY_Y);
	}

	public void setup() {

	}


	public void onEnable() {
		minecraft.displayGuiScreen(new AerGui());
	}

	public void onDisable() {

	}


	public void onGuiValueUpdate() {

	}


}
