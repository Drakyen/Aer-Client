package me.aerclient.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.injection.events.render.EventRenderWorld;
import me.aerclient.module.base.Category;
import me.aerclient.module.base.Module;
import me.aerclient.render.render3D.RenderUtils3D;
import me.aerclient.config.valuesystem.BooleanValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;

public class Tracers extends Module {

	private BooleanValue ShowPlayer = new BooleanValue("Players", true);
	private BooleanValue ShowMob = new BooleanValue("Monsters", false);
	private BooleanValue ShowPassive = new BooleanValue("Passives", false);
	private BooleanValue ShowItem = new BooleanValue("Items", false);
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
	public void onDraw3D(EventRenderWorld event) {
		for (Entity e : minecraft.world.getLoadedEntityList()) {
			if (e instanceof EntityMob && ShowMob.getObject()) {
				RenderUtils3D.drawTracerLine(e, 0.7f, 0.3f, 0.1f, 1f, 1f);
			} else if ((e instanceof EntityAnimal || e instanceof EntityWaterMob) && ShowPassive.getObject()) {
				RenderUtils3D.drawTracerLine(e, 0.6f, 1.0f, 0.2f, 1.0f, 1f);
			} else if (e instanceof EntityItem && ShowItem.getObject()) {
				RenderUtils3D.drawTracerLine(e, 0.2f, 0.3f, 1.0f, 1.0f, 1f);
			} else if ((e instanceof EntityPlayer && e != minecraft.player) && ShowPlayer.getObject()) {
				RenderUtils3D.drawTracerLine(e, 0.8f, 0.0f, 0.9f, 1.0f, 1f);
			}

		}

	}

}
