package me.aer.implementation.module.modules;


import me.aer.config.valuesystem.ColourValue;
import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;

import java.awt.*;

public class Dummy extends Module {

    private ColourValue col = new ColourValue("Test", "Test", new Color(0xff000000));

    public Dummy() {
        super("Dummy", Category.UTIL);
    }


}
