package me.aerclient.visual.style.minimal.click;

import me.aerclient.implementation.module.base.Category;
import me.aerclient.visual.gui.click.ClickGuiUI;
import me.aerclient.visual.gui.click.base.Panel;
import me.aerclient.visual.render.feather.animate.Animation;
import me.aerclient.visual.render.render2D.RenderUtils2D;
import me.aerclient.visual.render.render2D.font.Fonts;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class MinimalPanel extends Panel {

    public Color col;
    public Color backgroundCol;
    public Color foregroundCol;

    public MinimalPanel(int xIn, int yIn, int widthIn, int heightIn, boolean extendedIn, Animation.Transition type, int animationDuration, ClickGuiUI parent, Category catIn) {
        super(xIn, yIn, widthIn, heightIn, extendedIn, type, animationDuration, 0, parent, catIn);
    }

    @Override
    public void updateCols(Color newCol) {
        col = new Color(newCol.getRGB());
        backgroundCol = new Color(15, 15, 15, newCol.getAlpha());
        foregroundCol = new Color(28, 28, 28, newCol.getAlpha());
    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);

        rend2D.drawRect(x + width, y + 1, x + width + 1, getAbsoluteHeightOfChildren() + 3, backgroundCol.getRGB());
        rend2D.drawRect(x + 1, getAbsoluteHeightOfChildren() + 2, x + width + 1, getAbsoluteHeightOfChildren() + 3, backgroundCol.getRGB());
        rend2D.drawRect(x, y, x + width, y + height, 0xff0f0f0f);
        rend2D.drawCenteredString(Fonts.mid, getCat().name(), x + width / 2, y + height / 2, 0xffffffff, true);
        rend2D.drawRect(x, getAbsoluteHeightOfChildren(), x + width, getAbsoluteHeightOfChildren() + 2, backgroundCol.brighter().getRGB());

        GL11.glPushMatrix();
        GL11.glTranslated(x + width - 8, y + height / 2, 0);
        GL11.glRotated(animation.get(-90, 0), 0, 0, 1);
        rend2D.setColor(col.getRGB());
        double percent = animation.get(0, 1);
        double percent2 = animation.get(1, 0);
        RenderUtils2D.tess.vertex((float) (-4 + (1 * percent2)), (float) (-3 + (3 * percent2)), 0)
                .vertex((float) (3 - percent2), 4 + (float) (percent2), 0)
                .vertex((float) (3 - percent2), (float) (-3 - (2 * percent2)), 0).end(GL11.GL_TRIANGLES);
        GL11.glPopMatrix();


    }
}
