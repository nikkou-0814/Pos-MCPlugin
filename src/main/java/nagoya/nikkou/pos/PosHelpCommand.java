package nagoya.nikkou.pos;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PosHelpCommand implements CommandExecutor {

    private final Pos plugin;

    public PosHelpCommand(Pos plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "=== PosPlugin Help ===");
        sender.sendMessage(ChatColor.GREEN + "/pos [playername]" + ChatColor.WHITE + " - 指定したプレイヤーの座標をチャットに送信し、発光効果を付与します。");
        sender.sendMessage(ChatColor.GREEN + "/pos" + ChatColor.WHITE + " - 自分の座標をチャットに送信し、発光効果を付与します。");
        sender.sendMessage(ChatColor.GREEN + "/poshelp" + ChatColor.WHITE + " - このヘルプメッセージを表示します。");
        sender.sendMessage(ChatColor.GOLD + "===========================");
        return true;
    }
}