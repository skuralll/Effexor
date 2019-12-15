package com.kuraserver.effexor.map.entity.behavior;

import cn.nukkit.Player;
import com.kuraserver.effexor.map.entity.EMapEntity;

abstract public class Behavior {

    public EMapEntity owner;

    public Behavior(EMapEntity owner) {
        this.owner = owner;
    }

    abstract public String getKey();

    public void open(){

    }

    public void onUpdate(int currentTick){

    }

    public void onAttackBy(Player player, int damage){

    }

}
