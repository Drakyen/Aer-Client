package net.aer.utils.player;

import net.aer.Aer;
import net.aer.utils.world.WorldUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;

public class PlayerUtil {

    public PlayerUtil() {
    }


    public static String direction(final EntityLivingBase player) {
        final EnumFacing face = EnumFacing.getHorizontal(MathHelper.floor(player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
        int direction = face.getHorizontalIndex();
        if (Aer.minecraft.player.moveForward < 0.0f) {
            direction = face.getOpposite().getHorizontalIndex();
        }
        if (direction == 0) {
            return "SOUTH";
        }
        if (direction == 1) {
            return "WEST";
        }
        if (direction == 2) {
            return "NORTH";
        }
        if (direction == 3) {
            return "EAST";
        }
        return null;
    }

    public static String directionLeft(final EntityLivingBase player) {
        final EnumFacing face = EnumFacing.getHorizontal(MathHelper.floor(player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
        int direction = face.getHorizontalIndex();
        if (Aer.minecraft.player.moveForward < 0.0f) {
            direction = face.getOpposite().getHorizontalIndex();
        }
        if (direction == 0) {
            return "EAST";
        }
        if (direction == 1) {
            return "SOUTH";
        }
        if (direction == 2) {
            return "WEST";
        }
        if (direction == 3) {
            return "NORTH";
        }
        return null;
    }

    public static String directionRight(final EntityLivingBase player) {
        final EnumFacing face = EnumFacing.getHorizontal(MathHelper.floor(player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
        int direction = face.getHorizontalIndex();
        if (Aer.minecraft.player.moveForward < 0.0f) {
            direction = face.getOpposite().getHorizontalIndex();
        }
        if (direction == 0) {
            return "WEST";
        }
        if (direction == 1) {
            return "NORTH";
        }
        if (direction == 2) {
            return "EAST";
        }
        if (direction == 3) {
            return "SOUTH";
        }
        return null;
    }

    public static boolean hasSpaceToMove() {
        return WorldUtil.checkForBlocksAroundPos(Aer.minecraft.player.posX, Aer.minecraft.player.posY, Aer.minecraft.player.posZ);
    }

    public static String directionBackwards(EntityPlayerSP player) {
        final EnumFacing face = EnumFacing.getHorizontal(MathHelper.floor(player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
        int direction = face.getHorizontalIndex();
        if (Aer.minecraft.player.moveForward < 0.0f) {
            direction = face.getOpposite().getHorizontalIndex();
        }
        if (direction == 0) {
            return "NORTH";
        }
        if (direction == 1) {
            return "EAST";
        }
        if (direction == 2) {
            return "SOUTH";
        }
        if (direction == 3) {
            return "WEST";
        }
        return null;
    }


    /**
     * Returns the direction string, with the coord direction added - as in, "SOUTH [+Z]"
     *
     * @param player
     * @return
     */
    public static String direction2(EntityPlayerSP player) {
        final EnumFacing face = EnumFacing.getHorizontal(MathHelper.floor(player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
        int direction = face.getHorizontalIndex();
        if (Aer.minecraft.player.moveForward < 0.0f) {
            direction = face.getOpposite().getHorizontalIndex();
        }
        if (direction == 0) {
            return "SOUTH [+Z]";
        }
        if (direction == 1) {
            return "WEST [-X]";
        }
        if (direction == 2) {
            return "NORTH [-Z]";
        }
        if (direction == 3) {
            return "EAST [+X]";
        }
        return null;
    }

    /**
     * Returns the direction string, with the coord direction added and additional formatting codes - as in, "SOUTH \u00a7p[\u00a7r+Z\u00a7p]\u00a7r"
     *
     * @param player
     * @return
     */
    public static String direction3(EntityPlayerSP player) {
        final EnumFacing face = EnumFacing.getHorizontal(MathHelper.floor(player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
        int direction = face.getHorizontalIndex();
        if (Aer.minecraft.player.moveForward < 0.0f) {
            direction = face.getOpposite().getHorizontalIndex();
        }
        if (direction == 0) {
            return "SOUTH \u00a7p[\u00a7r+Z\u00a7p]\u00a7r";
        }
        if (direction == 1) {
            return "WEST \u00a7p[\u00a7r-X\u00a7p]\u00a7r";
        }
        if (direction == 2) {
            return "NORTH \u00a7p[\u00a7r-Z\u00a7p]\u00a7r";
        }
        if (direction == 3) {
            return "EAST \u00a7p[\u00a7r+X\u00a7p]\u00a7r";
        }
        return null;
    }

}
