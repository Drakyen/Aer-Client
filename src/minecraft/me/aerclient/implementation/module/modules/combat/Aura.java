package me.aerclient.implementation.module.modules.combat;

import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.config.valuesystem.BooleanValue;
import me.aerclient.config.valuesystem.NumberValue;
import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.module.base.Module;
import me.aerclient.injection.events.world.EventPreTick;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

import java.util.List;

public class Aura extends Module {


	private BooleanValue aura = new BooleanValue("Aura", "Whether to punch entities", true);
	private BooleanValue crystals = new BooleanValue("Crystals", "Whether to attack with crystals", true);
	private BooleanValue players = new BooleanValue("Players", "Whether to attack players", true);

	private NumberValue<Integer> kaDelay = new NumberValue<>("Delay", "How long to wait between attacks", 2, 1, 10, true);


	public Aura() {
		super("Aura", Category.COMBAT, "Automation of various combat");
	}

	int delay;

	@EventTarget
	public void onTick(EventPreTick event) {
		List<EntityPlayer> list = minecraft.world.playerEntities;
		delay++;

		if (aura.getObject()) {
			if (players.getObject()) {

				for (EntityPlayer o : list) {
					if (o.getName().equals(minecraft.player.getName())) {
						continue;
					}

					EntityPlayer entityplayer = list.get(1);

					if (minecraft.player.getDistanceToEntity(entityplayer) > minecraft.player.getDistanceToEntity(o)) {
						entityplayer = o;
					}

					float f = minecraft.player.getDistanceToEntity(entityplayer);

					if (f < 4F && minecraft.player.canEntityBeSeen(entityplayer) && delay > kaDelay.getValue()) {

						Aimbot.faceEntity(entityplayer);
						minecraft.playerController.attackEntity(minecraft.player, entityplayer);
						minecraft.player.swingArm(EnumHand.MAIN_HAND);
						delay = 0;
					}

				}

			}
		}
	}

}
