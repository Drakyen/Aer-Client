package me.aerclient.implementation.module.modules.combat;

import java.util.Random;

import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.module.base.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

import java.util.List;

// CREATED BY VacCat ON 3.3.2020

public class AntiAim extends Module {

    public Aimbot() {
        super("AntiAim", Category.COMBAT, "Moves your head around");
    }
  
    public void onUpdate() {
    float yaw = (180 + random.nextFloat(5 - 15));
		float pitch = 90;
		
		MC.player.networkHandler.sendPacket(
		new PlayerMoveC2SPacket.LookOnly(yaw, pitch, MC.player.onGround));
    }
}
