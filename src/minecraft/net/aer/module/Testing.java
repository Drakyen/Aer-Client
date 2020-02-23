package net.aer.module;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.aer.Aer;
import net.aer.events.EventGuiOpened;
import net.aer.gui.TestingGui;
import net.aer.utils.valuesystem.BooleanValue;
import net.minecraft.client.gui.GuiMainMenu;
import org.lwjgl.input.Keyboard;

public class Testing extends Module {


	private BooleanValue test;

	public Testing() {
		super("Testing", Category.UTIL, "Dev tests", Keyboard.KEY_Y);
	}

	public void setup() {

	}


	public void onEnable() {
		Aer.minecraft.displayGuiScreen(new TestingGui());
    }

	public void onDisable() {

	}


	public void onGuiValueUpdate() {

	}





}
