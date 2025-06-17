package com.shortNotes;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the persistent data model for a single note.
 * This class holds the note's text content and its associated window configuration.
 * It is designed to be easily serialized and deserialized (e.g., using Gson).
 * It also implements an observer pattern for text content changes.
 */
public class NoteSave
{
    // transient keyword ensures these fields are not serialized/deserialized by Gson.
    private transient List<TextChangeListener> listeners = new ArrayList<>();

    private WindowConfig windowConfig;
    private String content;
    private transient boolean isOpen;


    //Constructor for creating a NoteSave instance.
    public NoteSave(
            WindowConfig windowConfig,
            String content,
            boolean isOpen
    )
    {
        this.windowConfig = windowConfig;
        this.content = content;
        this.isOpen = isOpen;
    }

    /**
     * Initializes transient fields after deserialization.
     * This is crucial because `transient` fields are skipped during deserialization,
     * so they need to be re-initialized manually to prevent NullPointerExceptions.
     */
    public void init()
    {
        listeners = new ArrayList<>();
    }

    // --- Getters and Setters for NoteSave properties ---

    public WindowConfig getWindowConfig()
    {
        return windowConfig;
    }

    public void setWindowConfig(WindowConfig windowConfig)
    {
        this.windowConfig = windowConfig;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
        notifyListeners();
    }

    public boolean isOpen()
    {
        return isOpen;
    }

    public void setOpen(boolean open)
    {
        isOpen = open;
    }

    public void addTextChangeListener(TextChangeListener listener)
    {
        listeners.add(listener);
    }

    public void removeTextChangeListener(TextChangeListener listener)
    {
        if (!listeners.contains(listener)) return;
        listeners.remove(listener);
    }

    /**
     * Notifies all registered `TextChangeListener`s that the note's content has changed.
     */
    private void notifyListeners()
    {
        for (TextChangeListener listener : listeners)
        {
            listener.onTextChanged(content);
        }
    }

}
