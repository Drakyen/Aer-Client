package me.aerclient.visual.gui.screen.login;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.aerclient.implementation.utils.Utilities;
import net.minecraft.util.Session;
import net.minecraft.util.text.TextFormatting;

import java.net.Proxy;

//import com.mintyplays.minty.Minty;


public final class AltLoginThread extends Thread implements Utilities {

    private final String password;
    private String status;
    private final String username;

    public AltLoginThread(String username, String password) {
        super("Alt Login Thread");
        this.username = username;
        this.password = password;
        this.status = (Object) ((Object) TextFormatting.GRAY) + "Waiting...";
    }

    private Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException localAuthenticationException) {
            localAuthenticationException.printStackTrace();
            return null;
        }
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public void run() {
        if (this.password.equals("")) {
            minecraft.session = new Session(this.username, "", "", "mojang");
            this.status = (Object) ((Object) TextFormatting.GREEN) + "Logged in. (" + this.username + " - offline name)";
            return;
        }
        this.status = (Object) ((Object) TextFormatting.YELLOW) + "Logging in...";
        Session auth = this.createSession(this.username, this.password);
        if (auth == null) {
            this.status = (Object) ((Object) TextFormatting.RED) + "Login failed!";
        } else {
            this.status = (Object) ((Object) TextFormatting.GREEN) + "Logged in. (" + auth.getUsername() + ")";
            minecraft.session = auth;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

