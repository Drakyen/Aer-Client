package me.aer.implementation.utils.player;

import me.aer.implementation.utils.Utilities;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;


public class BlockUtil implements Utilities {
    public BlockUtil() {
    }

    public static boolean placeBlockScaffold(BlockPos pos) {
        Vec3d eyesPos = new Vec3d(minecraft.player.posX,
                minecraft.player.posY + minecraft.player.getEyeHeight(),
                minecraft.player.posZ);

        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();

            // check if side is visible (facing away from player)
            if (eyesPos.squareDistanceTo(
                    new Vec3d(pos).addVector(0.5, 0.5, 0.5)) >= eyesPos
                    .squareDistanceTo(
                            new Vec3d(neighbor).addVector(0.5, 0.5, 0.5)))
                continue;

            // check if neighbor can be right clicked
            if (!minecraft.world.getBlockState(neighbor).getBlock().canCollideCheck(minecraft.world.getBlockState(neighbor), false))
                continue;

            Vec3d hitVec = new Vec3d(neighbor).addVector(0.5, 0.5, 0.5)
                    .add(new Vec3d(side2.getDirectionVec()).scale(0.5));

            // check if hitVec is within range (4.25 blocks)
            if (eyesPos.squareDistanceTo(hitVec) > 18.0625)
                continue;

            // place block
            RotationUtil.faceVectorPacketInstant(hitVec);
            minecraft.playerController.processRightClickBlock(minecraft.player, minecraft.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            minecraft.player.swingArm(EnumHand.MAIN_HAND);

            return true;
        }

        return false;
    }
}
