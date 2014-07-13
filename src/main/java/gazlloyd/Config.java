package gazlloyd;

import java.util.*;

/**
 * Created by Gareth Lloyd on 11/07/14.
 */
public class Config {

    boolean enabled;
    boolean casesensitive;
    String prefix;
    List<String> ignored;
    HashMap<String, String> resp;
    int delay;

    Gazlunebot bot;
    public static Config cfg;

    public Config(Gazlunebot glb) {
        bot = glb;
        cfg = this;
        Gazlunebot.log.info("Loading Gazlunebot config");
        load();
    }


    public void load() {
        enabled = bot.getConfig().getBoolean("enabled", true);
        casesensitive = bot.getConfig().getBoolean("casesensitive",false);
        prefix = bot.getConfig().getString("prefix","Elunebot");
        ignored = bot.getConfig().getStringList("ignored");
        delay = bot.getConfig().getInt("delay",20);
        if (delay < 0) {
            Gazlunebot.log.info("Delay cannot be negative! Using 20 for now, please fix config and reload.");
            delay = 20;
        }

        parseResp(bot.getConfig().getConfigurationSection("responses").getKeys(false));

        Gazlunebot.log.info("All possible messages:");
        for (String k : resp.keySet()) {
            Gazlunebot.log.info(k+" : "+resp.get(k));
        }
    }

    private void parseResp(Set<String> s) {
        resp = new HashMap<>();

        Iterator i = s.iterator();
        while (i.hasNext()) {
            String cmd = i.next().toString();
            String response = bot.getConfig().getString("responses."+cmd);
            if (response == null) {
                Gazlunebot.log.warning("Gazlunebot command "+cmd+" has no response! Ignoring.");
                continue;
            }
            resp.put(casesensitive ? cmd : cmd.toLowerCase(),response);
        }
    }

    public void setEnabled() {
        enabled = true;
        bot.getConfig().set("enabled",true);
        bot.saveConfig();
    }


    public void setDisabled() {
        enabled = false;
        bot.getConfig().set("enabled",false);
        bot.saveConfig();
    }



}
