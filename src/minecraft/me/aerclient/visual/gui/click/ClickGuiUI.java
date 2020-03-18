package me.aerclient.visual.gui.click;

import me.aerclient.config.ConfigHandler;
import me.aerclient.implementation.module.base.Category;
import me.aerclient.implementation.utils.Utilities;
import me.aerclient.visual.gui.base.basic.UI;
import me.aerclient.visual.gui.click.base.Element;
import me.aerclient.visual.gui.click.base.Keybinding;
import me.aerclient.visual.gui.click.base.ModuleButton;
import me.aerclient.visual.gui.click.base.Panel;
import me.aerclient.visual.style.Style;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class ClickGuiUI extends GuiScreen implements Utilities {


    private static Style style;
    private static ArrayList<Panel> panels;
    private static ArrayList<Panel> reversedPanels;
    private Properties clickGuiProps;
    private static Color guiCol;
    private boolean blurMode;
    private boolean soundMode;
    private UI hoveredObject;
    private int tooltipDelay = 400;
    private boolean showTooltips = true;
    private float scrollSpeed = 1f;

    public ClickGuiUI(Style styleIn) {
        clickGuiProps = ConfigHandler.loadSettings("clickGuiProps", new Properties());
        style = styleIn;
        init();
    }

    public boolean isShowTooltips() {
        return showTooltips;
    }

    public void setShowTooltips(boolean showTooltips) {
        this.showTooltips = showTooltips;
    }

    public int getTooltipDelay() {
        return tooltipDelay;
    }

    public void setTooltipDelay(int tooltipDelay) {
        this.tooltipDelay = tooltipDelay;
    }

    public void init() {
        createPanels();
        setCol(new Color(0.5f, 0.5f, 0.5f, 0.5f));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        rend2D.enableArray(GL11.GL_VERTEX_ARRAY);
        rend2D.enableBlending();
        GlStateManager.disableTexture2D();
        for (Panel p : panels) {
            p.render(mouseX, mouseY);
        }
        if (hoveredObject != null && hoveredObject.hovered(mouseX, mouseY)) {
            if (hoveredObject instanceof ModuleButton) {
                style.renderTooltip(((ModuleButton) hoveredObject).getModule().getDescription(), mouseX, mouseY, guiCol);
            } else if (hoveredObject instanceof Element) {
                if (((Element) hoveredObject).getSetting() != null) {
                    style.renderTooltip(((Element) hoveredObject).getSetting().getDescription(), mouseX, mouseY, guiCol);
                }
            }
        } else {
            hoveredObject = null;
        }
        rend2D.disableArray(GL11.GL_VERTEX_ARRAY);
        GlStateManager.enableTexture2D();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (Panel p : panels) {
            if (allowAction(p, mouseX, mouseY)) {
                p.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseScrolled(int scroll) {
        super.mouseScrolled(scroll);
        scroll = scroll / 10;
        for (Panel p : panels) {
            p.scrolled((int) -(scroll * scrollSpeed));
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        for (Panel p : panels) {
            p.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE && !this.keybindListening()) {
            minecraft.displayGuiScreen(null);
        }
        for(Panel p : panels) {
            p.keyPressed(keyCode);
        }
    }

    public Color getGuiCol(){
        return guiCol;
    }

    public void setHovered(ModuleButton button){
        this.hoveredObject = button;
    }

    public void setHovered(Element element){
        this.hoveredObject = element;
    }

    private void createPanels() {

        panels = new ArrayList<>();
        reversedPanels = new ArrayList<>();

        int px;
        int py;
        int pyo = 10;
        boolean panelExtended;

        for (Category c : Category.values()) {
            if (c != Category.HIDDEN) {
                String panelName = c.name().toUpperCase();
                if (clickGuiProps.containsKey(panelName + "xPos")) {
                    px = Integer.parseInt(clickGuiProps.getProperty(panelName + "xPos"));
                } else {
                    px = 10;
                }
                if (clickGuiProps.containsKey(panelName + "yPos")) {
                    py = Integer.parseInt(clickGuiProps.getProperty(panelName + "yPos"));
                } else {
                    py = pyo;
                }
                if (clickGuiProps.containsKey(panelName + "extended")) {
                    panelExtended = Boolean.parseBoolean((clickGuiProps.getProperty(panelName + "extended")));
                } else {
                    panelExtended = false;
                }
                panels.add(style.getNewPanel(px, py, panelName, panelExtended, this, c));
                pyo += 50;
            }
        }
        reversedPanels.addAll(panels);
        Collections.reverse(reversedPanels);
    }

    private boolean keybindListening() {
        for (Panel p : panels) {
            for (UI button : p.getChildren()) {
                for (UI e : ((ModuleButton)button).getChildren()) {
                    if (e instanceof Keybinding && ((Keybinding) e).isListening()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        if (blurMode) {
            if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
                if (mc.entityRenderer.theShaderGroup != null) {
                    mc.entityRenderer.theShaderGroup.deleteShaderGroup();
                }
                mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
            }
        }
    }

    public void setCol(Color col) {
        guiCol = col;
        for (Panel p : panels) {
            p.updateCols(col);
            for (UI button : p.getChildren()) {
                ((ModuleButton)button).updateCols(col);
                for (UI e : ((ModuleButton)button).getChildren()) {
                    ((Element)e).updateCols(col);
                }
            }
        }
    }

    public boolean allowAction(Panel origin, int mouseX, int mouseY) {
        for (Panel p : reversedPanels) {
            if (p == origin) {
                return true;
            } else if (p.anythingHovered(mouseX, mouseY)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onGuiClosed() {
        if (mc.entityRenderer.theShaderGroup != null) {
            mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            mc.entityRenderer.theShaderGroup = null;
        }
        for (Panel p : panels) {
            clickGuiProps.setProperty(p.getCat().name() + "xPos", "" + p.getX());
            clickGuiProps.setProperty(p.getCat().name() + "yPos", "" + p.getY());
            clickGuiProps.setProperty(p.getCat().name() + "extended", "" + p.isExtended());
            for (UI m : p.getChildren()) {
                clickGuiProps.setProperty(((ModuleButton)m).getName() + "extended", "" + ((ModuleButton)m).isExtended());
            }
        }
        ConfigHandler.saveSettings("clickGuiProps", clickGuiProps);
    }

    public boolean getSoundMode(){
        return soundMode;
    }

    public boolean getBlurMode(){return blurMode;}

    public Properties getProps(){
        return clickGuiProps;
    }

    public Style getStyle(){
        return style;
    }

    public void setBlurMode(boolean blurModeIn) {
        blurMode = blurModeIn;
    }

    public void setSoundMode(boolean soundMode) {
        this.soundMode = soundMode;
    }

    public void setScrollSpeed(float scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

}
