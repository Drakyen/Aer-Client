package me.aer.visual.style.minimal.click;

import me.aer.implementation.module.base.Module;
import me.aer.visual.gui.click.base.ModuleButton;
import me.aer.visual.gui.click.base.Panel;
import me.aer.visual.render.Fonts;
import me.aer.visual.render.RenderUtils;
import me.aer.visual.render.feather.animate.Animation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class MinimalModuleButton extends ModuleButton {

    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    public MinimalModuleButton(int xIn, int yIn, int widthIn, int heightIn, boolean extendedIn, Module moduleIn, Panel parentIn, Animation.Transition type, int animationDuration) {
        super(xIn, yIn, widthIn, heightIn, extendedIn, moduleIn, parentIn, type, animationDuration);
    }

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(15, 15, 15, newCol.getAlpha());
        foregroundCol = new Color(28, 28, 28, newCol.getAlpha());
    }

    @Override
    public void renderModuleButton(int mouseX, int mouseY) {
        rend.drawRect(x, y, x + width, y + height, foregroundCol.getRGB());

        GL11.glPushMatrix();
        GL11.glTranslated(x + 6, y + height / 2, 0);
        GL11.glRotated(animation.get(90, 0), 0, 0, 1);
        rend.setColor(col.getRGB());
        double percent = animation.get(0, 1);
        double percent2 = animation.get(1, 0);
        RenderUtils.tess.vertex((float) (-3 + (1 * percent2)), (float) (-3 - (2 * percent2)), 0)
                .vertex((float) (-3 + percent2), (float) (4 + percent2), 0)
                .vertex((float) (4 - percent2), (float) (-3 + (3 * percent2)), 0).end(GL11.GL_TRIANGLES);
        GL11.glPopMatrix();

        if (getModule().isActive()) {
            rend.drawRect(x + width - 2, y + 1, x + width, y + height, 0xff0f6e00);
        } else {
            rend.drawRect(x + width - 2, y + 1, x + width, y + height, 0xff5c2020);
        }
        if (hovered(mouseX, mouseY)) {
            rend.drawString(Fonts.normal, getName(), x + 12, y + 5, 0xffffffff, true);
        } else {
            rend.drawString(Fonts.normal, getName(), x + 12, y + 5, 0xffcccccc, true);
        }


    }
}
