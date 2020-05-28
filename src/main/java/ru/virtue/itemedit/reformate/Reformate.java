package ru.virtue.itemedit.reformate;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Reformate
{
    private StringBuilder sb;
    private String newLine, symbols, lore;
    private int restriction;

    public Reformate(List<String> lore)
    {
        this.sb = new StringBuilder ();
        lore.forEach (s -> {
            sb.append (s.trim ()).append (" ");
        });
        this.lore = sb.toString ().trim ();
        sb.setLength (0);
    }

    public Reformate setStringNewLine(String string)
    {
        this.newLine = string;
        return this;
    }

    public Reformate setSymbolsNoLength(String symbols)
    {
        this.symbols = symbols;
        return this;
    }

    public Reformate setRestriction(int restriction)
    {
        this.restriction = restriction;
        return this;
    }

    public List<String> build()
    {
        List<String> result = new ArrayList<> ();
        String[] arrWords = lore.split(" ");
        int index = 0;
        int length = arrWords.length;
        String lastColor = "";
        while (index != length) {
            String word = arrWords[index];
            String lastWord = sb.toString().trim();
            int sbLength = getLength (lastWord)+1;
            int wordLength = getLength (lastColor + word);
            if(word.startsWith (colorLine (newLine))) {
                if (sb.length () != 0) {
                    result.add (lastWord);
                    sb.setLength (0);
                }
                word = word.replaceFirst (newLine, "").replaceFirst (colorLine (newLine), "");
                sb.append (colorLine (newLine)).append (lastColor).append (word).append (" ");
                index++;
            } else if ((sbLength + wordLength) <= restriction) {
                if(sb.length () == 0 && !lastColor.equals (""))
                { sb.append (lastColor); }
                sb.append(word).append(" ");
                index++;
            } else if(wordLength > restriction){
                result.add (lastWord);
                sb.setLength (0);
                result.add(lastColor + word);
                index++;
            } else {
                lastColor = ChatColor.getLastColors (lastWord);
                result.add(lastWord);
                sb.setLength (0);
            }
        }
        if (sb.length() > 0) {
            result.add(sb.toString());
            sb.setLength (0);
        }
        return result;
    }

    public String colorLine(String st)
    {
        return ChatColor.translateAlternateColorCodes ('&', st);
    }

    public int getLength(String string)
    {
        string = ChatColor.stripColor (string).trim ();
        int length = string.length ();
        if(string.contains (newLine))
        {
            length -= newLine.length ();
        }
        char[] chars = string.toCharArray ();
        if(symbols.length () > 0)
        {
            for (int index = 0; index < chars.length; index++)
            {
                char c = chars[index];
                char[] charsS = symbols.toCharArray ();
                for(int indexS = 0; indexS < charsS.length; indexS++)
                {
                    char cS = charsS[indexS];
                    if(c == cS)
                    { length--; }
                }
            }
        }
        return length;
    }
}
