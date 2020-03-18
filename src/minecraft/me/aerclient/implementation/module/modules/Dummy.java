package me.aerclient.implementation.module.modules;


import me.aerclient.Aer;
import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.module.base.Module;
import me.aerclient.visual.gui.click.ClickGuiUI;
import me.aerclient.visual.style.minimal.MinimalStyle;

public class Dummy extends Module {

    public Dummy() {
        super("Dummy", Category.UTIL);
    }

    @Override
    public void onEnable() {
        Aer.clickGui = new ClickGuiUI(new MinimalStyle("Testing"));
    }

}
