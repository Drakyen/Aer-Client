package me.aerclient.module.modules.render;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.injection.events.render.EventRenderBlock;
import me.aerclient.injection.events.world.EventWorldLoaded;
import me.aerclient.module.base.Category;
import me.aerclient.module.base.Module;
import me.aerclient.config.valuesystem.BlockArrayValue;
import me.aerclient.config.valuesystem.ModeValue;
import me.aerclient.config.valuesystem.NumberValue;
import me.aerclient.utils.world.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;

import java.util.ArrayList;

public class XRay extends Module {

    private NumberValue Opacity = new NumberValue("Opacity", 0.4f, 0f, 1f, true);
    private ModeValue presets = new ModeValue("Presets", "Ores", new String[]{"Custom", "Ores", "Valuables", "Useful", "Redstone"});
    public BlockArrayValue custom = new BlockArrayValue("Custom Blocks", new ArrayList<Block>());
    private BlockArrayValue valuables;
    private BlockArrayValue useful;
    private BlockArrayValue redstone;
    private String current = presets.getValue();
    int occlusion = 1;


    public XRay() {
        super("XRay", Category.RENDER, "Allows you to see through the ground");
    }

    public void setup() {
        ArrayList<Block> temp = new ArrayList<Block>();

        temp.add(Block.getBlockById(41));
		temp.add(Block.getBlockById(57));
		temp.add(Block.getBlockById(56));
		temp.add(Block.getBlockById(133));
		temp.add(Block.getBlockById(129));
		temp.add(Block.getBlockById(42));
		temp.add(Block.getBlockById(122));
		temp.add(Block.getBlockById(116));
		temp.add(Block.getBlockById(22));
		temp.add(Block.getBlockById(138));

		valuables = new BlockArrayValue("Valuables", new ArrayList<Block>(temp));

		temp.clear();

		temp.add(Block.getBlockById(49));
		temp.add(Block.getBlockById(46));
		temp.add(Block.getBlockById(90));
		temp.add(Block.getBlockById(119));
		temp.add(Block.getBlockById(120));
		temp.add(Block.getBlockById(209));
		temp.add(Block.getBlockById(145));

		useful = new BlockArrayValue("Useful", new ArrayList<Block>(temp));

		temp.clear();

		temp.add(Block.getBlockById(55));
		temp.add(Block.getBlockById(73));
		temp.add(Block.getBlockById(74));
		temp.add(Block.getBlockById(75));
		temp.add(Block.getBlockById(76));
		temp.add(Block.getBlockById(93));
		temp.add(Block.getBlockById(94));
		temp.add(Block.getBlockById(123));
		temp.add(Block.getBlockById(124));
		temp.add(Block.getBlockById(149));
		temp.add(Block.getBlockById(150));
		temp.add(Block.getBlockById(152));
		temp.add(Block.getBlockById(29));
		temp.add(Block.getBlockById(33));
		temp.add(Block.getBlockById(34));
		temp.add(Block.getBlockById(23));
		temp.add(Block.getBlockById(25));
		temp.add(Block.getBlockById(69));
		temp.add(Block.getBlockById(70));
		temp.add(Block.getBlockById(72));
		temp.add(Block.getBlockById(77));
		temp.add(Block.getBlockById(131));
		temp.add(Block.getBlockById(143));
		temp.add(Block.getBlockById(146));
		temp.add(Block.getBlockById(147));
		temp.add(Block.getBlockById(148));
		temp.add(Block.getBlockById(151));
		temp.add(Block.getBlockById(154));
		temp.add(Block.getBlockById(158));
		temp.add(Block.getBlockById(218));

		redstone = new BlockArrayValue("Redstone", new ArrayList<Block>(temp));

		temp.clear();

	}


	public void onValueUpdate() {
		if (current != presets.getValue()) {
			current = presets.getValue();
            minecraft.world.markBlockRangeForRenderUpdate((int) minecraft.player.posX - 128, (int) minecraft.player.posY - 128, (int) minecraft.player.posZ - 128, (int) minecraft.player.posX + 128, (int) minecraft.player.posY + 128, (int) minecraft.player.posZ + 128);
        }
    }

    public void onEnable() {
        occlusion = minecraft.gameSettings.ambientOcclusion;
        minecraft.gameSettings.ambientOcclusion = 1;
        WorldUtils.setBlocksTransparent(true, true);
        WorldUtils.setCulling(false, true);
        minecraft.world.markBlockRangeForRenderUpdate((int) minecraft.player.posX - 128, (int) minecraft.player.posY - 128, (int) minecraft.player.posZ - 128, (int) minecraft.player.posX + 128, (int) minecraft.player.posY + 128, (int) minecraft.player.posZ + 128);

    }

    public void onDisable() {
        minecraft.gameSettings.ambientOcclusion = occlusion;
        WorldUtils.setBlocksTransparent(false, true);
        WorldUtils.setCulling(true, true);
        minecraft.world.markBlockRangeForRenderUpdate((int) minecraft.player.posX - 128, (int) minecraft.player.posY - 128, (int) minecraft.player.posZ - 128, (int) minecraft.player.posX + 128, (int) minecraft.player.posY + 128, (int) minecraft.player.posZ + 128);

    }

    @EventTarget
    public void onWorldLoad(EventWorldLoaded event) {
        if (this.active) {
            this.onEnable();
        }
    }


	@EventTarget
	public void renderBlock(EventRenderBlock event) {
        if (xrayBlock(event.block)) {
            event.checkSides = false;
            return;
        }
        event.blockOpacity = Opacity.getValue().floatValue();
    }


	@Override
	public void toggle() {
		this.setActiveState(!isActive());
		if (this.isActive() == true) {
			EventManager.register(this);
			this.onEnable();
		} else if (this.isActive() == false) {
			this.onDisable();
		}
	}


	private boolean xrayBlock(Block block) {
		if (presets.getValue().equalsIgnoreCase("Custom")) {
			return custom.getObject().contains(block);
		} else if (presets.getValue().equalsIgnoreCase("Ores")) {
			return block instanceof BlockOre ? true : false;
		} else if (presets.getValue().equalsIgnoreCase("Valuables")) {
			return valuables.getObject().contains(block);
		} else if (presets.getValue().equalsIgnoreCase("Useful")) {
			return useful.getObject().contains(block);
		} else if (presets.getValue().equalsIgnoreCase("Redstone")) {
			return redstone.getObject().contains(block);
		}
		return false;
	}

}
