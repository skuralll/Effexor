package com.kuraserver.effexor.map.entity.common;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import com.kuraserver.effexor.Effexor;
import com.kuraserver.effexor.map.EMap;
import com.kuraserver.effexor.map.EMapManager;
import com.kuraserver.effexor.map.entity.VanilaEntity;

public class WarpCrystal extends VanilaEntity {

    protected String toEMap;
    protected Location location;

    public WarpCrystal(double x, double y, double z, String toEMap, Location location) {
        super(x, y, z);
        this.toEMap = toEMap;
        this.location = location;
    }

    public void setDestination(String emap, Location location){
        this.toEMap = emap;
        this.location = location;
    }

    @Override
    public String getVanilaId() {
        return "minecraft:ender_crystal";
    }

    @Override
    public void onTouch(Player player) {
       Effexor.getInstance().getEMapManager().warp(player, Effexor.getInstance().getEMapManager().getEMap(this.toEMap), this.location);
    }
}
