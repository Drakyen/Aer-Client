package me.aerclient.implementation.module.modules.combat;

import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.module.base.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class Aimbot extends Module {

    public Aimbot() {
        super("Aimbot", Category.COMBAT, "Looks at entity.");
    }

    public void onUpdate() {
        List list = minecraft.world.playerEntities;

        for(int k = 0; k < list.size(); k++){
            if(((EntityPlayer) list.get(k)).getName() == minecraft.player.getName()){
                continue;
            }

            EntityPlayer entPlayer = (EntityPlayer) list.get(k);

            if(minecraft.player.getDistanceToEntity(entPlayer) > minecraft.player.getDistanceToEntity((Entity) list.get(k))){
                entPlayer = (EntityPlayer) list.get(k);
            }

            float f = minecraft.player.getDistanceToEntity(entPlayer);
            if ( f < 5F && minecraft.player.canEntityBeSeen(entPlayer)){
                this.faceEntity(entPlayer);
            }
        }
    }

    public static synchronized void faceEntity(Entity entity) {
        final float[] rotations = getRotationsNeeded(entity);

        if (rotations != null) {
            minecraft.player.rotationYaw = rotations[0];
            minecraft.player.rotationPitch = rotations[1] + 1.0F;// 14
        }
    }

    public static float[] getRotationsNeeded(Entity entity) {
        if (entity == null) {
            return null;
        }

        final double diffX = entity.posX - minecraft.player.posX;
        final double diffZ = entity.posZ - minecraft.player.posZ;
        double diffY;

        if (entity instanceof EntityPlayer) {
            final EntityPlayer entityLivingBase = (EntityPlayer) entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (minecraft.player.posY + minecraft.player.getEyeHeight());
        } else if(entity instanceof EntityMob){
            final EntityMob entityLivingBase = (EntityMob) entity;
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (minecraft.player.posY + minecraft.player.getEyeHeight());
        }else{
            final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (minecraft.player.posY + minecraft.player.getEyeHeight());
        }

        final double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] {
                minecraft.player.rotationYaw + MathHelper.wrapDegrees(yaw - minecraft.player.rotationYaw), minecraft.player.rotationPitch + MathHelper.wrapDegrees(pitch - minecraft.player.rotationPitch)
        };
    }

}
