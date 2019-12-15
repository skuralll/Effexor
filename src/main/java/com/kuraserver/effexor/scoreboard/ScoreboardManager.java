package com.kuraserver.effexor.scoreboard;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.NukkitRunnable;
import com.kuraserver.effexor.Effexor;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

    private static Map<Player, Scoreboard> scoreboards;

    public static void init(Effexor plugin){
        ScoreboardManager.scoreboards = new HashMap<Player, Scoreboard>();
        plugin.getServer().getPluginManager().registerEvents(new ScoreboardListener(plugin), plugin);

        Server.getInstance().getScheduler().scheduleRepeatingTask(Effexor.getInstance(),
        new NukkitRunnable(){
            @Override
            public void run() {
                for (Scoreboard scoreboard: ScoreboardManager.getScoreboards().values()){
                    scoreboard.onTickUpdate(Server.getInstance().getTick());
                }
            }
        },
                1);

    }

    public static void setScoreboard(Player player, Scoreboard scoreboard){
        ScoreboardManager.scoreboards.put(player, scoreboard);
        scoreboard.open();
    }

    public static Scoreboard getScoreboard(Player player){
        return scoreboards.containsKey(player) ? scoreboards.get(player) : new MainScoreboard(player);
    }

    public static Map<Player, Scoreboard> getScoreboards(){
        return scoreboards;
    }

    public static void unsetScoreboard(Player player){
        if(ScoreboardManager.scoreboards.containsKey(player)){
            ScoreboardManager.scoreboards.get(player).close();
            ScoreboardManager.scoreboards.remove(player);
        }
    }

}
