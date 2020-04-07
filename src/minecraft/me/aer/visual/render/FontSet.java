package me.aer.visual.render;

public class FontSet {

    public final String name;
    public final CustomFontRenderer small;
    public final CustomFontRenderer normal;
    public final CustomFontRenderer mid;
    public final CustomFontRenderer big;
    public final CustomFontRenderer massive;

    public FontSet(String name, CustomFontRenderer smallIn, CustomFontRenderer normalIn, CustomFontRenderer midIn, CustomFontRenderer bigIn, CustomFontRenderer massiveIn) {
        this.name = name;
        small = smallIn;
        normal = normalIn;
        mid = midIn;
        big = bigIn;
        massive = massiveIn;
    }


}
