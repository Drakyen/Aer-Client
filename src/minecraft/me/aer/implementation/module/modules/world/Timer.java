package me.aer.implementation.module.modules.world;

import com.darkmagician6.eventapi.EventTarget;
import me.aer.config.valuesystem.NumberValue;
import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;
import me.aer.implementation.utils.world.TimerUtil;
import me.aer.injection.events.client.EventValueChanged;

public class Timer extends Module {

    private NumberValue<Float> speed = new NumberValue<>("Speed", "Multiplier for the client side tick speed", 1.0F, 1F, 10.0F, false);
    private TimerUtil timer = new TimerUtil();

    public Timer() {
        super("Timer", Category.WORLD, "Changes client tick speed [TPS]");
    }


    @Override
    public void onEnable() {
        minecraft.timer = new net.minecraft.util.Timer(speed.getValue() * 20);
    }

    @EventTarget
    public void onValueUpdate(EventValueChanged event) {
        if(timer.delay(150)) {
            minecraft.timer = new net.minecraft.util.Timer(speed.getValue() * 20);
            timer.reset();
        }
    }

    @Override
    public void onDisable(){
        minecraft.timer = new net.minecraft.util.Timer(20.0F);
    }

}
