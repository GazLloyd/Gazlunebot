package gazlloyd;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by Gareth Lloyd on 11/07/14.
 */
public class CommandHandler {

    private  Gazlunebot bot;

    public CommandHandler(Gazlunebot b) {
        bot = b;
    }

    public boolean parse(CommandSender sender, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            String[] help = {
                    "&c====&eGazlunebot help&c====",
                    "&eVarious admin commands for the bot",
                    "&c/gazlunebot [help]   &eThis page",
                    "&c/gazlunebot enable   &eTurns bot on",
                    "&c/gazlunebot disable   &eTurns bot off",
                    "&c/gazlunebot reload   &eReloads config file"
            };

            for (int i = 0; i < help.length; i++)
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',help[i]));
            return true;
        }

        if (args[0].equalsIgnoreCase("enable")) {
            if (Gazlunebot.cfg.enabled) {
                sender.sendMessage("Gazlunebot is already enabled!");
                return true;
            }
            Gazlunebot.cfg.setEnabled();
            bot.register();
            return true;
        }

        if (args[0].equalsIgnoreCase("disable")) {
            if (!Gazlunebot.cfg.enabled) {
                sender.sendMessage("Gazlunebot is already disabled!");
                return true;
            }
            Gazlunebot.cfg.setDisabled();
            bot.unregister();
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("Reloading Gazlunebot...");
            Gazlunebot.gazlunebot.reloadConfig();
            Config.cfg.load();
            sender.sendMessage("Reloaded Gazlunebot!");
            return true;
        }

        return false;

    }

}
