package net.aer.module.modules.render;

import com.darkmagician6.eventapi.EventTarget;
import net.aer.events.world.EventPreTick;
import net.aer.module.base.Category;
import net.aer.module.base.Module;
import net.minecraft.client.settings.GameSettings;

public class Brightness extends Module implements Runnable {

    public static boolean state;
    private Thread thread;
    private boolean start;

    public Brightness() {
        super("Brightness", Category.RENDER, "Makes the world bright!");
    }

    public void onEnable() {
        Brightness.state = true;
        new Thread(new Brightness()).start();
    }

    public void onDisable() {
        Brightness.state = false;
        new Thread(new Brightness()).start();
    }

    @EventTarget
    public void eventPreTick(EventPreTick event) {
        if (minecraft.player == null || minecraft.world == null) {
            return;
        }
        if (start) {
            start = false;
            this.onEnable();
        }
    }

    public void setup() {
        start = true;
    }

    public void onExit() {
        minecraft.gameSettings.gammaSetting = 1f;
    }

    @Override
    public void run() {
        while (minecraft.gameSettings.gammaSetting < 3.0f && state == true) {
            final GameSettings gameSettings = minecraft.gameSettings;
            gameSettings.gammaSetting += 0.003f;
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (minecraft.gameSettings.gammaSetting < 12.0f && state == true) {
            final GameSettings gameSettings = minecraft.gameSettings;
            gameSettings.gammaSetting += 0.01f;
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (minecraft.gameSettings.gammaSetting > 3.0f && state == false) {
            final GameSettings gameSettings2 = minecraft.gameSettings;
            gameSettings2.gammaSetting -= 0.01f;
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (minecraft.gameSettings.gammaSetting > 1f && state == false) {
            final GameSettings gameSettings2 = minecraft.gameSettings;
            gameSettings2.gammaSetting -= 0.003f;
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
