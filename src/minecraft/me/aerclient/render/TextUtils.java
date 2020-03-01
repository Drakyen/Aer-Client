package me.aerclient.render;

import me.aerclient.render.render2D.CustomFontRenderer;

import java.util.ArrayList;

public class TextUtils {

    public static ArrayList<String> splitLines(CustomFontRenderer font, String toSplit, char splitter, int splitWidth){
        //ArrayList to output
        ArrayList<String> toDraw = new ArrayList<>();
        //All "words" as split by the splitter char.
        String[] lines = toSplit.split(String.valueOf(splitter));
        //A temporary string that's used to keep track of the current line.
        String split = "";
        //Int that's used to track where in the list of words we are
        int index = 0;
        //Loops through all words and concatenates them into lines that do not go over the splitWidth
        for(String s : lines){
            index++;
            //Adds the current word to the current line if it doesn't got over the splitWidth limit.
            if(font.getStringWidth(split.concat(s + splitter)) < splitWidth) {
                split = split.concat(s + splitter);
            }
            //Else, adds the current line to the output, and resets it to the current word.
            else{
                toDraw.add(split);
                split = splitter + s + splitter;
            }
            //If we've not reached the last word, we skip back to the start here
            if(index != lines.length) {
                continue;
            }
            //If we are at the final word, we add the current line, no matter whether it's hit the length or not. This prevents words being dropped off of the end.
            toDraw.add(split);
        }
        //Returns the lines.
        return toDraw;
    }



}
