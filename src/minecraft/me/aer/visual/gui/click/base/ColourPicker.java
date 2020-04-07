package me.aer.visual.gui.click.base;

import me.aer.config.valuesystem.ColourValue;
import me.aer.visual.gui.base.EditableTextUI;
import me.aer.visual.render.Fonts;
import me.aer.visual.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.nio.ByteBuffer;

public abstract class ColourPicker extends Element<ColourValue> {

    protected EditableTextUI hexCode = new EditableTextUI(10, 10, 8, 0xffaaaaaa, 0xaaffffff, 0xffffffff, "NULL");
    private float areaWidth, areaHeight, areaX, areaY;
    private boolean colDragging = false;
    private boolean barDragging = false;
    private int colSelectorX, colSelectorY, selectorBarY, colSelectorOffsetX, colSelectorOffsetY, selectorBarOffset;
    private Color barCol = new Color(0xffff0000);
    private ByteBuffer RGB = ByteBuffer.allocateDirect(3);
    private boolean readCol = false;
    private boolean readBar = false;

    public ColourPicker(int xIn, int yIn, int widthIn, int heightIn, int areaWidthIn, int areaHeightIn, ColourValue valueIn, ModuleButton parentIn) {
        super(xIn, yIn, widthIn, heightIn, valueIn, parentIn);
        areaWidth = areaWidthIn;
        areaHeight = areaHeightIn;
        setPositionsFromColor();
        hexCode.setText(Integer.toHexString(value.getObject().getRGB()));
    }

