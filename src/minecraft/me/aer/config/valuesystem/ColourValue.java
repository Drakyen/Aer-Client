package me.aer.config.valuesystem;

import java.awt.*;
import java.util.Properties;

public class ColourValue extends Value<Color> {

    public ColourValue(String name, String description, Color defaultVal) {
        super(name, description, defaultVal);
    }

    @Override
    public void addToProperties(Properties props) {
        props.setProperty(this.getName() + "R", "" + this.getObject().getRed());
        props.setProperty(this.getName() + "G", "" + this.getObject().getGreen());
        props.setProperty(this.getName() + "B", "" + this.getObject().getBlue());
        props.setProperty(this.getName() + "A", "" + this.getObject().getAlpha());

    }

    @Override
    public void fromProperties(Properties props) {
        int r = Integer.parseInt(props.getProperty(this.getName() + "R"));
        int g = Integer.parseInt(props.getProperty(this.getName() + "G"));
        int b = Integer.parseInt(props.getProperty(this.getName() + "B"));
        int a = Integer.parseInt(props.getProperty(this.getName() + "A"));
        Color col = new Color(r, g, b, a);
        this.setObject(col);
    }
}
