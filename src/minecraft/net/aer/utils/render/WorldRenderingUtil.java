package net.aer.utils.render;

public class WorldRenderingUtil {

    private static int transparencyList = 0;
    private static int cullingList = 0;

    private static boolean renderBlocksTransparent = false;
    private static boolean allowCulling = true;

    public static boolean renderBlocksTransparent() {
        return renderBlocksTransparent;
    }


    public static boolean allowCulling() {
        return allowCulling;
    }

    public static void setBlocksTransparent(boolean transparent, boolean force) {
        if (force) {
            renderBlocksTransparent = transparent;
            if (transparent) {
                transparencyList++;
            } else {
                transparencyList = 0;
            }
        } else {
            if (transparent = false) {
                if (transparencyList <= 1) {
                    transparencyList = 0;
                    renderBlocksTransparent = false;
                } else {
                    transparencyList--;
                }
            } else {
                transparencyList++;
                renderBlocksTransparent = true;
            }
        }
    }

    public static void setCulling(boolean culling, boolean force) {
        if (force) {
            allowCulling = culling;
            if (!culling) {
                cullingList++;
            } else {
                cullingList = 0;
            }
        } else {
            if (culling = true) {
                if (cullingList <= 1) {
                    cullingList = 0;
                    allowCulling = true;
                } else {
                    cullingList--;
                }
            } else {
                cullingList++;
                allowCulling = false;
            }
        }
    }

}

