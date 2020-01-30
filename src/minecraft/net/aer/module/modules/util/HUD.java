package net.aer.module.modules.util;

import com.darkmagician6.eventapi.EventTarget;
import net.aer.Aer;
import net.aer.events.EventRender2D;
import net.aer.module.Category;
import net.aer.module.Module;
import net.aer.module.ModuleManager;
import net.aer.render.RainbowUtil;
import net.aer.render.Sorter;
import net.aer.render.render2D.CustomFontRenderer;
import net.aer.render.render2D.Fonts;
import net.aer.render.render2D.RenderUtils2D;
import net.aer.utils.player.PlayerUtil;
import net.aer.utils.valuesystem.BooleanValue;
import net.aer.utils.valuesystem.NumberValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class HUD extends Module {

	private DecimalFormat df2 = new DecimalFormat("#");
	private RainbowUtil rbw = new RainbowUtil(3);
	private NumberValue red = new NumberValue("Red", 1f, 0f, 1f);
	private NumberValue green = new NumberValue("Green", 1f, 0f, 1f);
	private NumberValue blue = new NumberValue("Blue", 1f, 0f, 1f);
	private BooleanValue coords = new BooleanValue("Coords", true);
	private BooleanValue netherCoords = new BooleanValue("NetherCoords", true);
	private BooleanValue direction = new BooleanValue("Direction", true);
	private BooleanValue showYpos = new BooleanValue("ShowYPos", false);
	private BooleanValue armor = new BooleanValue("Armor", true);
	private BooleanValue modList = new BooleanValue("ModList", false);
	private BooleanValue rainbow = new BooleanValue("Rainbow", false);
	private CustomFontRenderer font;
	private HashMap animations = new HashMap<Module, Integer>();
	private CustomFontRenderer modlistfont;
	private int darkcol;
	private int lightcol;


	public HUD() {
		super("HUD", Category.UTIL, "Shows useful information onscreen");
	}

	public void setup() {
		font = Fonts.mid;
		modlistfont = Fonts.normal;
	}

	public void onDisable() {
		animations.clear();
	}

	@EventTarget
	public void onDraw2D(EventRender2D event) {
		if (Aer.minecraft.world == null) {
			return;
		}
		ScaledResolution res = new ScaledResolution(minecraft);
		int col;
		if (rainbow.getObject()) {
			col = rbw.getRGB();
			darkcol = rbw.getCol().darker().getRGB();
			rbw.cycleColoursCustomSpeed();
			rbw.randomiseDirection();
			rbw.cycleColorsRandom();
			boolean hasReset = false;
			if (!modList.getObject() && !hasReset) {
				animations.clear();
				hasReset = true;
			}
			if (modList.getObject() && hasReset) {
				hasReset = false;
			}
		} else {
			col = new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), 1f).getRGB();
			darkcol = new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), 1f).darker().getRGB();
			lightcol = new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), 1f).brighter().getRGB();
		}
		ScaledResolution scaledRes = new ScaledResolution(Aer.minecraft);
		if (coords.getObject()) {
			if (netherCoords.getObject()) {
				if (showYpos.getObject()) {
					if (minecraft.player.dimension == 0) {
						RenderUtils2D.drawString(
								font, "\u00a7pXYZ: \u00a7r" + df2.format(minecraft.player.posX - 0.5)
										+ " " + df2.format(minecraft.player.posY) + " " + df2.format(minecraft.player.posZ - 0.5) + " \u00a7p[\u00a7r"
										+ df2.format(minecraft.player.posX / 8 - 0.5)
										+ " " + df2.format(minecraft.player.posY) + " " + df2.format(minecraft.player.posZ / 8 - 0.5) + "\u00a7p]\u00a7r"
								,
								5f, (float) scaledRes.getScaledHeight() - 18, col, true
						);
					} else {
						RenderUtils2D.drawString(
								font, "\u00a7pXYZ: \u00a7r" + df2.format(minecraft.player.posX * 8 - 0.5)
										+ " " + df2.format(minecraft.player.posY) + " " + df2.format(minecraft.player.posZ * 8 - 0.5) + " \u00a7p[\u00a7r"
										+ df2.format(minecraft.player.posX - 0.5)
										+ " " + df2.format(minecraft.player.posY) + " " + df2.format(minecraft.player.posZ - 0.5) + "\u00a7p]\u00a7r"
								,
								5f, (float) scaledRes.getScaledHeight() - 18, col, true
						);
					}
				} else {
					if (minecraft.player.dimension != -1) {
						RenderUtils2D.drawString(
								font, "\u00a7pXZ: \u00a7r" + df2.format(minecraft.player.posX - 0.5)
										+ " " + df2.format(minecraft.player.posZ - 0.5) + " \u00a7p[\u00a7r"
										+ df2.format(minecraft.player.posX / 8 - 0.5)
										+ " " + df2.format(minecraft.player.posZ / 8 - 0.5) + "\u00a7p]\u00a7r"
								,
								5f, (float) scaledRes.getScaledHeight() - 18, col, true
						);
					} else {
						RenderUtils2D.drawString(
								font, "\u00a7pXZ: \u00a7r" + df2.format(minecraft.player.posX * 8 - 0.5)
										+ " " + df2.format(minecraft.player.posZ * 8 - 0.5) + " \u00a7p[\u00a7r"
										+ df2.format(minecraft.player.posX - 0.5)
										+ " " + df2.format(minecraft.player.posZ - 0.5) + "\u00a7p]\u00a7r"
								,
								5f, (float) scaledRes.getScaledHeight() - 18, col, true
						);
					}
				}
			} else {
				if (showYpos.getObject()) {
					if (minecraft.player.dimension == -1) {
						RenderUtils2D.drawString(
								font, "\u00a7pXYZ: \u00a7r" + df2.format(minecraft.player.posX * 8 - 0.5)
										+ " " + df2.format(minecraft.player.posY) + " " + df2.format(minecraft.player.posZ * 8 - 0.5) + " \u00a7p[\u00a7rOverworld\u00a7p]\u00a7r",
								5f, (float) scaledRes.getScaledHeight() - 18, col, true
						);
					} else {
						RenderUtils2D.drawString(
								font, "\u00a7pXYZ: \u00a7r" + df2.format(minecraft.player.posX - 0.5)
										+ " " + df2.format(minecraft.player.posY) + " " + df2.format(minecraft.player.posZ - 0.5),
								5f, (float) scaledRes.getScaledHeight() - 18, col, true
						);
					}
				} else {
					if (minecraft.player.dimension == -1) {
						RenderUtils2D.drawString(
								font, "\u00a7pXZ: \u00a7r" + df2.format(minecraft.player.posX * 8 - 0.5)
										+ " " + df2.format(minecraft.player.posZ * 8 - 0.5) + " \u00a7p[\u00a7rOverworld\u00a7p]\u00a7r",
								5f, (float) scaledRes.getScaledHeight() - 18, col, true
						);
					} else {
						RenderUtils2D.drawString(
								font, "\u00a7pXZ: \u00a7r" + df2.format(minecraft.player.posX - 0.5)
										+ " " + df2.format(minecraft.player.posZ - 0.5),
								5f, (float) scaledRes.getScaledHeight() - 18, col, true
						);
					}
				}
			}
		} else if (netherCoords.getObject()) {
			if (showYpos.getObject()) {
				if (minecraft.player.dimension == -1) {
					RenderUtils2D.drawString(
							font, "\u00a7pXYZ: \u00a7r" + df2.format(minecraft.player.posX - 0.5)
									+ " " + df2.format(minecraft.player.posY) + " " + df2.format(minecraft.player.posZ - 0.5),
							5f, (float) scaledRes.getScaledHeight() - 18, col, true
					);
				} else {
					RenderUtils2D.drawString(
							font, "\u00a7pXYZ: \u00a7r" + df2.format(minecraft.player.posX / 8 - 0.5)
									+ " " + df2.format(minecraft.player.posY) + " " + df2.format(minecraft.player.posZ / 8 - 0.5) + " \u00a7p[\u00a7rNether\u00a7p]\u00a7r",
							5f, (float) scaledRes.getScaledHeight() - 18, col, true
					);
				}
			} else {
				if (minecraft.player.dimension == -1) {
					RenderUtils2D.drawString(
							font, "\u00a7pXZ: \u00a7r" + df2.format(minecraft.player.posX - 0.5)
									+ " " + df2.format(minecraft.player.posZ - 0.5),
							5f, (float) scaledRes.getScaledHeight() - 18, col, true
					);
				} else {
					RenderUtils2D.drawString(
							font, "\u00a7pXZ: \u00a7r" + df2.format(minecraft.player.posX / 8 - 0.5)
									+ " " + df2.format(minecraft.player.posZ / 8 - 0.5) + " \u00a7p[\u00a7rNether\u00a7p]\u00a7r",
							5f, (float) scaledRes.getScaledHeight() - 18, col, true
					);
				}
			}
		}

		if (direction.getObject()) {
			if (coords.getObject() || netherCoords.getObject()) {
				RenderUtils2D.drawString(font, PlayerUtil.direction3(Aer.minecraft.player), 5f, (float) scaledRes.getScaledHeight() - 18 - 15, col, true);
			} else {
				RenderUtils2D.drawString(font, PlayerUtil.direction3(Aer.minecraft.player), 5f, (float) scaledRes.getScaledHeight() - 18, col, true);
			}
		}


		if (armor.getObject()) {
			int offset = -72;
			int x = (res.getScaledWidth() - res.getScaledWidth() / 2);
			int y = res.getScaledHeight() - 40;
			if (minecraft.player.isSurvival() || minecraft.player.isAdventure()) {
				y -= 16;
			}
			for (ItemStack is : minecraft.player.inventory.armorInventory) {
				RenderUtils2D.drawItem(is, x - offset, y, 1f, font);

				offset += 20;
			}

		}


		if (modList.getObject()) {
			int offset = 0;
			int module = 0;
			int y = 0;

			Sorter c = new Sorter();
			ArrayList<Module> sortedModules = ModuleManager.visibleModuleList;
			sortedModules.sort(c);

			for (Module m : sortedModules) {
				int animateX = ((Integer) animations.getOrDefault(m, new Integer(-1))).intValue();

				if (m.isActive()) {
					if (animateX == -1) {
						animations.put(m, new Integer(0));
						animateX = 0;
					} else if (animateX < modlistfont.getStringWidth(m.getName()) + 1) {
						animateX++;
						animations.replace(m, animateX);
					}
				} else {
					if (animateX > 0) {
						animateX--;
						animations.replace(m, animateX);
					} else if (animateX <= 0) {
						animations.remove(m);
						animateX = 0;
					}
				}


				RenderUtils2D.drawGradientRectHoriz(res.getScaledWidth() - animateX + 1, y + offset, (int) (res.getScaledWidth() + modlistfont.getStringWidth(m.getName()) - animateX + 2), y + offset + 12, 100f, darkcol, 0x99777777);
				RenderUtils2D.drawVerticalLine(res.getScaledWidth() - animateX, y + offset - 1, y + offset + 12, lightcol);
				RenderUtils2D.drawString(modlistfont, m.getName(), res.getScaledWidth() - animateX + 1, y + offset + 1, col, true);

				if (animateX != 0) {
					offset += 12;
				}
			}
		}

	}

	public void onGuiValueUpdate() {
		rbw.reseedColour(new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), 1f));
	}


}
