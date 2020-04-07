package me.aer.visual.gui.base;

import com.darkmagician6.eventapi.EventManager;
import me.aer.implementation.utils.Utilities;
import me.aer.implementation.utils.world.TimerUtil;
import me.aer.visual.gui.base.basic.BasicGuiElement;
import me.aer.visual.gui.base.basic.UI;
import me.aer.visual.render.Fonts;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class EditableTextUI extends BasicGuiElement implements Utilities {

    boolean mouseDragging = false;
    private boolean editing = false;
    private boolean selected = false;
    private int cursorPos = 0;
    private int cursorIndex = 0;
    private int maxStringLength;
    private int defaultCol, selectedCol, cursorCol;
    private String text;
    private int selectedStartPos = 0;
    private int selectedEndPos = 0;
    private int selectedEndIndex = 0;
    private int lastClickedPosX = 0;
    private int lastClickedPosY = 0;
    private boolean blinking = false;
    private TimerUtil cursorTimer = new TimerUtil();
    private TimerUtil keyRepeatTimer1 = new TimerUtil();
    private TimerUtil keyRepeatTimer2 = new TimerUtil();
    private int lastPressedKeyCode = 0;
    private char lastPressedKey = ' ';
    private boolean edited = false;

    public EditableTextUI(int xIn, int yIn, int maxStringLengthIn, int defaultColIn, int selectedColIn, int cusorColIn, String textIn) {
        super(xIn, yIn, 0, 0);
        this.cursorCol = cusorColIn;
        this.defaultCol = defaultColIn;
        this.maxStringLength = maxStringLengthIn;
        this.selectedCol = selectedColIn;
        this.text = textIn;
        EventManager.register(this);
    }

    private void handleKeyboardRepeats() {
        if (Keyboard.isKeyDown(lastPressedKeyCode) && keyRepeatTimer1.delay(300)) {
            blinking = true;
            if (keyRepeatTimer2.delay(50)) {
                keyPressed(lastPressedKey, lastPressedKeyCode);
                keyRepeatTimer2.reset();
                cursorTimer.reset();
            }
        } else if (!Keyboard.isKeyDown(lastPressedKeyCode)) {
            keyRepeatTimer1.reset();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void render(int mouseX, int mouseY) {
        handleKeyboardRepeats();
        if (mouseDragging) {
            handleMouseDragging(mouseX, mouseY);
        }

        rend.drawString(Fonts.normal, text, x, y, defaultCol, false);

        if (editing && cursorTimer.delay(300)) {
            blinking = !blinking;
            cursorTimer.reset();
        }
        if (blinking && editing && !selected) {
            rend.drawVerticalLine(cursorPos, y, (int) (y + Fonts.normal.getStringHeight("T")), cursorCol);
        }
        if (editing && selected) {
            if (selectedStartPos > selectedEndPos) {
                int temp = selectedStartPos;
                selectedStartPos = selectedEndPos;
                selectedEndPos = temp;
            }
            rend.drawRect(selectedStartPos, y - 1, selectedEndPos, y + Fonts.normal.getStringHeight("T") + 1, selectedCol);
        }
    }

    private void handleMouseDragging(int mouseX, int mouseY) {
        if (nearestCursorPos(mouseX, mouseY, true) != cursorIndex) {
            selected = true;
            selectedStartPos = (int) (x + Fonts.normal.getStringWidth(text.substring(0, cursorIndex)) - 1);
            selectedEndPos = (int) (x + Fonts.normal.getStringWidth(text.substring(0, nearestCursorPos(mouseX, mouseY, true))) - 1);
            selectedEndIndex = nearestCursorPos(mouseX, mouseY, true);
        } else {
            selected = false;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (!textHovered(mouseX, mouseY)) {
            editing = false;
            return;
        }
        lastClickedPosX = mouseX;
        lastClickedPosY = mouseY;
        if (button == 0) {
            editing = true;
            blinking = true;
            mouseDragging = true;
            cursorPos = nearestCursorPos(mouseX, mouseY, false);
            cursorIndex = nearestCursorPos(mouseX, mouseY, true);
        }
    }

    private boolean textHovered(int mouseX, int mouseY) {
        if (text.length() != 0) {
            return mouseX > x - 1 && mouseX < x + Fonts.normal.getStringWidth(text) + 1 && mouseY > y - 1 && mouseY < y + Fonts.normal.getStringHeight(text) + 1;
        } else {
            int widtho = 0;
            for (int i = 0; i < maxStringLength; i++) {
                widtho += Fonts.normal.getStringWidth("T");
            }
            return mouseX > x - 1 && mouseX < x + widtho + 1 && mouseY > y - 1 && mouseY < y + Fonts.normal.getStringHeight("T") + 1;
        }
    }

    private int nearestCursorPos(int mouseX, int mouseY, boolean returnIndex) {
        int cursorIndex = 0;
        int offset;
        int pos = x - 1;
        int distanceToMouse = mouseX - x;
        assert distanceToMouse > 0;  //This should never be negative.

        if (text.length() == 0) {
            return x;
        }

        String s = text.substring(0, 1);
        for (char c : text.toCharArray()) {
            offset = (int) (Fonts.normal.getStringWidth(s));
            if (mouseX - (pos + offset) < distanceToMouse && mouseX - (pos + offset) > 0) {
                distanceToMouse = mouseX - (pos + offset);
                cursorIndex++;
            }
            s = s.concat("" + c);
        }
        if (returnIndex) {
            return cursorIndex;
        }
        return (int) (x + Fonts.normal.getStringWidth(text.substring(0, cursorIndex)) - 1);
    }


    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        if (button == 0) {
            mouseDragging = false;
        }
    }

    @Override
    public void keyPressed(char key, int keycode) {

        if (keycode != lastPressedKeyCode) {
            lastPressedKey = key;
            lastPressedKeyCode = keycode;
            keyRepeatTimer1.reset();
        }

        if (!editing) {
            return;
        }

        if ((keycode == Keyboard.KEY_DELETE || keycode == Keyboard.KEY_BACK) && cursorIndex > 0) {
            if (selected) {
                int index = text.indexOf(getSelectionText());
                if (index != -1) {
                    String prefix = text.substring(0, index);
                    String suffix = text.substring(index + getSelectionText().length());
                    cursorIndex -= getSelectionText().length();
                    if (cursorIndex < 0) {
                        cursorIndex = 0;
                    }
                    text = prefix.concat(suffix);
                    selected = false;
                    edited = true;
                    updateCursorPos();
                }
            } else {
                String prefix = text.substring(0, cursorIndex - 1);
                String suffix = text.substring(cursorIndex);
                text = prefix.concat(suffix);
                cursorIndex--;
                edited = true;
                updateCursorPos();
            }
        } else if (keycode == Keyboard.KEY_LEFT && cursorIndex > 0) {
            if (selected) {
                selected = false;
                return;
            }
            cursorIndex--;
            updateCursorPos();
        } else if (keycode == Keyboard.KEY_RIGHT && cursorIndex < text.length()) {
            if (selected) {
                selected = false;
                return;
            }
            cursorIndex++;
            updateCursorPos();
        } else if (keycode == Keyboard.KEY_LEFT || keycode == Keyboard.KEY_RIGHT) {
            selected = false;
        } else if (GuiScreen.isCtrlKeyDown() && keycode == Keyboard.KEY_C && selected) {
            GuiScreen.setClipboardString(getSelectionText());
        } else if (GuiScreen.isCtrlKeyDown() && keycode == Keyboard.KEY_X && selected) {
            GuiScreen.setClipboardString(getSelectionText());
            String prefix;
            String suffix;
            if (selectedEndIndex < cursorIndex) {
                prefix = text.substring(0, selectedEndIndex);
                suffix = text.substring(cursorIndex);
            } else {
                prefix = text.substring(0, cursorIndex);
                suffix = text.substring(selectedEndIndex);
            }
            String check = prefix.concat(suffix);
            if (check.length() <= maxStringLength) {
                cursorIndex -= getSelectionText().length();
                if (cursorIndex < 0) {
                    cursorIndex = 0;
                }
                text = check;
                selected = false;
                edited = true;
                updateCursorPos();
            }
        } else if (GuiScreen.isCtrlKeyDown() && keycode == Keyboard.KEY_V) {
            String add = GuiScreen.getClipboardString();
            String prefix;
            String suffix;
            if (selected) {
                if (selectedEndIndex < cursorIndex) {
                    prefix = text.substring(0, selectedEndIndex);
                    suffix = text.substring(cursorIndex);
                } else {
                    prefix = text.substring(0, cursorIndex);
                    suffix = text.substring(selectedEndIndex);
                }
                String check = prefix.concat(add).concat(suffix);
                if (check.length() <= maxStringLength) {
                    text = check;
                    selected = false;
                    edited = true;
                    updateCursorPos();
                }
            } else if (text.concat(add).length() <= maxStringLength) {
                text = text.concat(add);
                cursorIndex += add.length();
                edited = true;
                updateCursorPos();
            }
        } else if (text.length() < maxStringLength && ChatAllowedCharacters.isAllowedCharacter(key)) {
            String prefix = text.substring(0, cursorIndex);
            String suffix = text.substring(cursorIndex);
            text = prefix.concat("" + key).concat(suffix);
            cursorIndex++;
            edited = true;
            updateCursorPos();
        }
    }

    private void updateCursorPos() {
        if (text.length() > 0) {
            cursorPos = (int) (x + Fonts.normal.getStringWidth(text.substring(0, cursorIndex)) - 1);
        } else {
            cursorPos = x;
        }
    }

    private String getSelectionText() {
        int start = cursorIndex;
        int end = selectedEndIndex;
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }
        return text.substring(start, end);
    }

    @Override
    public void scrolled(int amount) {

    }

    public void setCursorCol(int col) {
        cursorCol = col;
    }

    public void setDefaultCol(int col) {
        defaultCol = col;
    }

    public void setSelectedCol(int col) {
        selectedCol = col;
    }

    public String getText() {
        return text;
    }

    public void setText(String textIn) {
        editing = false;
        edited = false;
        selected = false;
        mouseDragging = false;
        text = textIn;
    }

    public boolean isEditing() {
        return editing;
    }

    public boolean edited() {
        if (edited && !editing) {
            edited = false;
            return true;
        }
        return false;
    }

    @Override
    public UI setX(int xIn) {
        super.setX(xIn);
        updateCursorPos();
        return this;
    }

    @Override
    public UI setY(int yIn) {
        super.setY(yIn);
        updateCursorPos();
        return this;
    }
}
