package net.aer.utils.world;

import net.aer.Aer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collection;

public class WorldUtil {
	private static Minecraft minecraft = Aer.minecraft;

	public static boolean checkForBlocksAroundPos(double posX, double posY, double posZ) {
		if (
				minecraft.world.isBlockSolid(new BlockPos(posX + 0.29, posY, posZ), false) ||
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
						minecraft.world.isBlockSolid(new BlockPos(posX - 0.29, posY + 1, posZ + 0.29), false)
		) {
			return true;
		}
		return false;
	}

	/**
	 * Gets a list of data for online players.
	 *
	 * @return
	 */
	public static ArrayList<NetworkPlayerInfo> getPlayerList() {
		ArrayList<NetworkPlayerInfo> players = new ArrayList<NetworkPlayerInfo>();
		Collection<NetworkPlayerInfo> playersC = Minecraft.getMinecraft().getConnection().getPlayerInfoMap();
		playersC.forEach((loadedPlayer) -> {
			players.add(loadedPlayer);
		});
		return players;
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
}
