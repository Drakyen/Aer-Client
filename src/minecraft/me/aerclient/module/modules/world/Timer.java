package me.aerclient.module.modules.world;

import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.injection.events.client.EventValueChanged;
import me.aerclient.module.base.Category;
import me.aerclient.module.base.Module;
import me.aerclient.config.valuesystem.NumberValue;

public class Timer extends Module {

    private NumberValue<Float> timer = new NumberValue<>("Speed", 1.0F, 1F, 10.0F, false);

    public Timer() {
        super("Timer", Category.WORLD, "Changes client tick speed (TPS)");
    }


    @Override
    public void onEnable(){
        minecraft.timer = new net.minecraft.util.Timer(timer.getValue()*20);
    }

    @EventTarget
    public void onValueUpdate(EventValueChanged event){
        minecraft.timer = new net.minecraft.util.Timer(timer.getValue()*20);
    }

    @Override
    public void onDisable(){
        minecraft.timer = new net.minecraft.util.Timer(20.0F);
    }

}
