package me.aer.implementation.utils;

import me.aer.implementation.utils.exploits.AntikickUtil;
import me.aer.implementation.utils.exploits.ClipUtil;
import me.aer.implementation.utils.player.BlockUtil;
import me.aer.implementation.utils.player.RotationUtil;
import me.aer.visual.render.RenderUtils;
import me.aer.visual.render.feather.util.TextureUtils;
import net.minecraft.client.Minecraft;

public interface Utilities {
    RenderUtils rend = new RenderUtils();
    ClipUtil clipUtil = new ClipUtil();
    Minecraft minecraft = Minecraft.getMinecraft();
    TextureUtils textureUtils = new TextureUtils();
    AntikickUtil antiKickUtil = new AntikickUtil();
    BlockUtil blockUtil = new BlockUtil();
    RotationUtil rotationUtil = new RotationUtil();
}
