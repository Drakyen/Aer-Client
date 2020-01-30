package net.aer.gui;

import net.aer.Aer;
import net.aer.gui.clickgui.elements.Element;
import net.aer.gui.clickgui.elements.ElementKeybinding;
import net.aer.gui.clickgui.elements.ModuleButton;
import net.aer.gui.clickgui.elements.Panel;
import net.aer.module.Category;
import net.aer.render.RainbowUtil;
import net.aer.render.render2D.CustomFontRenderer;
import net.aer.render.render2D.Fonts;
import net.aer.render.render2D.RenderUtils2D;
import net.aer.utils.config.ConfigHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class ClickGui extends GuiScreen {

	public static ArrayList<Panel> panels;

	private RainbowUtil rainbow = new RainbowUtil(2);
	public Color col;
	private CustomFontRenderer font = Fonts.normal;
	public Properties ClickGuiProps;

	private ModuleButton hoveredModule;

	public boolean blurMode;

	private boolean isBlurred;

	public ClickGui() {

		ClickGuiProps = ConfigHandler.loadSettings("ClickGuiProps", new Properties());

		panels = new ArrayList<>();
		col = new Color(0, 20, 200, 255);

		int px = 10;
		int py = 10;
		int pyd = 10;
		int pw = 90;
		int ph = 15;
		boolean panelExtended = false;

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
					py = pyd;
				}
				if (ClickGuiProps.containsKey(panelName + "extended")) {
					panelExtended = Boolean.parseBoolean((ClickGuiProps.getProperty(panelName + "extended")));
				} else {
					panelExtended = false;
				}
				panels.add(new Panel(px, py, pw, ph, panelName, panelExtended, c, this));

				pyd += 50;
			}
		}

	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (blurMode != isBlurred) {
			if (blurMode) {
				if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
					if (mc.entityRenderer.theShaderGroup != null) {
						mc.entityRenderer.theShaderGroup.deleteShaderGroup();
					}
					mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
				}
				isBlurred = true;
			} else {
				if (mc.entityRenderer.theShaderGroup != null) {
					mc.entityRenderer.theShaderGroup.deleteShaderGroup();
					mc.entityRenderer.theShaderGroup = null;
				}
				isBlurred = false;
			}
		}
		for (Panel p : panels) {
			p.drawScreen(mouseX, mouseY, partialTicks);
		}
		if (hoveredModule != null && hoveredModule.hoveredTimer > 50) {
			ScaledResolution res = new ScaledResolution(Aer.minecraft);
			if (mouseX + 12 + font.getStringWidth(hoveredModule.module.getDescription()) > res.getScaledWidth()) {
				RenderUtils2D.drawGradientRectHoriz((int) (mouseX - 12 - font.getStringWidth(hoveredModule.module.getDescription())), mouseY, mouseX, mouseY + 16, 0f, 0xaa333333, col.darker().darker().getRGB());
				RenderUtils2D.drawRect(mouseX, mouseY, (int) (mouseX - 12 - font.getStringWidth(hoveredModule.module.getDescription())), mouseY - 1, 0xaa333333);
				RenderUtils2D.drawRect(mouseX, mouseY + 16, (int) (mouseX - 12 - font.getStringWidth(hoveredModule.module.getDescription())), mouseY + 15, 0xaa333333);
				RenderUtils2D.drawRect((int) (mouseX - 12 - font.getStringWidth(hoveredModule.module.getDescription())), mouseY, (int) (mouseX - 12 - font.getStringWidth(hoveredModule.module.getDescription())) + 1, mouseY + 16, 0xaa333333);
				RenderUtils2D.drawRect((int) (mouseX - 12 - font.getStringWidth(hoveredModule.module.getDescription())), mouseY, (int) (mouseX - 12 - font.getStringWidth(hoveredModule.module.getDescription())) + 1, mouseY + 16, 0xaa333333);
				RenderUtils2D.drawString(font, hoveredModule.module.getDescription(), mouseX - 5 - font.getStringWidth(hoveredModule.module.getDescription()), mouseY + 4, col.brighter().getRGB(), true);
			} else {
				RenderUtils2D.drawGradientRectHoriz(mouseX, mouseY, (int) (mouseX + 12 + font.getStringWidth(hoveredModule.module.getDescription())), mouseY + 16, 0f, 0xaa333333, col.darker().darker().getRGB());
				RenderUtils2D.drawRect(mouseX, mouseY, (int) (mouseX + 12 + font.getStringWidth(hoveredModule.module.getDescription())), mouseY - 1, 0xaa333333);
				RenderUtils2D.drawRect(mouseX, mouseY + 16, (int) (mouseX + 12 + font.getStringWidth(hoveredModule.module.getDescription())), mouseY + 15, 0xaa333333);
				RenderUtils2D.drawRect(mouseX, mouseY, mouseX + 1, mouseY + 16, 0xaa333333);
				RenderUtils2D.drawRect((int) (mouseX + 12 + font.getStringWidth(hoveredModule.module.getDescription())), mouseY, (int) (mouseX + 12 + font.getStringWidth(hoveredModule.module.getDescription())) - 1, mouseY + 16, 0xaa333333);
				RenderUtils2D.drawString(font, hoveredModule.module.getDescription(), mouseX + 10, mouseY + 4, col.brighter().getRGB(), true);
			}
		}
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		for (Panel p : panels) {
			p.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {
		for (Panel p : panels) {
			p.mouseReleased(mouseX, mouseY, state);
		}
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE && !this.keybindListening()) {
			Aer.minecraft.displayGuiScreen((GuiScreen) null);
		}
		for (Panel p : panels) {
			p.keyTyped(typedChar, keyCode);
		}
	}

	private boolean keybindListening() {
		boolean flag = false;
		for (Panel p : panels) {
			for (ModuleButton em : p.modules) {
				for (Element e : em.menuElements) {
					if (e instanceof ElementKeybinding) {
						if (((ElementKeybinding) e).listening) {
							return true;
						}
					}
				}
			}
		}
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

	public void onGuiClosed() {
		if (mc.entityRenderer.theShaderGroup != null) {
			mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			mc.entityRenderer.theShaderGroup = null;
		}
		for (Panel p : panels) {
			ClickGuiProps.setProperty(p.name + "xPos", "" + p.x);
			ClickGuiProps.setProperty(p.name + "yPos", "" + p.y);
			ClickGuiProps.setProperty(p.name + "extended", "" + p.extended);
			for (ModuleButton m : p.modules) {
				ClickGuiProps.setProperty(m.name + "extended", "" + m.extended);
			}
		}
		ConfigHandler.saveSettings("ClickGuiProps", ClickGuiProps);
	}

	public void changeCol(Color newCol) {
		col = newCol;
		for (Panel p : panels) {
			p.resetCols();
		}
	}

	public void setHoveredModule(ModuleButton moduleButton) {
		this.hoveredModule = moduleButton;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public void mouseScrolled(int scroll) {
		for (Panel p : panels) {
			p.scroll(scroll);
		}
	}


}
