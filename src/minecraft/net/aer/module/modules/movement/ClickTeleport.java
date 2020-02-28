package net.aer.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import net.aer.events.input.EventMouseInput;
import net.aer.events.entity.EventPostEntityUpdate;
import net.aer.events.entity.EventPreEntityUpdate;
import net.aer.events.render.EventRender3D;
import net.aer.module.base.Category;
import net.aer.module.base.Module;
import net.aer.render.render3D.RenderUtils3D;
import net.aer.utils.valuesystem.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

/**
 * Allows the player to teleport to where they're looking
 */
public class ClickTeleport extends Module {


	private NumberValue red = new NumberValue("Red", 0f, 0f, 1f);
	private NumberValue green = new NumberValue("Green", 0.6f, 0f, 1f);
	private NumberValue blue = new NumberValue("Blue", 0.4f, 0f, 1f);
	private NumberValue alpha = new NumberValue("Alpha", 0.4f, 0f, 1f);
	private double X = 0;
	private double Y = 0;
	private double Z = 0;
	private double XBlk = 0;
	private double YBlk = 0;
	private double ZBlk = 0;
	private double Time = 0;
	private boolean veiwBobbing;
	private BlockPos check;
	private BlockPos prevCheck;
	private int teleportDistance = 150;
	private int teleportKey = 2;
	private boolean run = false;
	ItemStack stackHeld;

	/**
	 * Constructor for ClickTeleport
	 */
	public ClickTeleport() {
		super("ClickTeleport", Category.MOVEMENT, "Allows you to teleport to wherever you look");
	}

	@EventTarget
	public void onInput(EventMouseInput event) {
		int Key = event.button;
		if (minecraft.isSingleplayer()) {
			teleportDistance = 150;
		} else {
			teleportDistance = 9;
		}
		if (minecraft.world == null || minecraft.player == null) {
			run = false;
			return;
		} else {
			ItemStack stackHeld = minecraft.player.inventory.getCurrentItem();
			if ((stackHeld.getItem() instanceof ItemAir || stackHeld.getItem() instanceof ItemSword) && Key == 1) {
				run = true;
			}
		}
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
		if (minecraft.world == null || minecraft.player == null) {
			return;
		}
		Entity local = minecraft.player;
		if (local != null) {
			local.noClip = false;
			local.onGround = true;
			local.fallDistance = 0f;
		}
	}

	@EventTarget
	public void preEntityUpdate(EventPreEntityUpdate event) {
		if (minecraft.world == null || minecraft.player == null) {
			return;
		}
		RayTraceResult block = minecraft.player.rayTrace(teleportDistance, 0);
		BlockPos pos = block.getBlockPos();

		if (minecraft.world.isAirBlock(pos) == true) {
			check = null;
			run = false;
			return;
		}
		XBlk = pos.getX();
		YBlk = pos.getY();
		ZBlk = pos.getZ();
		check = new BlockPos(XBlk, YBlk, ZBlk);

		while (minecraft.world.isAirBlock(check) == false || minecraft.world.isAirBlock(prevCheck) == false) {
			YBlk++;
			check = new BlockPos(XBlk, YBlk, ZBlk);
			prevCheck = new BlockPos(XBlk, YBlk - 1, ZBlk);
		}

		if (run == true) {
			Y = YBlk - minecraft.player.posY - 1;
			X = XBlk - minecraft.player.posX;
			Z = ZBlk - minecraft.player.posZ;
			X = X + 0.5;
			Z = Z + 0.5;
			minecraft.player.setVelocity(X, Y, Z);
			minecraft.player.onGround = true;
			minecraft.player.noClip = true;
			minecraft.player.fallDistance = 0f;
		}
	}

	@EventTarget
	public void postEntityUpdate(EventPostEntityUpdate event) {
		if (minecraft.world == null || minecraft.player == null) {
			return;
		}
		ItemStack stackHeld = minecraft.player.inventory.getCurrentItem();
		if (run == true) {
			minecraft.player.motionY -= minecraft.player.motionY;
			minecraft.player.motionX -= minecraft.player.motionX;
			minecraft.player.motionZ -= minecraft.player.motionZ;
			run = false;
		}
	}

	@EventTarget
	public void render3D(EventRender3D event) {
		if (minecraft.world == null || minecraft.player == null || check == null) {
			return;
		}
		BlockPos blockIn = new BlockPos(check.getX(), (prevCheck.getY() - 1), check.getZ());
		RenderUtils3D.drawBlockESP(blockIn, red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), alpha.getValue().floatValue(), red.getValue().floatValue(), green.getValue().floatValue(), green.getValue().floatValue(), alpha.getValue().floatValue(), 2f);
	}

}

  