    @Override
    public void onClicked(int button, int mouseX, int mouseY) {
        if (barHovered(mouseX, mouseY)) {
            barDragging = true;
            readBar = true;
        } else if (colHovered(mouseX, mouseY)) {
            colDragging = true;
            readCol = true;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        hexCode.mouseClicked(mouseX, mouseY, button);
    }

    private boolean barHovered(int mouseX, int mouseY) {
        return mouseX > areaX + ((areaWidth / 7) * 6.1) && mouseX < areaX + areaWidth && mouseY > areaY && mouseY < areaY + ((areaHeight / 7) * 6);
    }

    private boolean colHovered(int mouseX, int mouseY) {
        return mouseX > areaX && mouseX < areaX + ((areaWidth / 7) * 6) && mouseY > areaY && mouseY < areaY + ((areaHeight / 7) * 6);
    }

    @Override
    public void init() {

    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        if (hexCode.edited()) {
            updateFromHex();
        }

        if (colDragging) {
            colSelectorOffsetX = (int) MathHelper.clamp((mouseX - areaX), 1, ((areaWidth / 7) * 6));
            colSelectorOffsetY = (int) MathHelper.clamp((mouseY - areaY), 1, ((areaHeight / 7) * 6));
        }
        if (barDragging) {
            selectorBarOffset = (int) MathHelper.clamp((mouseY - areaY), 1, ((areaHeight / 7) * 6));
        }
        colSelectorX = (int) (areaX + colSelectorOffsetX);
        colSelectorY = (int) (areaY + colSelectorOffsetY);
        selectorBarY = (int) (areaY + selectorBarOffset);
        if (readBar || barDragging) {
            read(false);
            readBar = false;
        }
        if (readCol || colDragging || barDragging) {
            read(true);
            readCol = false;
        }
        drawSelectorIcon(colSelectorX, colSelectorY);
        drawSliderBar((int) (areaX + ((areaWidth / 7) * 6)) + 1, selectorBarY, (int) (areaX + areaWidth));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        colDragging = false;
        barDragging = false;
        hexCode.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void keyPressed(char key, int keycode) {
        hexCode.keyPressed(key, keycode);
    }

    protected Color getCol(int x, int y) {
        return null;
    }

    protected abstract void drawSelectorIcon(int xpos, int ypos);

    protected abstract void drawSliderBar(int xpos, int ypos, int x2);

    protected abstract void drawHexBox(float xpos, float ypos, float width, float height);


    protected void drawColourPicker(int x, int y, int mouseX, int mouseY) {
        areaX = x;
        areaY = y;
        GL11.glPushMatrix();
        rend.enableGradient();
        RenderUtils.tess
                .color(0xffffffff).vertex(x, y, 0)
                .color(0xff000000).vertex(x, y + ((areaHeight / 7) * 6), 0)
                .color(0xff000000).vertex(x + ((areaWidth / 7) * 6), y + ((areaHeight / 7) * 6), 0)
                .color(barCol.getRGB()).vertex(x + ((areaWidth / 7) * 6), y, 0)
                .end(GL11.GL_QUADS);

        GL11.glTranslated(((areaWidth / 7) * 6), 0, 0);
        RenderUtils.tess
                .color(0xffff0000).vertex(x, y, 0)
                .color(0xffff00ff).vertex(x, y + areaHeight / 7, 0)
                .color(0xffff00ff).vertex(x + areaWidth / 7, y + areaHeight / 7, 0)
                .color(0xffff0000).vertex(x + areaWidth / 7, y, 0)
                .end(GL11.GL_QUADS);
        GL11.glTranslated(0, areaHeight / 7, 0);
        RenderUtils.tess.
                color(0xffff00ff).vertex(x, y, 0)
                .color(0xff0000ff).vertex(x, y + areaHeight / 7, 0)
                .color(0xff0000ff).vertex(x + areaWidth / 7, y + areaHeight / 7, 0)
                .color(0xffff00ff).vertex(x + areaWidth / 7, y, 0)
                .end(GL11.GL_QUADS);
        GL11.glTranslated(0, areaHeight / 7, 0);
        RenderUtils.tess
                .color(0xff0000ff).vertex(x, y, 0)
                .color(0xff00ffff).vertex(x, y + areaHeight / 7, 0)
                .color(0xff00ffff).vertex(x + areaWidth / 7, y + areaHeight / 7, 0)
                .color(0xff0000ff).vertex(x + areaWidth / 7, y, 0)
                .end(GL11.GL_QUADS);
        GL11.glTranslated(0, areaHeight / 7, 0);
        RenderUtils.tess
                .color(0xff00ffff).vertex(x, y, 0)
                .color(0xff00ff00).vertex(x, y + areaHeight / 7, 0)
                .color(0xff00ff00).vertex(x + areaWidth / 7, y + areaHeight / 7, 0)
                .color(0xff00ffff).vertex(x + areaWidth / 7, y, 0)
                .end(GL11.GL_QUADS);
        GL11.glTranslated(0, areaHeight / 7, 0);
        RenderUtils.tess
                .color(0xff00ff00).vertex(x, y, 0)
                .color(0xffffff00).vertex(x, y + areaHeight / 7, 0)
                .color(0xffffff00).vertex(x + areaWidth / 7, y + areaHeight / 7, 0)
                .color(0xff00ff00).vertex(x + areaWidth / 7, y, 0)
                .end(GL11.GL_QUADS);
        GL11.glTranslated(0, areaHeight / 7, 0);
        RenderUtils.tess
                .color(0xffffff00).vertex(x, y, 0)
                .color(0xffff0000).vertex(x, y + areaHeight / 7, 0)
                .color(0xffff0000).vertex(x + areaWidth / 7, y + areaHeight / 7, 0)
                .color(0xffffff00).vertex(x + areaWidth / 7, y, 0)
                .end(GL11.GL_QUADS);
        GL11.glPopMatrix();
        rend.disableGradient();
        hexCode.setX((int) ((areaX + areaWidth / 2) - Fonts.normal.getStringWidth(hexCode.getText()) / 2))
                .setY((int) ((areaY + areaHeight) - Fonts.normal.getStringHeight("T")));
        drawHexBox(x, (y + (areaHeight / 7) * 6), areaWidth, areaHeight / 7 + 1);
        hexCode.render(mouseX, mouseY);
    }

    private void read(boolean colOrBar) {
        ScaledResolution res = new ScaledResolution(minecraft);
        GL11.glFlush();
        GL11.glFinish();
        if (colOrBar) {
            GL11.glReadPixels(colSelectorX * res.getScaleFactor(), minecraft.displayHeight - (colSelectorY * res.getScaleFactor()), 1, 1, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, RGB);
        } else {
            GL11.glReadPixels((int) ((areaX + ((areaWidth / 7) * 6.5)) * res.getScaleFactor()), minecraft.displayHeight - (selectorBarY * res.getScaleFactor()), 1, 1, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, RGB);
        }
        int red, green, blue;

        red = Byte.toUnsignedInt(RGB.get(0));
        green = Byte.toUnsignedInt(RGB.get(1));
        blue = Byte.toUnsignedInt(RGB.get(2));

        if (colOrBar) {
            value.setObject(new Color(red, green, blue));
        } else {
            barCol = new Color(red, green, blue);
        }
        hexCode.setText(Integer.toHexString(value.getObject().getRGB()));
    }

    private void updateFromHex() {
        int color;
        if (hexCode.getText().length() < 8) {
            return;
        }
        try {
            color = Integer.parseUnsignedInt(hexCode.getText(), 16);
            this.value.setObject(new Color(color));
            this.setPositionsFromColor();
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }

    }

    private void setPositionsFromColor() {

        int red = value.getObject().getRed();
        int green = value.getObject().getGreen();
        int blue = value.getObject().getBlue();

        float[] HSB = Color.RGBtoHSB(red, green, blue, null);

        double min = Math.min(Math.min(red, green), blue);
        double max = Math.max(Math.max(red, green), blue);

        if (max == min) {
            HSB[1] = 0;
        }

        selectorBarOffset = (int) (((areaHeight / 7) * 6) - ((areaHeight / 7) * 6) * (HSB[0]));
        colSelectorOffsetX = (int) (((areaWidth / 7) * 6) * HSB[1]);
        colSelectorOffsetY = (int) (((areaHeight / 7) * 6) - (((areaHeight / 7) * 6) * HSB[2]));
        this.barCol = new Color(Color.HSBtoRGB(HSB[0], 1, 1));
    }

    private void forceRead(boolean colOrBar) {
        if (colOrBar) {
            read(true);
        } else {
            read(false);
        }
    }


}
