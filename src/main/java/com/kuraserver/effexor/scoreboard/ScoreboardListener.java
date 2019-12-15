package com.kuraserver.effexor.scoreboard;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import com.kuraserver.effexor.Effexor;

public class ScoreboardListener implements Listener {

    private Effexor plugin;

    public ScoreboardListener(Effexor plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        ScoreboardManager.setScoreboard(event.getPlayer(), new MainScoreboard(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        ScoreboardManager.unsetScoreboard(event.getPlayer());
    }

}
