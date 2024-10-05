package nagoya.nikkou.pos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PosCommand implements CommandExecutor, TabCompleter {

    private final Pos plugin;

    public PosCommand(Pos plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player targetPlayer = null;

        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "使用方法: /pos [プレイヤー名]");
            return true;
        }

        if (args.length == 1) {
            targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage(ChatColor.RED + "そのプレイヤーは存在しません！");
                return true;
            }
        } else {
            if (sender instanceof Player) {
                targetPlayer = (Player) sender;
            } else {
                sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーのみ実行できます。");
                return true;
            }
        }

        Location loc = targetPlayer.getLocation();
        World world = loc.getWorld();
        String worldName = world != null ? world.getName() : "未知のワールド";

        String message = String.format(
                "%sの座標 [ワールド: %s]: X=%.2f, Y=%.2f, Z=%.2f",
                targetPlayer.getName(),
                worldName,
                loc.getX(),
                loc.getY(),
                loc.getZ()
        );

        Bukkit.broadcastMessage(ChatColor.GREEN + message);

        targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1));

        boolean sendXaeroWaypoint = plugin.getConfig().getBoolean("send-xaero-waypoint", false);

        if (sendXaeroWaypoint) {
            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();
            String dimension = world != null ? world.getEnvironment().name().toLowerCase() : "unknown";

            String xaeroMessage = String.format(
                    "xaero-waypoint:%s is here:S:%d:%d:%d:3:false:0:Internal-%s-waypoints",
                    targetPlayer.getName(),
                    x,
                    y,
                    z,
                    dimension
            );

            targetPlayer.sendMessage(xaeroMessage);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] Player) {
        List<String> completions = new ArrayList<>();

        if (Player.length == 1) {
            String partial = Player[0].toLowerCase();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(partial)) {
                    completions.add(player.getName());
                }
            }
        }

        return completions;
    }
}