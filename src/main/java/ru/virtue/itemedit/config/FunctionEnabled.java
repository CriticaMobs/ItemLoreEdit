package ru.virtue.itemedit.config;

public class FunctionEnabled
{
    private boolean aBoolean;
    private String name;

    public FunctionEnabled(String name)
    {
        aBoolean = Config.getStringBooleanMap ().get (name);
        this.name = name;
    }

    public boolean isaBoolean() {
        return Config.isLore () && aBoolean;
    }

    public String getName() {
        return name;
    }
}
