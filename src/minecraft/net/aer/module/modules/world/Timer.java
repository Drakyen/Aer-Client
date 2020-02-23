package net.aer.module.modules.world;

import net.aer.module.Category;
import net.aer.module.Module;
import net.aer.utils.valuesystem.NumberValue;

public class Timer extends Module {

    private NumberValue<Float> timer = new NumberValue<>("Timer", 40.0F, 20.0F, 200.0F);

    public Timer() {
        super("Timer", Category.WORLD, "Changes client tick speed.");
    }

    @Override
    public void onEnable(){
        minecraft.timer = new net.minecraft.util.Timer(timer.getValue());
    }

    @Override
    public void onDisable(){
        minecraft.timer = new net.minecraft.util.Timer(20.0F);
    }

}
