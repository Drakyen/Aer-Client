package net.aer.render.render2D;

import net.aer.module.Testing;

import java.awt.*;
import java.io.IOException;

public class Fonts {

	public static CustomFontRenderer small = createFont("/net/aer/render/render2D/zeros.ttf", 10f, "Zeros Small");
	public static CustomFontRenderer normal = createFont("/net/aer/render/render2D/zeros.ttf", 18f, "Zeros Normal");
	public static CustomFontRenderer mid = createFont("/net/aer/render/render2D/zeros.ttf", 22f, "Zeros Mid");
	public static CustomFontRenderer big = createFont("/net/aer/render/render2D/zeros.ttf", 28f, "Zeros Mid");
	public static CustomFontRenderer massive = createFont("/net/aer/render/render2D/zeros.ttf", 128f, "Zeros Massive");


	public static CustomFontRenderer createFont(String path, float size, String name) {
		try {
			return new CustomFontRenderer(Font.createFont(
					Font.TRUETYPE_FONT, Testing.class.getResourceAsStream(path)).deriveFont(Font.PLAIN, size), false, name);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Does "nothing", but causes this class to be loaded, which initalises the fonts and stops it from lagging later.
	 */
	public static void load() {

	}
}
