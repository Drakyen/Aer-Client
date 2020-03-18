package me.aerclient.implementation.utils;

import me.aerclient.implementation.utils.exploits.ClipUtil;
import me.aerclient.visual.render.feather.util.TextureUtils;
import me.aerclient.visual.render.render2D.RenderUtils2D;
import me.aerclient.visual.render.render3D.RenderUtils3D;
import net.minecraft.client.Minecraft;

public interface Utilities {
    RenderUtils2D rend2D = new RenderUtils2D();
    RenderUtils3D rend3D = new RenderUtils3D();
    ClipUtil clipUtil = new ClipUtil();
    Minecraft minecraft = Minecraft.getMinecraft();
    TextureUtils textureUtils = new TextureUtils();
}
