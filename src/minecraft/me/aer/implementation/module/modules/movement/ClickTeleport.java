package me.aer.implementation.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;
import me.aer.config.valuesystem.NumberValue;
import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;
import me.aer.injection.events.entity.EventPostEntityUpdate;
import me.aer.injection.events.entity.EventPreEntityUpdate;
import me.aer.injection.events.input.EventMouseInput;
import me.aer.injection.events.render.EventRenderWorld;
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


    private NumberValue<Float> red = new NumberValue<>("Red", "Red component of the outline box", 0f, 0f, 1f, true);
    private NumberValue<Float> green = new NumberValue<>("Green", "Green component of the outline box", 0.6f, 0f, 1f, true);
    private NumberValue<Float> blue = new NumberValue<>("Blue", "Blue component of the outline box", 0.4f, 0f, 1f, true);
    private NumberValue<Float> alpha = new NumberValue<>("Alpha", "Alpha/Transparency component of the outline box", 0.4f, 0f, 1f, true);
    private boolean viewBobbing;
    private BlockPos check;
    private BlockPos prevCheck;
    private int teleportDistance = 150;
    private boolean run = false;

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
		} else {
			ItemStack stackHeld = minecraft.player.inventory.getCurrentItem();
			if ((stackHeld.getItem() instanceof ItemAir || stackHeld.getItem() instanceof ItemSword) && Key == 1) {
				run = true;
			}
		}
	}


	public void onEnable() {
		if (minecraft.getRenderManager().options != null) {
            viewBobbing = minecraft.getRenderManager().options.viewBobbing;
            minecraft.getRenderManager().options.viewBobbing = false;
		}
	}


	public void onDisable() {
        if (minecraft.getRenderManager().options != null) {
            minecraft.getRenderManager().options.viewBobbing = viewBobbing;
        }
        if (minecraft.world == null || minecraft.player == null) {
            return;
        }
        Entity local = minecraft.player;
        local.noClip = false;
        local.onGround = true;
        local.fallDistance = 0f;
	}

	@EventTarget
	public void preEntityUpdate(EventPreEntityUpdate event) {
        if (minecraft.world == null || minecraft.player == null) {
            return;
        }
        RayTraceResult block = minecraft.player.rayTrace(teleportDistance, 0);
        BlockPos pos = block.getBlockPos();

        if (minecraft.world.isAirBlock(pos)) {
            check = null;
            run = false;
            return;
        }
        double XBlk = pos.getX();
        double YBlk = pos.getY();
        double ZBlk = pos.getZ();
        check = new BlockPos(XBlk, YBlk, ZBlk);

        while (!minecraft.world.isAirBlock(check) || !minecraft.world.isAirBlock(prevCheck)) {
            YBlk++;
            check = new BlockPos(XBlk, YBlk, ZBlk);
            prevCheck = new BlockPos(XBlk, YBlk - 1, ZBlk);
        }

        if (run) {
            double y = YBlk - minecraft.player.posY - 1;
            double x = XBlk - minecraft.player.posX;
            double z = ZBlk - minecraft.player.posZ;
            x = x + 0.5;
            z = z + 0.5;
            minecraft.player.setVelocity(x, y, z);
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
        if (run) {
            minecraft.player.motionY -= minecraft.player.motionY;
            minecraft.player.motionX -= minecraft.player.motionX;
            minecraft.player.motionZ -= minecraft.player.motionZ;
            run = false;
        }
	}

	@EventTarget
	public void render3D(EventRenderWorld event) {

    }

}

  


