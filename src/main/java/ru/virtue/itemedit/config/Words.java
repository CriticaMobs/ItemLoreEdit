package ru.virtue.itemedit.config;

import ru.virtue.itemedit.ItemLoreEdit;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Words
{
    private URL url;
    private File file;
    private boolean load;

    public Words(ItemLoreEdit edit)
    {
        this.load = edit.getConfigFile ().isWords ();
        file = new File (edit.getDataFolder (), "words.yml");
        if(load)
        {
            loadFile ();
        }
    }

    public void loadFile()
    {
        if(file.exists ()) return;
        new Thread (() -> {
            try {
                url = new URL ("https://my-files.su/Save/k1qt61/RUS.txt");
                url.openConnection ().connect ();
                InputStream in = url.openStream();
                Files.copy(in, file.toPath (), StandardCopyOption.REPLACE_EXISTING);
                in.close ();
            }catch (IOException ex)
            {
                Logger.getGlobal ().severe ("please connected to internet");
            }
        }).start ();
    }

    public List<String> getStrings()
    {
        List<String> sb = new ArrayList<> ();
        try {
            BufferedReader reader = new BufferedReader (new FileReader (file));
            reader.lines ().filter (string -> !string.isEmpty ()).forEach (sb::add);
        }catch (Exception ex)
        { ex.printStackTrace (); }
        return sb;
    }

    public boolean isLoad() {
        return load;
    }
}
