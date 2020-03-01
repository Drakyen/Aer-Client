package me.aerclient.module.modules.util;

import com.darkmagician6.eventapi.EventTarget;
import me.aerclient.Aer;
import me.aerclient.gui.styles.ModernStyle;
import me.aerclient.gui.styles.SciFiStyle;
import me.aerclient.injection.events.client.EventValueChanged;
import me.aerclient.module.base.Category;
import me.aerclient.module.base.Module;
import me.aerclient.render.render2D.font.Fonts;
import me.aerclient.config.valuesystem.BooleanValue;
import me.aerclient.config.valuesystem.ModeValue;
import me.aerclient.config.valuesystem.NumberValue;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;


public class ClickGui extends Module {

    private ModeValue mode = new ModeValue("Style", "Modern", "SciFi", "Modern");
    private ModeValue font = new ModeValue("Font", "Default", "Default", "Plain", "Modern", "SciFi", "Mc");
    private NumberValue<Float> red = new NumberValue<>("Red", 0.5f, 0.0f, 1f, true);
    private NumberValue<Float> green = new NumberValue<>("Green", 0.9f, 0.0f, 1f, true);
    private NumberValue<Float> blue = new NumberValue<>("Blue", 0.6f, 0.0f, 1f, true);
    private NumberValue<Float> alpha = new NumberValue<>("Alpha", 0.8f, 0.5f, 1f, true);
    private BooleanValue blur = new BooleanValue("Blur", true);
    private BooleanValue sound = new BooleanValue("Sound", true);

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
        this.setActiveState(false);
    }

    @EventTarget
    public void onValueUpdate(EventValueChanged event) {
        Aer.clickGui.setCol(new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), alpha.getValue().floatValue()));
        if (mode.getValue() != currentMode || blur.getObject() != Aer.clickGui.blurMode || sound.getObject() != Aer.clickGui.soundMode) {
            reloadGui();
            currentMode = mode.getValue();
        }
        if (font.getValue() != currentFont) {
            Fonts.setFont(font.getValue());
            currentFont = font.getValue();
        }
    }

	private void reloadGui() {
        int mx = Mouse.getX();
        int my = Mouse.getY();
        if (font.getValue() != currentFont) {
            Fonts.setFont(font.getValue());
            currentFont = font.getValue();
        }
        minecraft.displayGuiScreen(null);
        if (mode.getValue() == "SciFi") {
            Aer.clickGui = new me.aerclient.gui.ClickGui(new SciFiStyle());
        }
        if (mode.getValue() == "Modern") {
            Aer.clickGui = new me.aerclient.gui.ClickGui(new ModernStyle());
        }
        Aer.clickGui.blurMode = blur.getObject();
        Aer.clickGui.soundMode = sound.getObject();
        Aer.clickGui.setCol(new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), alpha.getValue().floatValue()));
        minecraft.displayGuiScreen(Aer.clickGui);
        Mouse.setCursorPosition(mx, my);
    }

    public Color getCol() {
        return new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), alpha.getValue().floatValue());
    }

    public Color getColNoAlpha() {
        return new Color(red.getValue().floatValue(), green.getValue().floatValue(), blue.getValue().floatValue(), 1f);
    }


}
