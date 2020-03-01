package me.aerclient.module.modules.combat;

import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.injection.events.world.EventPreTick;
import me.aerclient.module.base.Category;
import me.aerclient.module.base.Module;
import me.aerclient.config.valuesystem.BooleanValue;
import me.aerclient.config.valuesystem.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

import java.util.List;

public class Aura extends Module {


	private BooleanValue aura = new BooleanValue("Aura", true);
	private BooleanValue crystals = new BooleanValue("Crystals", true);
	private BooleanValue players = new BooleanValue("Players", true);

	private NumberValue<Integer> kaDelay = new NumberValue<>("Delay", 2, 1, 10, true);


	public Aura() {
		super("Aura", Category.COMBAT, "Automation of various combat");
	}

	int delay;

	@EventTarget
	public void onTick(EventPreTick event) {
		List list = minecraft.world.playerEntities;
		delay++;

		if(aura.getObject()){
			if(players.getObject()){

				for(int k = 0; k < list.size(); k++){
					if(((EntityPlayer) list.get(k)).getName() == minecraft.player.getName()){
						continue;
					}

					EntityPlayer entityplayer = (EntityPlayer) list.get(1);

					if(minecraft.player.getDistanceToEntity(entityplayer) > minecraft.player.getDistanceToEntity((Entity) list.get(k))){
						entityplayer = (EntityPlayer) list.get(k);
					}

					float f = minecraft.player.getDistanceToEntity(entityplayer);

					if(f < 4F && minecraft.player.canEntityBeSeen(entityplayer) && delay > kaDelay.getValue()){

						Aimbot.faceEntity(entityplayer);
						minecraft.playerController.attackEntity(minecraft.player, entityplayer);
						minecraft.player.swingArm(EnumHand.MAIN_HAND);
						delay = 0;
						continue;

					}

				}

			}
		}
	}

}
