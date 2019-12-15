package com.kuraserver.effexor.map.entity;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;

abstract public class VanilaEntity extends EMapEntity {

    public VanilaEntity(double x, double y, double z, double yaw, double pitch) {
        super(x, y, z, yaw, pitch);
    }

    public VanilaEntity(double x, double y, double z) {
        super(x, y, z);
    }

    abstract public String getVanilaId();

    @Override
    public void show(Player player) {
        AddEntityPacket pk = new AddEntityPacket();
        pk.entityUniqueId = this.getEid();
        pk.entityRuntimeId = this.getEid();
        pk.id = this.getVanilaId();
        pk.x = (float) this.x;
        pk.y = (float) this.y;
        pk.z = (float) this.z;
        pk.yaw = (float) this.yaw;
        pk.headYaw = (float) this.yaw;
        pk.pitch = (float) this.pitch;
        pk.metadata = this.getMetadata();

        player.dataPacket(pk);
    }

    @Override
    public void hide(Player player) {
        RemoveEntityPacket pk = new RemoveEntityPacket();
        pk.eid = this.getEid();

        player.dataPacket(pk);
    }
}
