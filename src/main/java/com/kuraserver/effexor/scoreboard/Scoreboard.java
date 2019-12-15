package com.kuraserver.effexor.scoreboard;

import cn.nukkit.Player;
import com.kuraserver.effexor.packet.RemoveObjectivePacket;
import com.kuraserver.effexor.packet.SetDisplayObjectivePacket;
import com.kuraserver.effexor.packet.SetScorePacket;
import com.kuraserver.effexor.packet.data.ScorePacketEntry;

public class Scoreboard {

    protected static String OBJECT_NAME = "bfthree";

    protected static String DISPLAY_NAME = "";

    protected Player player;

    protected String displayName = "";

    public Scoreboard(Player player){
        this.player = player;
    }

    public void open(){
        this.show();
    }

    public void close(){

    }

    public void show(){
        SetDisplayObjectivePacket pk = new SetDisplayObjectivePacket();
        pk.displaySlot = "sidebar";
        pk.objectiveName = Scoreboard.OBJECT_NAME;
        pk.displayName = this.displayName;
        pk.criteriaName = "dummy";
        pk.sortOrder = 0;

        this.player.dataPacket(pk);
    }

    public void hide() {
        RemoveObjectivePacket pk = new RemoveObjectivePacket();
        pk.objectiveName = Scoreboard.OBJECT_NAME;

        this.player.dataPacket(pk);
    }

    public void update(){

    }

    public void setScore(Integer id, String name, Integer score){
        this.removeScore(id);

        ScorePacketEntry entry = new ScorePacketEntry();
        entry.objectiveName = Scoreboard.OBJECT_NAME;
        entry.type = ScorePacketEntry.TYPE_FAKE_PLAYER;
        entry.customName = name;
        entry.score = score;
        entry.scoreboardId = id;

        SetScorePacket pk = new SetScorePacket();
        pk.type = SetScorePacket.TYPE_CHANGE;
        pk.entries = new ScorePacketEntry[]{entry};

        this.player.dataPacket(pk);
    }

    public void removeScore(Integer id){
        ScorePacketEntry entry = new ScorePacketEntry();
        entry.objectiveName = Scoreboard.OBJECT_NAME;
        entry.type = ScorePacketEntry.TYPE_FAKE_PLAYER;
        entry.customName = "";
        entry.score = 0;
        entry.scoreboardId = id;

        SetScorePacket pk = new SetScorePacket();
        pk.type = SetScorePacket.TYPE_REMOVE;
        pk.entries = new ScorePacketEntry[]{entry};

        this.player.dataPacket(pk);
    }

    public void onTickUpdate(Integer currentTick){

    }

}
