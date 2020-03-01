package me.aerclient.ui.click.base;

import me.aerclient.module.base.Module;
import me.aerclient.ui.base.container.ClickableGuiContainer;

public class ModuleButton extends ClickableGuiContainer {

    private Panel parent;
    private Module module;


    private boolean extended;

    public ModuleButton(int xIn, int yIn, int widthIn, int heightIn, Module moduleIn, Panel parentIn) {
        super(xIn, yIn, widthIn, heightIn);
        module = moduleIn;
        parent = parentIn;
    }

    @Override
    public void onClicked(int button) {

    }

    public Panel getParent(){
        return parent;
    }

    public boolean isExtended() {
        return extended;
    }

    public String getName(){
        return module.getName();
    }

    public void updateCols(){}
}
