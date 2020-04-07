package me.aer.visual.style.minimal.click;

import me.aer.visual.gui.click.base.Keybinding;
import me.aer.visual.gui.click.base.ModuleButton;
import me.aer.visual.render.Fonts;
import me.aer.visual.render.RenderUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class MinimalKeybinding extends Keybinding {
    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    public MinimalKeybinding(int xIn, int yIn, int widthIn, int heightIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, parentIn);
    }

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(15, 15, 15, newCol.getAlpha());
        foregroundCol = new Color(28, 28, 28, newCol.getAlpha());
    }

    @Override
    public void renderElement(int mouseX, int mouseY) {
        rend.drawRect(x, y, x + width, y + height, foregroundCol.getRGB());
        rend.drawRect(x + 5, y, x + 7, y + height, col.getRGB());

        GL11.glPushMatrix();
        GL11.glTranslated(x + 6, y + height - 2, 0);
        RenderUtils.tess.vertex(-3, 2, 0)
                .vertex(3, 2, 0)
                .vertex(0, -1, 0).end(GL11.GL_TRIANGLES);
        GL11.glPopMatrix();

        if (hovered(mouseX, mouseY)) {
            if (isListening()) {
                rend.drawString(Fonts.normal, "Listening..", x + 15, y + 4, 0xffffffff, false);
            } else {
                rend.drawString(Fonts.normal, "Keybind \u00A7p[\u00A7r" + (parent.getModule().getKeybind() != Keyboard.KEY_ESCAPE ? Keyboard.getKeyName(parent.getModule().getKeybind()) : "NONE") + "\u00A7p]", x + 15, y + 4, 0xffffffff, false);
            }
        } else {
            if (isListening()) {
                rend.drawString(Fonts.normal, "Listening..", x + 15, y + 4, 0xffffffff, false);
            } else {
                rend.drawString(Fonts.normal, "Keybind \u00A7p[\u00A7r" + (parent.getModule().getKeybind() != Keyboard.KEY_ESCAPE ? Keyboard.getKeyName(parent.getModule().getKeybind()) : "NONE") + "\u00A7p]", x + 15, y + 4, 0xffaaaaaa, false);
            }
        }
    }

}
