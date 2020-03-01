package me.aerclient.module.modules;


import me.aerclient.Aer;
import me.aerclient.gui.TestingGui;
import me.aerclient.module.base.Category;
import me.aerclient.module.base.Module;

public class Dummy extends Module {

    public Dummy() {
        super("Testing", Category.UTIL);
    }

    public void onEnable(){
        minecraft.displayGuiScreen(new TestingGui());
        toggle();
    }

}
