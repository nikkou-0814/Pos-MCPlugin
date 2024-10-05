package nagoya.nikkou.pos;

import org.bukkit.plugin.java.JavaPlugin;

public final class Pos extends JavaPlugin {

    private static Pos instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        this.getCommand("pos").setExecutor(new PosCommand(this));
        this.getCommand("pos").setTabCompleter(new PosCommand(this));

        this.getCommand("poshelp").setExecutor(new PosHelpCommand(this));

        getLogger().info("Posプラグインが有効化されました。");
    }

    @Override
    public void onDisable() {
        getLogger().info("Posプラグインが無効化されました。");
    }

    public static Pos getInstance() {
        return instance;
    }
}