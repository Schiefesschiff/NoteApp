package com.shortNotes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages the loading and saving of application data (notes and window configurations)
 * to and from a JSON file. It uses the Gson library for serialization and deserialization.
 */
public class SaveManager
{
    private final String CONFIG_FILE_NAME = "config.json";

    public final WindowConfig mainDefaultWindow;
    public static  final WindowConfig NOTE_DEFAULT_WINDOW = new WindowConfig(200, 200, -1, -1, false);

    /**
     * Constructor initializes the default configuration for the main window.
     */
    public SaveManager()
    {
        this.mainDefaultWindow = new WindowConfig(300, 500, -1, -1, false);
    }

    /**
     * Loads the application data from the `config.json` file.
     * If the file is not found or an error occurs during loading,
     * it returns a `SaveData` object with default configurations.
     * @return A `SaveData` object containing the loaded data or default values.
     */
    public SaveData LoadData()
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
            System.err.println("Error loading the data: " + e.getMessage() + ". Using default data.");

            return new SaveData(mainDefaultWindow);
        }
    }

    /**
     * Saves the provided `SaveData` object to the `config.json` file.
     * The data is written in a human-readable, pretty-printed JSON format.
     * @param saveData The `SaveData` object containing all application state to be saved.
     */
    public void SaveData(SaveData saveData)
    {
        if (saveData == null)
        {
            System.err.println("No SaveData receive");
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
