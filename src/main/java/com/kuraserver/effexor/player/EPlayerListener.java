package com.kuraserver.effexor.player;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityArmorChangeEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;
import cn.nukkit.item.food.FoodNormal;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.NukkitRunnable;
import com.kuraserver.effexor.Effexor;
import com.kuraserver.effexor.item.ItemCreator;
import com.kuraserver.effexor.item.ItemReader;
import com.kuraserver.effexor.map.maps.FirstVillage;
import com.kuraserver.effexor.scoreboard.ScoreboardManager;

import java.util.HashMap;
import java.util.Map;

public class EPlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Effexor.getInstance().getEplayerManager().join(event.getPlayer());

        /*event.getPlayer().setGamemode(0);
        event.getPlayer().getInventory().clearAll();
        Map<String , Object> data = new HashMap<String, Object>(){
            {
                put(ItemCreator.TAG_UNBREAK, 1);
                put(ItemCreator.TAG_ATK, 1);
                put(ItemCreator.DATA_SHOW_DLORE, 1);
            }
        };
        Item item = ItemCreator.create(268, 0, 1, data, "冒険者の剣");
        event.getPlayer().getInventory().addItem(item);*/

        //ゲームオーバー演出回避
        new NukkitRunnable() {
            @Override
            public void run() {
                if(event.getPlayer().isOnline()){
                    event.getPlayer().clearTitle();
                    event.getPlayer().removeAllEffects();
                }
            }
        }.runTaskLater(Effexor.getInstance(), 2);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();

        //ゲームオーバー演出
        new NukkitRunnable() {
            @Override
            public void run() {
                if(player.isOnline()){
                    player.sendTitle("§4§lGame Over!!", "", 5, 40, 20);
                    player.addEffect(Effect.getEffect(Effect.BLINDNESS).setDuration(20 * 5).setAmplifier(1));
                    player.addEffect(Effect.getEffect(Effect.SLOWNESS).setDuration(20 * 5).setAmplifier(5));
                }
            }
        }.runTaskLater(Effexor.getInstance(), 1);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setKeepInventory(true);
        event.setKeepExperience(true);
        event.setDeathMessage("");
        Effexor.getInstance().getEMapManager().setPlayerEMap(event.getEntity(), Effexor.getInstance().getEMapManager().getEMap(FirstVillage.uniqueKey));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Effexor.getInstance().getEplayerManager().quit(event.getPlayer());
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event){
        new NukkitRunnable() {
            @Override
            public void run() {
                if(event.getPlayer().isOnline()) ScoreboardManager.getScoreboard(event.getPlayer()).update();
            }
        }.runTaskLater(Effexor.getInstance(), 1);
    }

    @EventHandler
    public void onArmorChange(EntityArmorChangeEvent event){
        if(event.getEntity() instanceof  Player){
            new NukkitRunnable() {
                @Override
                public void run() {
                    if(((Player)event.getEntity()).isOnline()) ScoreboardManager.getScoreboard((Player) event.getEntity()).update();
                }
            }.runTaskLater(Effexor.getInstance(), 1);
        }
    }

    @EventHandler
    public void onEatFood(PlayerEatFoodEvent event){
        ItemReader reader = new ItemReader(event.getPlayer().getInventory().getItemInHand());
        Integer food = reader.getFood();
        Float saturation = reader.getSaturation();
        if(food != -1 && saturation != -1){
            event.setFood(new FoodNormal(food, saturation));
        }
    }

    @EventHandler
    public void toggleSprint(PlayerToggleSprintEvent event){

    }

}
