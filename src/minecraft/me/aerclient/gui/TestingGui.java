package me.aerclient.gui;

import me.aerclient.render.feather.animate.Animation;
import me.aerclient.ui.click.base.Panel;
import me.aerclient.style.click.ModernPanel;
import me.aerclient.style.click.ModernToggle;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class TestingGui extends GuiScreen {

    private Panel panel;

    public TestingGui(){
        panel = new ModernPanel(10, 10, 90, 15, "Panel!", Animation.Transition.CURVE, 500);
        for(int i = 0; i < 5; i++) {
            panel.add(new ModernToggle(0, 0, 90, 15, null, null));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        panel.render(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        panel.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        panel.mouseReleased(mouseX, mouseY, state);
    }
}
