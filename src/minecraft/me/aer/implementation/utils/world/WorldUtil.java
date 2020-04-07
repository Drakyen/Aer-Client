package me.aer.implementation.utils.world;

import me.aer.implementation.utils.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collection;

public class WorldUtil implements Utilities {

    public static boolean isSpaceForPlayerAt(double posX, double posY, double posZ) {
        return minecraft.world.isBlockSolid(new BlockPos(posX + 0.29, posY, posZ), false) ||
                minecraft.world.isBlockSolid(new BlockPos(posX + 0.29, posY + 1, posZ), false) ||

                minecraft.world.isBlockSolid(new BlockPos(posX - 0.29, posY, posZ), false) ||
                minecraft.world.isBlockSolid(new BlockPos(posX - 0.29, posY + 1, posZ), false) ||

                minecraft.world.isBlockSolid(new BlockPos(posX, posY, posZ + 0.29), false) ||
                minecraft.world.isBlockSolid(new BlockPos(posX, posY + 1, posZ + 0.29), false) ||

                minecraft.world.isBlockSolid(new BlockPos(posX, posY, posZ - 0.29), false) ||
                minecraft.world.isBlockSolid(new BlockPos(posX, posY + 1, posZ - 0.29), false) ||

                minecraft.world.isBlockSolid(new BlockPos(posX + 0.29, posY, posZ + 0.29), false) ||
                minecraft.world.isBlockSolid(new BlockPos(posX + 0.29, posY + 1, posZ + 0.29), false) ||

                minecraft.world.isBlockSolid(new BlockPos(posX - 0.29, posY, posZ - 0.29), false) ||
                minecraft.world.isBlockSolid(new BlockPos(posX - 0.29, posY + 1, posZ - 0.29), false) ||

                minecraft.world.isBlockSolid(new BlockPos(posX + 0.29, posY, posZ - 0.29), false) ||
                minecraft.world.isBlockSolid(new BlockPos(posX + 0.29, posY + 1, posZ - 0.29), false) ||

                minecraft.world.isBlockSolid(new BlockPos(posX - 0.29, posY, posZ + 0.29), false) ||
                minecraft.world.isBlockSolid(new BlockPos(posX - 0.29, posY + 1, posZ + 0.29), false);
    }

	/**
	 * Gets a list of data for online players.
	 *
	 * @return
	 */
	public static ArrayList<NetworkPlayerInfo> getPlayerList() {
		Collection<NetworkPlayerInfo> playersC = Minecraft.getMinecraft().getConnection().getPlayerInfoMap();
		return new ArrayList<>(playersC);
	}

	/**
	 * Gets a list of names for online players.
	 *
	 * @return
	 */
	public static ArrayList<String> getPlayerNamesList() {
		ArrayList<String> players = new ArrayList<>();
		Collection<NetworkPlayerInfo> playersC = Minecraft.getMinecraft().getConnection().getPlayerInfoMap();
		playersC.forEach((loadedPlayer) -> {
			assert loadedPlayer.getDisplayName() != null;
			players.add(loadedPlayer.getDisplayName().getUnformattedText());
		});
		return players;
	}

	/**
	 * Gets a list of names for online players within render distance
	 *
	 * @return
	 */
	public static ArrayList<String> getPlayerNamesInRender() {
		ArrayList<String> players = new ArrayList<>();
		for (Entity e : minecraft.world.getLoadedEntityList()) {
			if ((e instanceof EntityPlayer && e != minecraft.player)) {
				players.add(e.getName());
			}
		}
		return players;
	}

	private static int transparencyList = 0;
	private static int cullingList = 0;

	private static boolean renderBlocksTransparent = false;
	private static boolean allowCulling = true;

	public static boolean renderBlocksTransparent() {
            return renderBlocksTransparent;
	}

	public static boolean allowCulling() {
		return allowCulling;
	}

	public static void setBlocksTransparent(boolean transparent, boolean force) {
		if (force) {
			renderBlocksTransparent = transparent;
			if (transparent) {
				transparencyList++;
			} else {
				transparencyList = 0;
			}
		} else {
			if (!transparent) {
				if (transparencyList <= 1) {
					transparencyList = 0;
					renderBlocksTransparent = false;
				} else {
					transparencyList--;
				}
			} else {
				transparencyList++;
				renderBlocksTransparent = true;
			}
		}
	}

	public static void setCulling(boolean culling, boolean force) {
		if (force) {
			allowCulling = culling;
			if (!culling) {
				cullingList++;
			} else {
				cullingList = 0;
			}
		} else {
			if (culling) {
				if (cullingList <= 1) {
					cullingList = 0;
					allowCulling = true;
                } else {
                    cullingList--;
                }
            } else {
                cullingList++;
                allowCulling = false;
            }
        }
    }

    public static BlockPos fixPosForRender(BlockPos posIn) {
        double x = (posIn.getX() - minecraft.player.posX) + (minecraft.player.posX - minecraft.getRenderManager().renderPosX);
        double y = (posIn.getY() - minecraft.player.posY) + (minecraft.player.posY - minecraft.getRenderManager().renderPosY);
        double z = (posIn.getZ() - minecraft.player.posZ) + (minecraft.player.posZ - minecraft.getRenderManager().renderPosZ);
        return new BlockPos(x, y, z);
    }

}

