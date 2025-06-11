package com.shortNotes;

record WindowConfig(
        double width,
        double height,
        double x,
        double y,
        boolean maximized
)
{
}

record NoteSave(
        WindowConfig windowConfig,
        String content
)
{
}