package net.aer.module.modules.combat;

import net.aer.module.Category;
import net.aer.module.Module;
import net.aer.utils.valuesystem.BooleanValue;

public class Aura extends Module {


	private BooleanValue aura = new BooleanValue("Aura", true);
	private BooleanValue crystals = new BooleanValue("Crystals", true);
	private BooleanValue players = new BooleanValue("Players", true);


	public Aura() {
		super("Aura", Category.COMBAT, "Automation of various combat");
	}


}
