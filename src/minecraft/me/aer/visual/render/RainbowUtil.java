package me.aer.visual.render;

import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class RainbowUtil {

	private int red = (int) MathHelper.clamp(Math.random() * 300, 0, 255);
	private int green = (int) MathHelper.clamp(Math.random() * 300, 0, 255);
	private int blue = (int) MathHelper.clamp(Math.random() * 300, 0, 255);
	private int attempt = 0;
	private int speed = 2;
	private boolean redUp = getRandBool();
	private boolean blueUp = getRandBool();
	private boolean greenUp = getRandBool();


	public RainbowUtil() {
		reseedColour();
	}

	public RainbowUtil(int speedin) {
		reseedColour();
		speed = speedin;
	}

	public RainbowUtil(double seed) {
		reseedColour(seed);
	}

	public RainbowUtil(double seed, int speedin) {
		reseedColour(seed);
		speed = speedin;
	}

	public int getRGB() {
		return new Color(red, green, blue).getRGB();
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public RainbowUtil reseedColour() {
		red = (int) MathHelper.clamp(Math.random() * 300, 0, 255);
		green = (int) MathHelper.clamp(Math.random() * 300, 0, 255);
		blue = (int) MathHelper.clamp(Math.random() * 300, 0, 255);
		return  this;
	}

	public RainbowUtil reseedColour(double seed) {
		red = (int) MathHelper.clamp((seed + Math.random() * 300), 0, 255);
		green = (int) MathHelper.clamp((seed + Math.random() * 300), 0, 255);
		blue = (int) MathHelper.clamp((seed + Math.random() * 300), 0, 255);
		return  this;
	}

	public RainbowUtil cycleColors() {
		if (red == 0) {
			redUp = true;
		}
		if (red == 255) {
			redUp = false;
		}

		if (green == 0) {
			greenUp = true;
		}
		if (green == 255) {
			greenUp = false;
		}

		if (blue == 0) {
			blueUp = true;
		}
		if (blue == 255) {
			blueUp = false;
		}

		if (redUp) {
			red++;
		} else {
			red--;
		}

		if (greenUp) {
			green++;
		} else {
			green--;
		}

		if (blueUp) {
			blue++;
		} else {
			blue--;
		}
		return this;
	}

	public RainbowUtil cycleColoursCustomSpeed() {
		attempt++;
		if (attempt >= speed) {
			attempt = 0;
			cycleColors();
		}
		return this;
	}

	public RainbowUtil cycleColorsRandom() {

		if (red == 255 || red == 0) {

		} else if (getRandBool()) {
			red++;
		} else {
			red--;
		}


		if (green == 255 || green == 0) {

		} else if (getRandBool()) {
			green++;
		} else {
			green--;
		}

		if (blue == 255 || blue == 0) {

		} else if (getRandBool()) {
			blue++;
		} else {
			blue--;
		}
		return  this;
	}

	public RainbowUtil randomiseDirection() {
		double r = Math.random();
		if (r < 0.01) {
			double r1 = Math.random();
            blueUp = !(r1 < 0.5);

			double r2 = Math.random();
            greenUp = r2 < 0.5;

			double r3 = Math.random();
            redUp = r3 < 0.5;
		} else {
			return this;
		}
		return this;
	}

	private boolean getRandBool() {
		double r = Math.random();
        return r < 0.5;
	}

	public Color getCol() {
		return new Color(red, green, blue);
	}

	public RainbowUtil reseedColour(Color color) {
		this.red = color.getRed();
		this.green = color.getGreen();
		this.blue = color.getBlue();
		return this;
	}

}
