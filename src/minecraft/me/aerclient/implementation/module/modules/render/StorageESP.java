package me.aerclient.implementation.module.modules.render;


import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.config.valuesystem.BooleanValue;
import me.aerclient.config.valuesystem.NumberValue;
import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.module.base.Module;
import me.aerclient.injection.events.render.EventRenderWorld;
import me.aerclient.visual.render.render3D.RenderUtils3D;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class StorageESP extends Module {


    private BooleanValue ShowShulkers = new BooleanValue("Shulkers", "Whether to show shulker boxes", true);
    private BooleanValue ShowChests = new BooleanValue("Chests", "Whether to show chests", true);
    private BooleanValue ShowEChests = new BooleanValue("EChests", "Whether to show ender chests", true);
    private BooleanValue ShowContainers = new BooleanValue("Containers", "Whether to show other blocks that can have items in them", false);
    private BooleanValue ShowEntityChests = new BooleanValue("EntityChests", "Whether to show chests on donkeys & llamas", false);
    private BooleanValue Tracers = new BooleanValue("Tracers", "Whether to draw tracers to found storage blocks", false);
    private NumberValue<Float> alpha = new NumberValue<>("Alpha", "Alpha/Transparency of the outlines & tracers", 0.4f, 0f, 1f, true);

    private boolean viewBobbing;


    public StorageESP() {
        super("StorageESP", Category.RENDER, "Outlines storages");
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
	}

	@EventTarget
	public void onDraw3D(EventRenderWorld event) {
		if (minecraft.world.loadedTileEntityList == null || minecraft.world.loadedEntityList == null) {
			return;
		}
		for (TileEntity t : minecraft.world.loadedTileEntityList) {
			if (ShowChests.getObject()) {
				if (t.getBlockType() instanceof BlockChest) {
					BlockChest t1 = (BlockChest) t.getBlockType();
					if (t1.isDoubleChest(minecraft.world, t.getPos())) {
						if (t1.chestType == BlockChest.Type.TRAP) {
                            RenderUtils3D.drawESPBox(t1.doubleChestBox(minecraft.world, t.getPos()), 0.9f, 0.3f, 0.1f, alpha.getValue(), 1.0f, 0.3f, 0.2f, alpha.getValue().floatValue(), 2f);
                        } else {
                            RenderUtils3D.drawESPBox(t1.doubleChestBox(minecraft.world, t.getPos()), 0.7f, 0.6f, 0.3f, alpha.getValue(), 0.8f, 0.6f, 0.4f, alpha.getValue().floatValue(), 2f);
                        }
					} else {
						if (t1.chestType == BlockChest.Type.TRAP) {
							RenderUtils3D.drawESPBox(
                                    new AxisAlignedBB(t.getPos().getX() + 0.05f, t.getPos().getY(), t.getPos().getZ() + 0.05f,
                                            t.getPos().getX() + 0.95f, t.getPos().getY() + 0.9, t.getPos().getZ() + 0.95f), 0.9f, 0.3f, 0.1f, alpha.getValue(),
                                    1.0f, 0.3f, 0.2f, alpha.getValue(), 2f
                            );
						} else {
							RenderUtils3D.drawESPBox(
                                    new AxisAlignedBB(t.getPos().getX() + 0.05f, t.getPos().getY(), t.getPos().getZ() + 0.05f,
                                            t.getPos().getX() + 0.95f, t.getPos().getY() + 0.9, t.getPos().getZ() + 0.95f), 0.7f, 0.6f, 0.3f, alpha.getValue(),
                                    0.8f, 0.6f, 0.4f, alpha.getValue(), 2f
                            );
						}
					}
					if (Tracers.getObject()) {
						if (t1.chestType == BlockChest.Type.TRAP) {
                            RenderUtils3D.drawTracerLine(t.getPos(), 0.9f, 0.3f, 0.1f, alpha.getValue(), 2f);
                        } else {
                            RenderUtils3D.drawTracerLine(t.getPos(), 0.7f, 0.6f, 0.3f, alpha.getValue(), 2f);
                        }
					}
				}
			}
			if (ShowShulkers.getObject()) {
				if (t.getBlockType() instanceof BlockShulkerBox) {
					RenderUtils3D.drawESPBox(
                            new AxisAlignedBB(t.getPos().getX(), t.getPos().getY(), t.getPos().getZ(),
                                    t.getPos().getX() + 1f, t.getPos().getY() + 1f, t.getPos().getZ() + 1f), 0.6f, 0.3f, 1f, alpha.getValue().floatValue(),
                            0.6f, 0.5f, 1f, alpha.getValue(), 2f
                    );
					if (Tracers.getObject()) {
                        RenderUtils3D.drawTracerLine(t.getPos(), 0.6f, 0.3f, 1f, alpha.getValue(), 2f);
                    }
				}
			}
			if (ShowEChests.getObject()) {
				if (t.getBlockType() instanceof BlockEnderChest) {
					RenderUtils3D.drawESPBox(
                            new AxisAlignedBB(t.getPos().getX() + 0.05f, t.getPos().getY(), t.getPos().getZ() + 0.05f,
                                    t.getPos().getX() + 0.95f, t.getPos().getY() + 0.9, t.getPos().getZ() + 0.95f), 1f, 0.2f, 0.6f, alpha.getValue().floatValue(),
                            1f, 0.2f, 0.6f, alpha.getValue(), 2f
                    );
					if (Tracers.getObject()) {
                        RenderUtils3D.drawTracerLine(t.getPos(), 1f, 0.2f, 0.6f, alpha.getValue(), 2f);
                    }
				}
			}
			if (ShowContainers.getObject()) {
				if (t.getBlockType() instanceof BlockContainer && !(t.getBlockType() instanceof BlockChest) &&
						!(t.getBlockType() instanceof BlockShulkerBox) && !(t.getBlockType() instanceof BlockMobSpawner)
						&& !(t.getBlockType() instanceof BlockEnderChest)) {
					RenderUtils3D.drawESPBox(
                            new AxisAlignedBB(t.getPos().getX(), t.getPos().getY(), t.getPos().getZ(),
                                    t.getPos().getX() + 1f, t.getPos().getY() + 1f, t.getPos().getZ() + 1f), 0.6f, 0.6f, 0.6f, alpha.getValue().floatValue(),
                            0.6f, 0.6f, 0.6f, alpha.getValue(), 2f
                    );
					if (Tracers.getObject()) {
                        RenderUtils3D.drawTracerLine(t.getPos(), 0.6f, 0.6f, 0.6f, alpha.getValue(), 2f);
                    }
				}
			}
		}
		for (Entity e : minecraft.world.loadedEntityList) {
			if (ShowEntityChests.getObject()) {
				if (e instanceof EntityLlama) {
					if (((EntityLlama) e).hasChest()) {
						RenderUtils3D.drawESPBox(
                                new AxisAlignedBB(e.posX - 0.1f, e.posY + 0.7f, e.posZ - 0.1f,
                                        e.posX + 0.1f, e.posY + 1.15, e.posZ + 0.1f), 0.5f, 1f, 0.4f, alpha.getValue(),
                                0.6f, 1f, 0.5f, alpha.getValue(), 2f
                        );
						if (Tracers.getObject()) {
                            RenderUtils3D.drawTracerLine(e, 0.5f, 1f, 0.4f, alpha.getValue(), 2f);
                        }
					}
				}
				if (e instanceof EntityDonkey) {
					if (((EntityDonkey) e).hasChest()) {
						RenderUtils3D.drawESPBox(
                                new AxisAlignedBB(e.posX - 0.1f, e.posY + 0.7f, e.posZ - 0.1f,
                                        e.posX + 0.1f, e.posY + 1.15, e.posZ + 0.1f), 0.5f, 0.6f, 0.3f, alpha.getValue(),
                                0.6f, 0.6f, 0.4f, alpha.getValue(), 2f
                        );
						if (Tracers.getObject()) {
                            RenderUtils3D.drawTracerLine(e, 0.5f, 1f, 0.4f, alpha.getValue(), 2f);
                        }
					}
				}
				if (e instanceof EntityMinecart) {
					if (((EntityMinecart) e).hasStorage() != 0) {
						if (((EntityMinecart) e).hasStorage() == 1) {
							RenderUtils3D.drawESPBox(
                                    new AxisAlignedBB(e.posX - 0.4f, e.posY + 0.2f, e.posZ - 0.4f,
                                            e.posX + 0.4f, e.posY + 1f, e.posZ + 0.4f), 0.5f, 0.6f, 0.3f, alpha.getValue(),
                                    0.6f, 0.6f, 0.4f, alpha.getValue(), 2f
                            );
							if (Tracers.getObject()) {
                                RenderUtils3D.drawTracerLine(e, 0.5f, 1f, 0.4f, alpha.getValue(), 2f);
                            }
						} else if (((EntityMinecart) e).hasStorage() == 2) {
							RenderUtils3D.drawESPBox(
                                    new AxisAlignedBB(e.posX - 0.4f, e.posY + 0.2f, e.posZ - 0.4f,
                                            e.posX + 0.4f, e.posY + 0.8f, e.posZ + 0.4f), 0.6f, 0.6f, 0.6f, alpha.getValue(),
                                    0.6f, 0.6f, 0.6f, alpha.getValue(), 2f
                            );
							if (Tracers.getObject()) {
                                RenderUtils3D.drawTracerLine(e, 0.5f, 1f, 0.4f, alpha.getValue(), 2f);
                            }
						}
					}
				}
			}
		}
	}
}
