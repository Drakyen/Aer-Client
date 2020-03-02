package me.aerclient.implementation.command.commands;


import me.aerclient.implementation.command.Command;
import me.aerclient.implementation.module.base.ModuleManager;
import me.aerclient.implementation.module.modules.render.XRay;
import me.aerclient.visual.render.render2D.ChatColour;
import me.aerclient.visual.render.render2D.ChatUtils;
import net.minecraft.block.Block;

public class XRayBlocks extends Command {

	public XRayBlocks() {
		super("XRayblocks", "Edits custom XRay blocks", "XRayblocks [LIST/ADD/REMOVE] [BLOCK ID/NAME]");
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {


		if (args[0].equalsIgnoreCase("ADD")) {
			Block b;
			try {
				XRay x = (XRay) ModuleManager.getModuleByName("XRay");
				if (Integer.parseInt(args[1]) > 255) {
					ChatUtils.sendChatMessage("Unable to parse block: Block ID out of range", ChatColour.lightPurple, "XRay", true);
					return;
				}
				x.custom.getObject().add(
						Block.getBlockById(Integer.parseInt(args[1]))
				);
				x.onValueUpdate();
				b = Block.getBlockById(Integer.parseInt(args[1]));
			} catch (Exception e) {
				XRay x = (XRay) ModuleManager.getModuleByName("XRay");
				if (Block.getBlockFromName(args[1]) == null) {
					ChatUtils.sendChatMessage("Unable to parse block: Block name invalid", ChatColour.lightPurple, "XRay", true);
					return;
				}
				x.custom.getObject().add(
						Block.getBlockFromName(args[1])
				);
				x.onValueUpdate();
				b = Block.getBlockFromName(args[1]);
			}
			ChatUtils.sendChatMessage("Added block " + b.getLocalizedName(), ChatColour.lightPurple, "XRay", true);
		} else if (args[0].equalsIgnoreCase("REMOVE")) {
			Block b;
			try {
				XRay x = (XRay) ModuleManager.getModuleByName("XRay");
				if (Integer.parseInt(args[1]) > 255) {
					ChatUtils.sendChatMessage("Unable to parse block: Block ID out of range", ChatColour.lightPurple, "XRay", true);
					return;
				}
				x.custom.getObject().remove(
						Block.getBlockById(Integer.parseInt(args[1]))
				);
				x.onValueUpdate();
				b = Block.getBlockById(Integer.parseInt(args[1]));
			} catch (Exception e) {
				XRay x = (XRay) ModuleManager.getModuleByName("XRay");
				if (Block.getBlockFromName(args[1]) == null) {
					ChatUtils.sendChatMessage("Unable to parse block: Block name invalid", ChatColour.lightPurple, "XRay", true);
					return;
				}
				x.custom.getObject().remove(
						Block.getBlockFromName(args[1])
				);
				x.onValueUpdate();
				b = Block.getBlockFromName(args[1]);
			}
			ChatUtils.sendChatMessage("Removed block " + b.getLocalizedName(), ChatColour.lightPurple, "XRay", true);
		} else if (args[0].equalsIgnoreCase("List")) {
			try {
				XRay x = (XRay) ModuleManager.getModuleByName("XRay");
				ChatUtils.clearChat(false, "XRay");
				ChatUtils.sendChatMessage("Blocks:", ChatColour.lightPurple, "XRay", true);
				for (Block s : x.custom.getObject()) {
					ChatUtils.sendChatMessage("Name: " + s.getLocalizedName() + "; Id: " + s.getIdFromBlock(s), ChatColour.gray, "XRay", false);
				}


			} catch (Exception e) {
			}
		} else if (args[0].isEmpty()) {
			ChatUtils.sendChatMessage(this.getSyntax(), ChatColour.lightPurple, "XRay", true);
		} else {
			throw new Exception();
		}

		XRay x = (XRay) ModuleManager.getModuleByName("XRay");
		minecraft.world.markBlockRangeForRenderUpdate(-128, -128, -128, 128, 128, 128);

	}
}
