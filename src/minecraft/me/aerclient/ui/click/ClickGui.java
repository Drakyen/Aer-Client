package me.aerclient.ui.click;

import com.sun.org.apache.xpath.internal.operations.Mod;
import me.aerclient.config.ConfigHandler;
import me.aerclient.module.base.Category;
import me.aerclient.style.Style;
import me.aerclient.ui.base.basic.UI;
import me.aerclient.ui.click.base.Element;
import me.aerclient.ui.click.base.Keybinding;
import me.aerclient.ui.click.base.ModuleButton;
import me.aerclient.ui.click.base.Panel;
import me.aerclient.utils.Utilities;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class ClickGui extends GuiScreen implements Utilities {


    private static Style style;
    private static ArrayList<Panel> panels;
    private static ArrayList<Panel> reversedPanels;
    private Properties ClickGuiProps;
    private static Color guiCol;
    private boolean blurMode;
    private boolean soundMode;
    private Object hoveredObject;


    public ClickGui(Style styleIn){
        ClickGuiProps = ConfigHandler.loadSettings("ClickGuiProps", new Properties());
        this.style = styleIn;
        init();
    }

    public void init(){
       createPanels();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for(Panel p : panels) {
            p.render(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for(Panel p : panels) {
            p.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        for(Panel p : panels) {
            p.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for(Panel p : panels) {
            p.keyPressed(keyCode);
        }
    }

    public Color getGuiCol(){
        return guiCol;
    }

    public void setStyle(Style newStyle){
        style = newStyle;
        init();
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
                if (ClickGuiProps.containsKey(panelName + "xPos")) {
                    px = Integer.parseInt(ClickGuiProps.getProperty(panelName + "xPos"));
                } else {
                    px = 10;
                }
                if (ClickGuiProps.containsKey(panelName + "yPos")) {
                    py = Integer.parseInt(ClickGuiProps.getProperty(panelName + "yPos"));
                } else {
                    py = pyo;
                }
                if (ClickGuiProps.containsKey(panelName + "extended")) {
                    panelExtended = Boolean.parseBoolean((ClickGuiProps.getProperty(panelName + "extended")));
                } else {
                    panelExtended = false;
                }
                panels.add(style.getNewPanel(px, py, panelName, panelExtended, this));
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
                    if ((Element)e instanceof Keybinding && ((Keybinding) e).isListening()) {
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
            for (UI button : p.getChildren()) {
                ((ModuleButton)button).updateCols();
                for (UI e : ((ModuleButton)button).getChildren()) {
                    ((Element)e).updateCols();
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
            ClickGuiProps.setProperty(p.getName() + "xPos", "" + p.getX());
            ClickGuiProps.setProperty(p.getName() + "yPos", "" + p.getY());
            ClickGuiProps.setProperty(p.getName() + "extended", "" + p.isExtended());
            for (UI m : p.getChildren()) {
                ClickGuiProps.setProperty(((ModuleButton)m).getName() + "extended", "" + ((ModuleButton)m).isExtended());
            }
        }
        ConfigHandler.saveSettings("ClickGuiProps", ClickGuiProps);
    }

    public boolean getSoundMode(){
        return soundMode;
    }

}
