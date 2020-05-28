package ru.virtue.itemedit.config;

public enum FunctionConfig
{
    INVENTORY_OPEN(new FunctionEnabled ("inventory-open")),
    INVENTORY_CLOSE(new FunctionEnabled ("inventory-close")),
    INVENTORY_DROP(new FunctionEnabled ("inventory-drop")),
    PLAYER_COMMAND(new FunctionEnabled ("player-command"));

    private FunctionEnabled name;

    private FunctionConfig(FunctionEnabled enabled)
    {
        name=enabled;
    }

    public FunctionEnabled getName() {
        return name;
    }

    public boolean isEnable()
    {return name.isaBoolean ();}

    public String nameString()
    {return name.getName ();}
}
