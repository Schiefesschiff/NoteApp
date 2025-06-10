package com.shortNotes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class SaveManager
{
    private static final String CONFIG_FILE_NAME = "config.json";

    private WindowConfig mainDefaultWindow;
    private WindowConfig noteDefaultWindow;

    private final String jsonArrayString;

    public SaveManager()
    {
        this.mainDefaultWindow = new WindowConfig(300, 500, -1, -1, false);
        this.noteDefaultWindow = new WindowConfig(200, 200, -1, -1, false);

        jsonArrayString = """
                [
                  {
                    "width": 300.0,
                    "height": 500.0,
                    "x": -1.0,
                    "y": -1.0,
                    "maximized": false
                  },
                  {
                    "width": 200.0,
                    "height": 200.0,
                    "x": -1.0,
                    "y": -1.0,
                    "maximized": false
                  },
                  {
                    "width": 800.0,
                    "height": 600.0,
                    "x": 100.0,
                    "y": 50.0,
                    "maximized": true
                  }
                ]
                """;
    }

    public SaveData Load()
    {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(CONFIG_FILE_NAME))
        {
            return gson.fromJson(reader, SaveData.class);
        } catch (FileNotFoundException e)
        {
            System.out.println("Speicherdatei '" + CONFIG_FILE_NAME + "' nicht gefunden. Es wird eine leere SaveData zurückgegeben.");

            return new SaveData(mainDefaultWindow);

        } catch (IOException e)
        {
            System.err.println("Fehler beim Laden der Daten: " + e.getMessage());
            e.printStackTrace();
            return null; // Im Fehlerfall null zurückgeben
        }
    }

    public void SaveConfigs(SaveData saveData)
    {
        if (saveData == null)
        {
            System.err.println("No SAveData receive");
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(CONFIG_FILE_NAME))
        {
            gson.toJson(saveData, writer);
            System.out.println("Daten erfolgreich in " + CONFIG_FILE_NAME + " gespeichert.");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
