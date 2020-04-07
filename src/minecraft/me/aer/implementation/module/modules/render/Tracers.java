package me.aer.implementation.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;
import me.aer.config.valuesystem.BooleanValue;
import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;
import me.aer.injection.events.render.EventRenderWorld;

public class Tracers extends Module {

    private BooleanValue ShowPlayer = new BooleanValue("Players", "Whether to draw tracers to players", true);
    private BooleanValue ShowMob = new BooleanValue("Monsters", "Whether to draw tracers to monsters", false);
    private BooleanValue ShowPassive = new BooleanValue("Passives", "Whether to draw tracers to passive mobs", false);
    private BooleanValue ShowItem = new BooleanValue("Items", "Whether to draw tracers to items", false);
    private boolean veiwBobbing;

    public Tracers() {
        super("Tracers", Category.RENDER, "Draws lines to nearby entities");
    }


    public void onEnable() {
        if (minecraft.getRenderManager().options != null) {
            veiwBobbing = minecraft.getRenderManager().options.viewBobbing;
			minecraft.getRenderManager().options.viewBobbing = false;
		}
	}


	public void onDisable() {
		if (minecraft.getRenderManager().options != null) {
			minecraft.getRenderManager().options.viewBobbing = veiwBobbing;
		}
	}


    @EventTarget
    public void onRender(EventRenderWorld event) {

    }

}
