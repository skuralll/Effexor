package com.kuraserver.effexor.animation.animations;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import com.kuraserver.effexor.animation.Animation;

import java.util.Random;

public class DamageAnimation extends Animation {

    private Player player;
    private Vector3 pos;
    private Integer damage;

    private long eid;

    private int tick = 0;

    public DamageAnimation(Player player, Vector3 pos, Integer damage) {
        this.player = player;
        this.pos = pos;
        this.damage = damage;

        this.eid = Entity.entityCount++;

        this.show();
    }

    private void show(){
        AddEntityPacket pk = new AddEntityPacket();
        pk.entityUniqueId = this.eid;
        pk.entityRuntimeId = this.eid;
        pk.id = "minecraft:item";
        pk.x = (float) this.pos.x;
        pk.y = (float) this.pos.y;
        pk.z = (float) this.pos.z;
        pk.speedX = (1 - new Random().nextInt(2)) * 0.04f;
        pk.speedY = 0.21f;
        pk.speedZ = (1 - new Random().nextInt(2)) * 0.04f;
        pk.yaw = 0;
        pk.headYaw = 0;
        pk.pitch = 0;

        EntityMetadata data = new EntityMetadata();
        data.putString(Entity.DATA_NAMETAG, "§l" + this.damage);
        data.putByte(Entity.DATA_ALWAYS_SHOW_NAMETAG, 1);
        data.putFloat(Entity.DATA_SCALE, 0);

        pk.metadata = data;

        player.dataPacket(pk);
    }

    private void hide(){
        if(!this.player.isOnline()) return;

        RemoveEntityPacket pk = new RemoveEntityPacket();
        pk.eid = this.eid;

        this.player.dataPacket(pk);
    }

    @Override
    public void onUpdate(int currentTick) {
        this.tick++;
        if(this.tick > 13){
            this.hide();
            this.close();
        }
    }
}
