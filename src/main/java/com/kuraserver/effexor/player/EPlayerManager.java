package com.kuraserver.effexor.player;

import cn.nukkit.Player;
import cn.nukkit.Server;
import com.kuraserver.effexor.Effexor;

import java.util.HashMap;
import java.util.Map;

public class EPlayerManager {

    private Map<String, EPlayer> players = new HashMap<String, EPlayer>();

    public EPlayerManager() {
        Server.getInstance().getPluginManager().registerEvents(new EPlayerListener(), Effexor.getInstance());
    }

    public EPlayer get(Player player){
        return this.players.get(player.getName());
    }

    public void join(Player player){
        this.players.put(player.getName(), new EPlayer(player));
    }

    public void quit(Player player){
        this.players.get(player.getName()).fin();
        this.players.remove(player.getName());
    }

    public void saveAll(){
        for (EPlayer player: this.players.values()){
            player.save();
        }
    }
}
