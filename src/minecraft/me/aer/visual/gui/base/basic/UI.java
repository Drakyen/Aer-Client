package me.aer.visual.gui.base.basic;


import me.aer.implementation.utils.Utilities;

/**
 * A interface with basic methods that a UI would want.
 */
public interface UI extends Utilities {

    void init();
    void render(int mouseX, int mouseY);
    void mouseClicked(int mouseX, int mouseY, int button);

    void mouseReleased(int mouseX, int mouseY, int button);

    void keyPressed(char key, int keycode);

    boolean hovered(int mouseX, int mouseY);
    UI setX(int x);
    UI setY(int y);
    int getWidth();
    int getHeight();
    int getX();
    int getY();

}
