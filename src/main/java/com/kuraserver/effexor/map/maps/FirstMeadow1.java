package com.kuraserver.effexor.map.maps;

import cn.nukkit.level.Location;
import com.kuraserver.effexor.map.EMap;
import com.kuraserver.effexor.map.UniqueMap;
import com.kuraserver.effexor.map.entity.common.WarpCrystal;
import com.kuraserver.effexor.map.entity.hostile.Wolf;

import java.util.Random;

public class FirstMeadow1 extends EMap implements UniqueMap {

    public String getEMapName(){
        return "ほろうのそうげん";
    }

    public String getSubEMapName(){
        return "その1";
    }

    public static String uniqueKey = "first_meadow_1";

    private int maxEntity = 6;

    private Location[] enemySpawns = new Location[]{
            new Location(241,23,410,314,8),
            new Location(243,23,429,236,5),
            new Location(224,22,409,292,0),
            new Location(223,22,424,263,0),
            new Location(231,22,416,263,0),
            new Location(233,22,411,299,0),
            new Location(221,22,424,229,0),
            new Location(238,22,423,263,0),
            new Location(234,24,428,251,11),
            new Location(246,26,430,219,11),
            new Location(246,24,407,331,9),
    };

    public FirstMeadow1() {
        this.addEntity(new WarpCrystal(254, 22, 422, FirstVillage.uniqueKey, new Location(207, 59, 224, 310, 0)));

        for (int i=0; i<4; i++) this.randomSpawn();
    }

    @Override
    protected String getKey() {
        return uniqueKey;
    }

    @Override
    protected String getLevelName() {
        return "Pockets";
    }

    @Override
    protected Location getSpawnLocation() {
        return new Location(251, 22.5, 420);
    }

    @Override
    public void onUpdate(int currentTick) {
        super.onUpdate(currentTick);
        if(currentTick % 100 == 0){
            if(this.getEntities().size() < this.maxEntity){
                this.randomSpawn();
            }
        }
    }

    private void randomSpawn(){
        Location spawn = this.enemySpawns[new Random().nextInt(this.enemySpawns.length)];
        this.addEntity(new Wolf(spawn.x, spawn.y, spawn.z, spawn.yaw, spawn.pitch));
    }
}
