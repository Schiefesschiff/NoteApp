package com.shortNotes;

import java.util.ArrayList;
import java.util.List;

public class NoteSave
{
    private transient  List<TextChangeListener> listeners = new ArrayList<>();

    private WindowConfig windowConfig;
    private String content;
    private transient boolean isOpen;

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

    public void init()
    {
        listeners = new ArrayList<>();
    }

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

    private void notifyListeners()
    {
        for (TextChangeListener listener : listeners)
        {
            listener.onTextChanged(content);
        }
    }

}
