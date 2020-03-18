package me.aerclient.visual.gui.screen;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.google.common.collect.Lists;
import me.aerclient.Aer;
import me.aerclient.implementation.utils.Utilities;
import me.aerclient.implementation.utils.world.TimerUtil;
import me.aerclient.injection.events.render.EventGuiOpened;
import me.aerclient.visual.gui.screen.login.GuiAltLogin;
import me.aerclient.visual.render.RainbowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

public class AerGuiMenu extends GuiScreen implements Utilities {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private static final RainbowUtil rainbow = new RainbowUtil().reseedColour(new Color(0xFF7A00));
    private static final TimerUtil timer = new TimerUtil();

    /** Counts the number of screen updates. */
    private final float updateCounter;

    /** The splash message. */
    private String splashText;

    /**
     * The Object object utilized as a thread lock when performing non thread-safe operations
     */
    private final Object threadLock = new Object();
    public static final String MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";

    /** Width of openGLWarning2 */
    private int openGLWarning2Width;

    /** Width of openGLWarning1 */
    private int openGLWarning1Width;

    /** Left x coordinate of the OpenGL warning */
    private int openGLWarningX1;

    /** Top y coordinate of the OpenGL warning */
    private int openGLWarningY1;

    /** Right x coordinate of the OpenGL warning */
    private int openGLWarningX2;

    /** Bottom y coordinate of the OpenGL warning */
    private int openGLWarningY2;

    /** OpenGL graphics card warning. */
    private String openGLWarning1;

    /** OpenGL graphics card warning. */
    private String openGLWarning2;

    /** Link to the Mojang Support about minimum requirements */
    private String openGLWarningLink;
    private static final ResourceLocation SPLASH_TEXTS = new ResourceLocation("texts/splashes.txt");

    private int LOGO;

    public AerGuiMenu() {
        super();
        EventManager.register(this);
        this.openGLWarning2 = MORE_INFO_TEXT;
        this.splashText = "missingno";
        IResource iresource = null;
        LOGO = rend2D.loadTexture512("aerassets/logo.png");

        try {
            List<String> list = Lists.newArrayList();
            iresource = Minecraft.getMinecraft().getResourceManager().getResource(SPLASH_TEXTS);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null) {
                s = s.trim();

                if (!s.isEmpty()) {
                    list.add(s);
                }
            }

            if (!list.isEmpty()) {
                while (true) {
                    this.splashText = list.get(RANDOM.nextInt(list.size()));

                    if (this.splashText.hashCode() != 125780783) {
                        break;
                    }
                }
            }
        } catch (IOException var8) {
        } finally {
            IOUtils.closeQuietly(iresource);
        }

        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1");
            this.openGLWarning2 = I18n.format("title.oldgl2");
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        int j = this.height / 4 + 48;

