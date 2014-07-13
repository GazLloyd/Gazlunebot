package gazlloyd;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Gareth Lloyd on 11/07/14.
 */
public final class ChatListener implements Listener {

    Config config;
    Gazlunebot bot;

    public ChatListener(Gazlunebot b, Config c) {
        bot = b;
        config = c;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        if (event.getMessage().startsWith(">")) {
            process(event.getPlayer(), event.getMessage().substring(1).split(" "));
        }
    }

    public void process(final Player p, final String[] cmd) {
        if (config.ignored.contains(p.getPlayerListName())) //if player is on ignored list, ignore
            return;
        final String cmdst = config.casesensitive ? cmd[0] : cmd[0].toLowerCase();
        if (!config.resp.containsKey(cmdst)) //if command is not defined, ignore
            return;
        bot.getServer().getScheduler().scheduleSyncDelayedTask(bot, new Runnable() {
            @Override
            public void run() { //force response to come after invocation
                String resp = config.resp.get(cmdst);
                resp = interpolate(resp, p, cmd);
                bot.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',config.prefix + resp));
            }
        }, config.delay);

    }

    public String interpolate(String s, Player p, String[] cmd) {
        String resp = s;

        String a = "";
        for (int i = 1; i < cmd.length; i++) {
            a += " "+cmd[i];
        }

        if (!a.equalsIgnoreCase(""))
            resp = resp.replaceAll("(?<!\\$)\\$a(\\{.*?\\})?",a);
        else {
            resp = resp.replaceAll("(?<!\\$)\\$a\\{(.*?)\\}","$1");
            resp = resp.replaceAll("(?<!\\$)\\$a","");
        }

        if (cmd.length > 1)
            resp = resp.replaceAll("(?<!\\$)\\$1(\\{.*?\\})?",cmd[1]);
        else {
            resp = resp.replaceAll("(?<!\\$)\\$1\\{(.*?)\\}","$1");
            resp = resp.replaceAll("(?<!\\$)\\$1","");
        }

        if (cmd.length > 2)
            resp = resp.replaceAll("(?<!\\$)\\$2(\\{.*?\\})?",cmd[2]);
        else {
            resp = resp.replaceAll("(?<!\\$)\\$2\\{(.*?)\\}","$1");
            resp = resp.replaceAll("(?<!\\$)\\$2","");
        }
        if (cmd.length > 3)
            resp = resp.replaceAll("(?<!\\$)\\$3(\\{.*?\\})?",cmd[3]);
        else {
            resp = resp.replaceAll("(?<!\\$)\\$3\\{(.*?)\\}","$1");
            resp = resp.replaceAll("(?<!\\$)\\$3","");
        }
        if (cmd.length > 4)
            resp = resp.replaceAll("(?<!\\$)\\$4(\\{.*?\\})?",cmd[4]);
        else {
            resp = resp.replaceAll("(?<!\\$)\\$4\\{(.*?)\\}","$1");
            resp = resp.replaceAll("(?<!\\$)\\$4","");
        }
        if (cmd.length > 5)
            resp = resp.replaceAll("(?<!\\$)\\$5(\\{.*?\\})?",cmd[5]);
        else {
            resp = resp.replaceAll("(?<!\\$)\\$5\\{(.*?)\\}","$1");
            resp = resp.replaceAll("(?<!\\$)\\$5","");
        }

        resp = resp.replaceAll("(?<!\\$)\\$p",p.getDisplayName());

        resp = resp.replaceAll("\\$\\$","$");

        return resp;
    }

}

