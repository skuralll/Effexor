package com.kuraserver.effexor.map.maps;

import cn.nukkit.level.Location;
import com.kuraserver.effexor.map.EMap;
import com.kuraserver.effexor.map.UniqueMap;
import com.kuraserver.effexor.map.entity.common.WarpCrystal;
import com.kuraserver.effexor.map.entity.hostile.Goblin;
import com.kuraserver.effexor.map.entity.hostile.Wolf;

import java.util.Random;

public class FirstForest1 extends EMap implements UniqueMap {

    public String getEMapName(){
        return "ほろうのもり";
    }

    public String getSubEMapName(){
        return "その1";
    }

    private int maxEntity = 6;

    private Location[] enemySpawns = new Location[]{
            new Location(125, 20.1, 441, 334, -3),
            new Location(125, 20.1, 449, 329, -3),
            new Location(121, 20.1, 435, 335, 0),
            new Location(104, 20.1, 444, 306, 0),
            new Location(123, 20.1, 437, 331, 0),
            new Location(110, 20.1, 445, 313, 0),
            new Location(119, 20.1, 443, 326, 0),
            new Location(116, 20.1, 441, 320, 0),
            new Location(137, 22.1, 444, 320, 0),
            new Location(97, 22.1, 453, 239, 7),
            new Location(120, 20.1, 451, 314, -3),
    };

    public static String uniqueKey = "first_forest_1";

    public FirstForest1() {
        this.addEntity(new WarpCrystal(140, 22, 470, FirstVillage.uniqueKey, new Location(236, 58, 308, 226, 0)));

        this.randomSpawn();
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
        return new Location(140, 22.5, 470);
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
        this.addEntity(new Goblin(spawn.x, spawn.y, spawn.z, spawn.yaw, spawn.pitch));
    }
}