        this.addSingleplayerMultiplayerButtons(j, 24);

        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, j + 72 + 12, 98, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, j + 72 + 12, 98, 20, I18n.format("menu.quit")));

        synchronized (this.threadLock)
        {
            this.openGLWarning1Width = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.openGLWarning2Width = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
            this.openGLWarningX1 = (this.width - k) / 2;
            this.openGLWarningY1 = (this.buttonList.get(0)).yPosition - 24;
            this.openGLWarningX2 = this.openGLWarningX1 + k;
            this.openGLWarningY2 = this.openGLWarningY1 + 24;
        }

        this.mc.setConnectedToRealms(false);
    }

    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
    {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, "Lonely fuck"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, "Multiplayer"));
        this.buttonList.add(new GuiButton(500, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, "Login"));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiWorldSelection(this));
        }

        if (button.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 4)
        {
            this.mc.shutdown();
        }

        if (button.id == 500)
        {
            this.mc.displayGuiScreen(new GuiAltLogin(this));
        }
    }

    public void confirmClicked(boolean result, int id)
    {
        if (id == 13) {
            if (result) {
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop").invoke(null);
                    oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
                } catch (Throwable throwable1) {
                    LOGGER.error("Couldn't open link", throwable1);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        rainbow.cycleColors();
        if (timer.delay(1000)) {
            rainbow.cycleColorsRandom();
            timer.reset();
        }
        ScaledResolution scaledRes = new ScaledResolution(minecraft);
        GlStateManager.disableAlpha();
        minecraft.getTextureManager().bindTexture(new ResourceLocation("aerassets/background.png"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), scaledRes.getScaledHeight());
        GlStateManager.enableAlpha();
        int j = this.width / 2 - 50;


        GL11.glBindTexture(GL11.GL_TEXTURE_2D, LOGO);

        rend2D.drawRect(j + 20, (height / 16) + 30, j + 80, (height / 16) + 70, rainbow.getRGB());
        rend2D.enableBlending();
        drawModalRectWithCustomSizedTexture(j, height / 16, 0F, 0.0F, 100, 100, 100F, 100F);
        rend2D.disableBlending();

        this.drawString(this.fontRendererObj, "Aer " + Aer.clientVersion, 2, this.height - 10, -1);
        this.drawString(this.fontRendererObj, "Free, open source client!", this.width - fontRendererObj.getStringWidth("Free, open source client!"), this.height - 10, -1);

        if (mouseX > this.width - fontRendererObj.getStringWidth("Free, open source client!") && mouseY > this.height - 10 && Mouse.isInsideWindow()) {
            drawRect(this.width - fontRendererObj.getStringWidth("Free, open source client!"), this.height - 2, this.width - 2, this.height - 1, -1);
        }

        if (mouseX < fontRendererObj.getStringWidth("Aer " + Aer.clientVersion) && mouseY > height - 10 && Mouse.isInsideWindow()) {
            drawRect(2, this.height - 2, fontRendererObj.getStringWidth("Aer " + Aer.clientVersion), this.height - 1, -1);
        }

        if (this.openGLWarning1 != null && !this.openGLWarning1.isEmpty()) {
            drawRect(this.openGLWarningX1 - 2, this.openGLWarningY1 - 2, this.openGLWarningX2 + 2, this.openGLWarningY2 - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.openGLWarningX1, this.openGLWarningY1, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.openGLWarning2Width) / 2, (this.buttonList.get(0)).yPosition - 12, -1);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        synchronized (this.threadLock)
        {
            if (!this.openGLWarning1.isEmpty() && !StringUtils.isNullOrEmpty(this.openGLWarningLink) && mouseX >= this.openGLWarningX1 && mouseX <= this.openGLWarningX2 && mouseY >= this.openGLWarningY1 && mouseY <= this.openGLWarningY2)
            {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }

        if (mouseX > this.width - fontRendererObj.getStringWidth("Free, open source client!") && mouseY > this.height - 10 && Mouse.isInsideWindow()) {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/Drakyen/Aer-Client").toURI());
            } catch (Exception e) {
            }
        } else if (mouseX < fontRendererObj.getStringWidth("Aer " + Aer.clientVersion) && mouseY > height - 10 && Mouse.isInsideWindow()) {
            try {
                Desktop.getDesktop().browse(new URL("http://aerclient.me").toURI());
            } catch (Exception e) {
            }
        }
    }

    @EventTarget
    public void aerMainMenuListener(EventGuiOpened event){
        if(event.gui instanceof GuiMainMenu || event.gui == this) {
            event.gui = this;
            double rand = Math.random();
            if (rand < 0.16) {
                rainbow.reseedColour(new Color(0xFF007A));
            } else if (rand < 0.32) {
                rainbow.reseedColour(new Color(0xFF7A00));
            } else if (rand < 0.48) {
                rainbow.reseedColour(new Color(0x00FF7A));
            } else if (rand < 0.64) {
                rainbow.reseedColour(new Color(0x7AFF00));
            } else if (rand < 0.72) {
                rainbow.reseedColour(new Color(0x007AFF));
            } else {
                rainbow.reseedColour(new Color(0x7A00FF));
            }
        }
    }
}
