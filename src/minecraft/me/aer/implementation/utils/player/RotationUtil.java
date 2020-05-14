package me.aer.implementation.utils.player;

import me.aer.implementation.utils.Utilities;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtil implements Utilities {
    public RotationUtil() {
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(minecraft.player.posX,
                minecraft.player.posY + minecraft.player.getEyeHeight(),
                minecraft.player.posZ);
    }

    private static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.xCoord - eyesPos.xCoord;
        double diffY = vec.yCoord - eyesPos.yCoord;
        double diffZ = vec.zCoord - eyesPos.zCoord;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[]{
                minecraft.player.rotationYaw
                        + MathHelper.wrapDegrees(yaw - minecraft.player.rotationYaw),
                minecraft.player.rotationPitch + MathHelper.wrapDegrees(pitch - minecraft.player.rotationPitch)};
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = getNeededRotations2(vec);

        minecraft.getConnection().sendPacket(new CPacketPlayer.Rotation(rotations[0],
                rotations[1], minecraft.player.onGround));
    }
}
