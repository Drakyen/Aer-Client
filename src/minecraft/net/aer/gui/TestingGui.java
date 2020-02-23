package net.aer.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import static net.aer.Aer.minecraft;

public class TestingGui extends GuiScreen {


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        minecraft.getTextureManager().bindTexture(new ResourceLocation("aerassets/logo.png"));
        Gui.drawModalRectWithCustomSizedTexture(10, 10, 0.0F, 0.0F, 600, 600, 600, 600);

    }



}
