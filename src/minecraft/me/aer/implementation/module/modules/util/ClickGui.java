package me.aer.implementation.module.modules.util;

import com.darkmagician6.eventapi.EventTarget;
import me.aer.Aer;
import me.aer.config.valuesystem.BooleanValue;
import me.aer.config.valuesystem.ColourValue;
import me.aer.config.valuesystem.ModeValue;
import me.aer.config.valuesystem.NumberValue;
import me.aer.implementation.module.base.Category;
import me.aer.implementation.module.base.Module;
import me.aer.injection.events.client.EventValueChanged;
import me.aer.visual.gui.click.ClickGuiUI;
import me.aer.visual.render.Fonts;
import me.aer.visual.style.minimal.MinimalStyle;
import me.aer.visual.style.modern.ModernStyle;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;


public class ClickGui extends Module {


    private ModeValue mode = new ModeValue("Style", "Gui style for Aer", "Modern", "Modern", "Minimal");
    private ModeValue font = new ModeValue("Font", "Font for Aer", "Default", "Default", "Plain", "Modern", "SciFi", "Mc");
    private ColourValue col = new ColourValue("Gui Colour", "The main colour for the clickgui", new Color(0.5f, 0.9f, 0.6f));
    private NumberValue<Float> alpha = new NumberValue<>("Alpha", "Alpha value for the clickgui", 0.8f, 0.5f, 1f, true);
    private BooleanValue blur = new BooleanValue("Blur", "Blurs the background", true);
    private BooleanValue sound = new BooleanValue("Sound", "Makes clicking the gui make sound", true);
    private BooleanValue tooltips = new BooleanValue("Tooltips", "Shows tooltips if you hover over a module or setting in the gui", true);
    private NumberValue<Float> tooltipDelay = new NumberValue<>("Delay", "How long it takes before tooltips appear [In seconds]", 0.3f, 0f, 3f, false);
    private NumberValue<Float> scrollSpeed = new NumberValue<>("Scroll Speed", "How fast the gui moves when scrolled [Multiplier]", 1f, 0f, 5f, false);

    private String currentMode;
    private String currentFont;

    public ClickGui() {
        super("ClickGui", Category.UTIL, "Gui!", Keyboard.KEY_RCONTROL);
    }

    public void setup() {
        Fonts.setFont(font.getValue());
        currentFont = font.getValue();
        currentMode = mode.getValue();
    }

    public void onEnable() {
        reloadGui();
        minecraft.displayGuiScreen(Aer.clickGui);
        this.setActiveState(false);
    }

    @EventTarget
    public void onValueUpdate(EventValueChanged event) {
        Aer.clickGui.setCol(new Color(col.getObject().getRed(), col.getObject().getGreen(), col.getObject().getBlue(), (int) (255 * alpha.getValue())));
        if (mode.getValue() != currentMode || blur.getObject() != Aer.clickGui.getBlurMode() || sound.getObject() != Aer.clickGui.getSoundMode()) {
            reloadGui();
            currentMode = mode.getValue();
        }
        if (font.getValue() != currentFont) {
            Fonts.setFont(font.getValue());
            currentFont = font.getValue();
        }
        Aer.clickGui.setTooltipDelay((int) (tooltipDelay.getValue() * 1000));
        Aer.clickGui.setShowTooltips(tooltips.getObject());
        Aer.clickGui.setScrollSpeed(scrollSpeed.getValue());
    }

	private void reloadGui() {
        int mx = Mouse.getX();
        int my = Mouse.getY();

        minecraft.displayGuiScreen(null);

        currentFont = font.getValue();
        currentMode = mode.getValue();

        if (mode.getValue().equals("Modern")) {
            Aer.clickGui = new ClickGuiUI(new ModernStyle("ModernStyle"));
        } else if (mode.getValue().equals("Minimal")) {
            Aer.clickGui = new ClickGuiUI(new MinimalStyle("MinimalStyle"));
        }

        Fonts.setFont(font.getValue());

        Aer.clickGui.setBlurMode(blur.getObject());
        Aer.clickGui.setSoundMode(sound.getObject());
        Aer.clickGui.setTooltipDelay((int) (tooltipDelay.getValue() * 1000));
        Aer.clickGui.setShowTooltips(tooltips.getObject());
        Aer.clickGui.setScrollSpeed(scrollSpeed.getValue());
        Aer.clickGui.setCol(new Color(col.getObject().getRed(), col.getObject().getGreen(), col.getObject().getBlue(), (int) (255 * alpha.getValue())));

        minecraft.displayGuiScreen(Aer.clickGui);

        Mouse.setCursorPosition(mx, my);
    }

    public Color getCol() {
        return new Color(col.getObject().getRed(), col.getObject().getGreen(), col.getObject().getBlue(), (int) (255 * alpha.getValue()));
    }

    public Color getColNoAlpha() {
        return col.getObject();
    }


}
