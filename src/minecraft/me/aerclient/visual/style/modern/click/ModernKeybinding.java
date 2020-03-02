package me.aerclient.visual.style.modern.click;

import me.aerclient.visual.render.render2D.font.Fonts;
import me.aerclient.visual.gui.click.base.Keybinding;
import me.aerclient.visual.gui.click.base.ModuleButton;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ModernKeybinding extends Keybinding {
    public ModernKeybinding(int xIn, int yIn, int widthIn, int heightIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, parentIn);
    }

    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(1, 1, 1, newCol.getAlpha());
        foregroundCol = new Color(70, 70, 70, newCol.getAlpha());
    }

    @Override
    public void init() {

    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        rend2D.drawRect(x, y, x + width, y + height, backgroundCol.getRGB());

        if (hovered(mouseX, mouseY) && parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            rend2D.drawRect(x + 2, y + 1, x + width - 2, y + height - 1, foregroundCol.brighter().getRGB());
        }

        if (isListening()) {
            rend2D.drawString(Fonts.normal, "Listening..", x + 5, y + 4, 0xffffffff, false);
        } else {
            rend2D.drawString(Fonts.normal, "Keybind \u00A7p[\u00A7r" + (parent.getModule().getKeybind() != Keyboard.KEY_ESCAPE ? Keyboard.getKeyName(parent.getModule().getKeybind()) : "NONE") + "\u00A7p]", x + 5, y + 4, 0xffffffff, false);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }
}
