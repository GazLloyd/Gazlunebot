package gazlloyd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by Gareth Lloyd on 11/07/14.
 */
public final class Gazlunebot extends JavaPlugin {

    private CommandHandler cmdp;
    public static Logger log;
    public static Gazlunebot gazlunebot;
    public static Config cfg;

    @Override
    public void onEnable() {
        gazlunebot = this;
        log = getLogger();
        log.info("Enabling Gazlunebot...");
        this.saveDefaultConfig();
        cmdp = new CommandHandler(this);
        cfg = new Config(this);
        if (cfg.enabled)
            register();
    }

    @Override
    public void onDisable() {
        log.info("Disabling Gazlunebot");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gazlunebot")
                || cmd.getName().equalsIgnoreCase("elunebot")) {

            if (sender instanceof ConsoleCommandSender)
                return cmdp.parse(sender, args);
            else if (sender instanceof Player && sender.hasPermission("gazlunebot.admin")) {
                return cmdp.parse(sender, args);
            }

            return false;
        } //If this has happened the function will return true.
        // If this hasn't happened the value of false will be returned.
        return false;
    }

    public void register() {
        log.info("Registering Gazlunebot event listener...");
        getServer().getPluginManager().registerEvents(new ChatListener(this,cfg), this);
    }

    public void unregister() {
        log.info("Unregistering Gazlunebot event listener...");
        HandlerList.unregisterAll(this);
    }




}
