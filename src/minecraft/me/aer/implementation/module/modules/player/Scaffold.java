package me.aer.implementation.module.modules.player;

import com.darkmagician6.eventapi.EventTarget;
import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;
import me.aer.implementation.utils.player.BlockUtil;
import me.aer.injection.events.world.EventPostUpdate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class Scaffold extends Module {
    public Scaffold() {
        super("Scaffold", Category.PLAYER, "Builds under you.");
    }

    public void onEnable() {
    }

    @EventTarget
    public void eventPostUpdate(EventPostUpdate event) {
        BlockPos belowPlayer = new BlockPos(minecraft.player).down();

        IBlockState state = minecraft.world.getBlockState(belowPlayer);
        if (!state.getBlock().isReplaceable(minecraft.world, belowPlayer)) {
            return;
        }

        int newSlot = -1;
        for (int i = 0; i < 9; i++) {
            //filter non blocks
            ItemStack stack = minecraft.player.inventory.getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            // filter for full blocks and gravity blocks
            Block block = Block.getBlockFromItem(stack.getItem());
            if (!block.getDefaultState().isFullBlock() && !(block instanceof BlockPistonBase) && !(block instanceof BlockFalling)) {
                continue;
            }

            newSlot = i;
            break;
        }

        if (newSlot == -1) {
            return;
        }

        // set slot
        int oldSlot = minecraft.player.inventory.currentItem;
        minecraft.player.inventory.currentItem = newSlot;

        // place block
        BlockUtil.placeBlockScaffold(belowPlayer);

        // reset slot
        minecraft.player.inventory.currentItem = oldSlot;

    }
}
