package com.kuraserver.effexor.scoreboard;

import cn.nukkit.Player;
import com.kuraserver.effexor.Effexor;

public class MainScoreboard extends Scoreboard{

    public MainScoreboard(Player player){
        super(player);
    }

    @Override
    public void open() {
        this.displayName = "§r§b-§r§cE§fffexor§f§r§b-§r";
        super.open();
        this.update();
    }

    public void update(){
        this.setScore(0,"     §3v0.01", 0);
        this.setScore(1,"§r§7-----------", 1);
        this.setScore(2,"§a§lギルド §r§f：  " + "未実装", 2);
        this.setScore(3,"§e§lコイン §r§f：  " + Effexor.getInstance().getEplayerManager().get(this.player).getCoin(), 3);
        this.setScore(4,"§7-----------", 5);
        this.setScore(5,"§7§lこうげき §r§f：  " + Effexor.getInstance().getEplayerManager().get(this.player).getAttack(), 5);
        this.setScore(6,"§7§lぼうぎょ §r§f：  " + Effexor.getInstance().getEplayerManager().get(this.player).getDefence(), 6);
        //this.setScore(5,"§7§lとっこう §r§f：" + String.format("%4s", "0"), 5);
        //this.setScore(6,"§7§lとくぼう §r§f：" + String.format("%4s", "0"), 6);
        this.setScore(7,"§7§lすばやさ §r§f：  " + Effexor.getInstance().getEplayerManager().get(this.player).getSpeed(), 7);
    }

    @Override
    public void onTickUpdate(Integer currentTick) {
        this.setScore(8,"§f§lYaw §r§f：" + String.format("%4s", Math.round(this.player.getYaw())), 8);
        this.setScore(9,"§f§lPitch§r§f：" + String.format("%4s",  Math.round(this.player.getPitch())), 9);
    }
}
