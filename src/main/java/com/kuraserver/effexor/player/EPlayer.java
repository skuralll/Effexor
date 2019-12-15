package com.kuraserver.effexor.player;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import com.kuraserver.effexor.Effexor;
import com.kuraserver.effexor.item.ItemReader;
import com.kuraserver.effexor.map.entity.EMapEntity;
import com.kuraserver.effexor.scoreboard.Scoreboard;
import com.kuraserver.effexor.scoreboard.ScoreboardManager;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class EPlayer {

    Player player;

    private LinkedHashMap<String, Object> saveData = null;

    private long lastAttack = System.currentTimeMillis();

    private static LinkedHashMap<String, Object> defaultSaveData = null;

    static {
        defaultSaveData = new LinkedHashMap<>();
        defaultSaveData.put("coin", 1000);
    }

    public EPlayer(Player player) {
        this.player = player;
        this.saveData = defaultSaveData;

        new NukkitRunnable(){
            @Override
            public void run() {
                if(player.isOnline()){
                    saveData = (LinkedHashMap<String, Object>) new Config(Effexor.getInstance().getDataFolder() + File.separator + player.getLoginChainData().getXUID() + ".yml", Config.YAML, defaultSaveData).getAll();
                    ScoreboardManager.getScoreboard(player).update();
                }
            }
        }.runTaskAsynchronously(Effexor.getInstance());
    }

    public void fin(){
        new NukkitRunnable(){
            @Override
            public void run() {
                save();
            }
        }.runTaskAsynchronously(Effexor.getInstance());
    }

    public void save(){
        new Config(Effexor.getInstance().getDataFolder() + File.separator + player.getLoginChainData().getXUID() + ".yml").setAll(this.saveData);
    }

    public int getAttack(){
        int atk = 1;

        atk += new ItemReader(this.player.getInventory().getItemInHand()).getAttack();

        return atk;
    }

    public int getDefence(){
        int def = 1;

        for(Item item : this.player.getInventory().getArmorContents()){
            def += new ItemReader(item).getDefence();
        }

        return def;
    }

    public int getSpeed(){
        return 1;
    }

    public boolean canAttackTo(EMapEntity entity){
        int speedMod = 10 * this.getSpeed();//すばやさ1毎に0.01秒ディレイ減少
        return System.currentTimeMillis() - this.lastAttack + speedMod >= 1000;
    }

    public void updateLastAttackTime(){
        this.lastAttack = System.currentTimeMillis();
    }

    public void attack(int amount, Vector3 knockback){
        amount = amount - this.getDefence() > 0 ? amount - this.getDefence() : 1;
        this.player.attack(new EntityDamageEvent(player, EntityDamageEvent.DamageCause.VOID, amount));
        this.player.setMotion(knockback);
    }

    public void killEMapEntity(EMapEntity entity){
        ArrayList<Item> drops = entity.getDrops();
        if(!drops.isEmpty()){
            String message = "";
            for (Item drop: drops) {
                if(drop.getId() == 0) continue;
                message += ("§7" + drop.getCustomName());
                if(drop.getCount() > 1) message += ("×" + drop.getCount());
                message += " ";
                this.player.getInventory().addItem(drop);
            }
            if(!message.equals("")) this.player.sendMessage(message + "を手に入れた");
        }
    }

    public int getCoin(){
        return (int) this.saveData.get("coin");
    }

}
