package net.aer.gui;

import net.aer.render.render2D.RenderUtils2D;
import net.aer.render.render2D.font.Fonts;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import static net.aer.Aer.minecraft;

public class AerGui extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledRes = new ScaledResolution(minecraft);
        minecraft.getTextureManager().bindTexture(new ResourceLocation("aerassets/background.png"));
        drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), scaledRes.getScaledHeight());
        RenderUtils2D.drawCenteredString(Fonts.massive, "Aer Client", scaledRes.getScaledWidth() / 2, scaledRes.getScaledHeight() / 2, 0xaaffffff, true);
    }


}
