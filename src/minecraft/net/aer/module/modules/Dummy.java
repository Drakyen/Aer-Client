package net.aer.module.modules;


import net.aer.Aer;
import net.aer.gui.TestingGui;
import net.aer.module.base.Category;
import net.aer.module.base.Module;

public class Dummy extends Module {

    public Dummy() {
        super("Testing", Category.UTIL);
    }

    public void onEnable(){
        Aer.minecraft.displayGuiScreen(new TestingGui());
        toggle();
    }

}
