package com.kuraserver.effexor.map.entity.test;

import cn.nukkit.Player;
import com.kuraserver.effexor.map.entity.VanilaEntity;

public class TestEntityV1 extends VanilaEntity {

    public TestEntityV1(double x, double y, double z, double yaw, double pitch) {
        super(x, y, z, yaw, pitch);
    }

    @Override
    public String getVanilaId() {
        return "minecraft:stray";
    }

    @Override
    public void onTouch(Player player) {
        player.sendMessage("テストEntity1");
    }
}
