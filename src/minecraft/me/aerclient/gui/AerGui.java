package me.aerclient.gui;

import me.aerclient.render.render2D.RenderUtils2D;
import me.aerclient.render.render2D.font.Fonts;
import me.aerclient.utils.Utilities;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class AerGui extends GuiScreen implements Utilities  {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledRes = new ScaledResolution(minecraft);
        minecraft.getTextureManager().bindTexture(new ResourceLocation("aerassets/background.png"));
        drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), scaledRes.getScaledHeight());
        RenderUtils2D.drawCenteredString(Fonts.massive, "Aer Client", scaledRes.getScaledWidth() / 2, scaledRes.getScaledHeight() / 2, 0xaaffffff, true);
    }


}
