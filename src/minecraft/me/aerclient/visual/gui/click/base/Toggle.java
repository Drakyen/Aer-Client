package me.aerclient.visual.gui.click.base;

import me.aerclient.config.valuesystem.BooleanValue;

public abstract class Toggle extends Element<BooleanValue> {

    int buttonCheck = -1;
    boolean toggled = false;

    /**
     *A basic click-to-toggle gui element which can either accept all clicks, or only a specific button click.
     */
    public Toggle(int xIn, int yIn, int widthIn, int heightIn, BooleanValue value, ModuleButton parent) {
        this(xIn, yIn, widthIn, heightIn, value, parent, -1);
    }

    public Toggle(int xIn, int yIn, int widthIn, int heightIn, BooleanValue value, ModuleButton parent, int buttonCheckIn) {
        super(xIn, yIn, widthIn, heightIn, value, parent);
        this.buttonCheck = buttonCheckIn;
        this.toggled = value.getObject();
    }

    @Override
    public void onClicked(int button, int mouseX, int mouseY) {
        if (parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            if (buttonCheck != -1) {
                if (button == buttonCheck) {
                    value.setObject(!value.getObject());
                }
            } else {
                value.setObject(!value.getObject());
            }
            this.toggled = value.getObject();
        }
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggle) {
        toggled = toggle;
    }

    @Override
    public void init() {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }

    @Override
    public void keyPressed(int key) {

    }

}
