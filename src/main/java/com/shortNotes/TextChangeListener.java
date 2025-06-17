package com.shortNotes;

// Interface for classes that want to be notified when the text of a NoteSave object changes
public interface TextChangeListener
{
    void onTextChanged(String newText);
}
