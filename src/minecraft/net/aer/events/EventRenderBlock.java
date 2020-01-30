package net.aer.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.block.Block;

public class EventRenderBlock extends EventCancellable {

	public boolean liquid;
	public Block block;
	public float blockOpacity = 1.0f;
	public boolean checkSides;

	public EventRenderBlock(boolean isLiquid, Block block, float blockOpacity, boolean checkSides) {
		this.liquid = isLiquid;
		this.block = block;
		this.blockOpacity = blockOpacity;
		this.checkSides = checkSides;
	}

}
