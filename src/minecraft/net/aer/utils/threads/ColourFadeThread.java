package net.aer.utils.threads;

import java.awt.*;

public class ColourFadeThread implements Runnable {

	private Color start;
	private Color end;
	private int time;
	private ColourFadeable out;

	public ColourFadeThread(Color start, Color end, int time, ColourFadeable output) {
		this.start = start;
		this.end = end;
		this.time = time;
		this.out = output;
		out.colOut = start;
	}


	@Override
	public void run() {
		double red = start.getRed();
		double green = start.getGreen();
		double blue = start.getBlue();
		double alpha = start.getAlpha();

		double redEnd = end.getRed();
		double greenEnd = end.getGreen();
		double blueEnd = end.getBlue();
		double alphaEnd = end.getAlpha();

		double redDif = (redEnd - red) / time;
		double greenDif = (greenEnd - green) / time;
		double blueDif = (blueEnd - blue) / time;
		double alphaDif = (alphaEnd - alpha) / time;

		while (time > 0) {
			time--;
			red += redDif;
			green += greenDif;
			blue += blueDif;
			alpha += alphaDif;
			out.colOut = new Color((int) red, (int) green, (int) blue, (int) alpha);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


	}

}
