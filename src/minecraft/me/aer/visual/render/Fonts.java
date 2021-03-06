package me.aer.visual.render;

import me.aer.implementation.module.base.ModuleManager;
import me.aer.implementation.module.modules.Dummy;
import me.aer.implementation.module.modules.util.HUD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Fonts {

    public static String currentFont = "Default";
    public static CustomFontRenderer small;
    public static CustomFontRenderer normal;
    public static CustomFontRenderer mid;
    public static CustomFontRenderer big;
    public static CustomFontRenderer massive;

    public static ArrayList<FontSet> cachedFonts = new ArrayList<>();


    public static CustomFontRenderer createFont(String path, float size, String name) {
        try {
            return new CustomFontRenderer(Font.createFont(
                    Font.TRUETYPE_FONT, Dummy.class.getResourceAsStream(path)).deriveFont(Font.PLAIN, size), false, name);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFont(String fontName) {
        cacheCurrentFont();
        currentFont = fontName;
        if (!loadFontFromCache(fontName)) {
            loadFont(fontName);
        }
        cacheCurrentFont();
        ((HUD) ModuleManager.getModuleByName("HUD")).resetAnimations();
    }

    private static boolean loadFontFromCache(String name) {
        for (FontSet f : cachedFonts) {
            if (f.name.equalsIgnoreCase(name)) {
                small = f.small;
                normal = f.normal;
                mid = f.mid;
                big = f.big;
                massive = f.massive;
                return true;
            }
        }
        return false;
    }

    private static void cacheCurrentFont() {
        cachedFonts.add(new FontSet(currentFont, small, normal, mid, big, massive));
    }


    /**
     * Caches all known fonts to use later.
     */
    public static void load() {
        cacheAllFonts();
    }

    private static void cacheAllFonts() {
        Logger LOGGER = LogManager.getLogger();
        LOGGER.info("[Aer] Loading fonts... [1/5]");
        loadFont("Default");
        LOGGER.info("[Aer] Loading fonts... [2/5]");
        loadFont("Modern");
        LOGGER.info("[Aer] Loading fonts... [3/5]");
        loadFont("Mc");
        LOGGER.info("[Aer] Loading fonts... [4/5]");
        loadFont("SciFi");
        LOGGER.info("[Aer] Loading fonts... [5/5]");
        loadFont("Plain");
    }

    private static void loadFont(String fontName) {
        if (fontName.equalsIgnoreCase("Default")) {
            currentFont = "Default";
            small = createFont("/me/aer/visual/render/fonts/default.ttf", 10f, "Default Small");
            normal = createFont("/me/aer/visual/render/fonts/default.ttf", 18f, "Default Normal");
            mid = createFont("/me/aer/visual/render/fonts/default.ttf", 22f, "Default Mid");
            big = createFont("/me/aer/visual/render/fonts/default.ttf", 28f, "Default Big");
            massive = createFont("/me/aer/visual/render/fonts/default.ttf", 128f, "Default Massive");
        } else if (fontName.equalsIgnoreCase("Modern")) {
            currentFont = "Modern";
            small = createFont("/me/aer/visual/render/fonts/modern.ttf", 10f, "Modern Small");
            normal = createFont("/me/aer/visual/render/fonts/modern.ttf", 18f, "Modern Normal");
            mid = createFont("/me/aer/visual/render/fonts/modern.ttf", 22f, "Modern Mid");
            big = createFont("/me/aer/visual/render/fonts/modern.ttf", 28f, "Modern Big");
            massive = createFont("/me/aer/visual/render/fonts/modern.ttf", 128f, "Modern Massive");
        } else if (fontName.equalsIgnoreCase("Mc")) {
            currentFont = "Mc";
            small = createFont("/me/aer/visual/render/fonts/mc.ttf", 10f, "Minecraft Small");
            normal = createFont("/me/aer/visual/render/fonts/mc.ttf", 18f, "Minecraft Normal");
            mid = createFont("/me/aer/visual/render/fonts/mc.ttf", 22f, "Minecraft Mid");
            big = createFont("/me/aer/visual/render/fonts/modern.ttf", 28f, "Minecraft Big");
            massive = createFont("/me/aer/visual/render/fonts/modern.ttf", 128f, "Minecraft Massive");
        } else if (fontName.equalsIgnoreCase("SciFi")) {
            currentFont = "SciFi";
            small = createFont("/me/aer/visual/render/fonts/scifi.ttf", 10f, "SciFi Small");
            normal = createFont("/me/aer/visual/render/fonts/scifi.ttf", 18f, "SciFi Normal");
            mid = createFont("/me/aer/visual/render/fonts/scifi.ttf", 22f, "SciFi Mid");
            big = createFont("/me/aer/visual/render/fonts/scifi.ttf", 28f, "SciFi Big");
            massive = createFont("/me/aer/visual/render/fonts/scifi.ttf", 128f, "SciFi Massive");
        } else if (fontName.equalsIgnoreCase("Plain")) {
            currentFont = "Plain";
            small = createFont("/me/aer/visual/render/fonts/plain.ttf", 10f, "Plain Small");
            normal = createFont("/me/aer/visual/render/fonts/plain.ttf", 18f, "Plain Normal");
            mid = createFont("/me/aer/visual/render/fonts/plain.ttf", 22f, "Plain Mid");
            big = createFont("/me/aer/visual/render/fonts/plain.ttf", 28f, "Plain Big");
            massive = createFont("/me/aer/visual/render/fonts/plain.ttf", 128f, "Plain Massive");
        }
        cacheCurrentFont();
    }
}
