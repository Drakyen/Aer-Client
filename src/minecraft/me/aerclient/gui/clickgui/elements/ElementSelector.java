package me.aerclient.gui.clickgui.elements;

import me.aerclient.Aer;
import me.aerclient.gui.GuiStyle;
import me.aerclient.config.valuesystem.ModeValue;
import me.aerclient.config.valuesystem.Value;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class ElementSelector extends Element {

    public boolean extended = false;

    private boolean extendMode = true;

    public ElementSelector(Value v, ModuleButton parent, GuiStyle style, boolean extendMode) {
        super(v, parent, style);
        this.extendMode = extendMode;
    }

    public void drawScreen(int x, int y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;

        this.hovered = hovered(mouseX, mouseY);

        style.drawElement(this);

        if (isHovered()) {
            hoverTimer++;
            if (hoverTimer >= style.getHoverTime()) {
                style.drawToolTip("null", mouseX, mouseY);
            }
        } else {
            hoverTimer = 0;
        }

    }

    public void onMouseClicked(int mouseX, int mouseY, int button) {
        if (parent.getParent().getParent().allowAction(parent.getParent(), mouseX, mouseY)) {
            if (extendMode) {
                if (hovered(mouseX, mouseY)) {
                    this.extended = !this.extended;
                    if (parent.getParent().getParent().soundMode) {
                        minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    }
                } else if (getHoveredOption(mouseX, mouseY) != "" && this.extended) {
                    this.setting.setObject(getHoveredOption(mouseX, mouseY));
                    if (parent.getParent().getParent().soundMode) {
                        minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    }
                }
            } else {
                if (hovered(mouseX, mouseY)) {
                    if (button == 0) {
                        this.setting.setObject(nextMode(((ModeValue) this.setting).getModes(), ((ModeValue) this.setting).getValue()));
                        if (parent.getParent().getParent().soundMode) {
                            minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                        }
                    } else if (button == 1) {
                        this.setting.setObject(previousMode(((ModeValue) this.setting).getModes(), ((ModeValue) this.setting).getValue()));
                        if (parent.getParent().getParent().soundMode) {
                            minecraft.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                        }
                    }
                }
            }
        }

    }

    private String nextMode(String[] modes, String mode) {
        int index = 1;
        for (String string : modes) {
            if (string.equals(mode)) {
                break;
            } else {
                index++;
            }
        }
        if (index == modes.length) {
            return modes[0];
        } else {
            return modes[index];
        }
    }

    private String previousMode(String[] modes, String mode) {
        int index = 1;
        for (String string : modes) {
            if (string.equals(mode)) {
                break;
            } else {
                index++;
            }
        }
        if (index == 1) {
            return modes[modes.length - 1];
        } else {
            return modes[index - 2];
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void keyTyped(char typedChar, int key) {

    }

    @Override
    public void updateCols() {

    }

    private String getHoveredOption(int mouseX, int mouseY) {
        int o = 0;
        for (String s : ((ModeValue) this.setting).getModes()) {
            o += 16;
            if (hovered(mouseX, mouseY - o)) {
                return s;
            }
        }
        return "";
    }

    @Override
    public boolean hovered(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }


}


