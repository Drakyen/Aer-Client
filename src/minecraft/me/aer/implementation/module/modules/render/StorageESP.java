package me.aer.implementation.module.modules.render;


import com.darkmagician6.eventapi.EventTarget;
import me.aer.config.valuesystem.BooleanValue;
import me.aer.config.valuesystem.NumberValue;
import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;
import me.aer.injection.events.render.EventRenderWorld;

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
    public void onRender(EventRenderWorld event) {

    }

}
