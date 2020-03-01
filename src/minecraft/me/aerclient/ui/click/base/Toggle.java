package me.aerclient.ui.click.base;

import me.aerclient.config.valuesystem.BooleanValue;

public abstract class Toggle extends Element<BooleanValue> {

    int buttonCheck = -1;
    boolean toggled = false;

    /**
     *A basic click-to-toggle gui element which can either accept all clicks, or only a specific button click.
     */
    public Toggle(int xIn, int yIn, int widthIn, int heightIn, BooleanValue value, ModuleButton parent) {
        super(xIn, yIn, widthIn, heightIn, value, parent);
    }

    public Toggle(int xIn, int yIn, int widthIn, int heightIn, BooleanValue value, ModuleButton parent, int buttonCheckIn) {
        super(xIn, yIn, widthIn, heightIn, value, parent);
        this.buttonCheck = buttonCheckIn;
    }

    @Override
    public void onClicked(int button) {
        if(buttonCheck != -1){
            if(button == buttonCheck){
                value.setObject(!value.getObject());
            }
        }
        else{
            value.setObject(!value.getObject());
        }
        this.toggled = value.getObject();
    }

    public boolean isToggled(){
        return toggled;
    }

    public void setToggled(boolean toggle){
        toggled = toggle;
    }

    @Override
    public void init() { }

    @Override
    public abstract void render(int mouseX, int mouseY);

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) { }

    @Override
    public void keyPressed(int key) { }
}
