package com.kuraserver.effexor.map.entity;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemHelmetChain;
import cn.nukkit.network.protocol.*;
import cn.nukkit.network.protocol.types.ContainerIds;
import cn.nukkit.utils.SerializedImage;

import java.util.UUID;

public class Humanoid extends EMapEntity {

    private static final Skin EMPTY_SKIN = new Skin();

    static {
        EMPTY_SKIN.setSkinData(SerializedImage.fromLegacy(new byte[8192]));
        EMPTY_SKIN.generateSkinId("empty");
    }

    protected UUID uuid = UUID.randomUUID();

    protected Skin skin = EMPTY_SKIN;

    protected Item item = Item.get(Item.AIR);

    protected Item helmet = Item.get(Item.AIR);
    protected Item chest = Item.get(Item.AIR);
    protected Item leggings = Item.get(Item.AIR);
    protected Item boots = Item.get(Item.AIR);

    public Humanoid(double x, double y, double z, double yaw, double pitch) {
        super(x, y, z, yaw, pitch);
    }

    @Override
    public void show(Player player) {
        PlayerListPacket.Entry[] entry = {new PlayerListPacket.Entry(uuid, this.getEid(), metadata.getString(Entity.DATA_NAMETAG), this.skin)};
        PlayerListPacket playerAdd = new PlayerListPacket();
        playerAdd.entries = entry;
        playerAdd.type = PlayerListPacket.TYPE_ADD;

        player.dataPacket(playerAdd);

        AddPlayerPacket pk = new AddPlayerPacket();
        pk.uuid = this.uuid;
        pk.username = "";
        pk.entityUniqueId = this.getEid();
        pk.entityRuntimeId = this.getEid();
        pk.x = (float) this.x;
        pk.y = (float) this.y + 1.62f;
        pk.z = (float) this.z;
        pk.speedX = 0;
        pk.speedY = 0;
        pk.speedZ = 0;
        pk.yaw = (float) this.yaw;
        pk.pitch = (float) this.pitch;
        pk.metadata = this.metadata;
        pk.item = this.item;

        player.dataPacket(pk);

        PlayerListPacket playerRemove = new PlayerListPacket();
        playerRemove.entries = entry;
        playerRemove.type = PlayerListPacket.TYPE_REMOVE;

        player.dataPacket(playerRemove);

        this.sendArmors();
    }

    @Override
    public void sendPosition(){
        MoveEntityAbsolutePacket pk = new MoveEntityAbsolutePacket();
        pk.eid = this.getEid();
        pk.x = this.x;
        pk.y = this.y + 1.62f;
        pk.z = this.z;
        pk.yaw = this.yaw;
        pk.headYaw = this.yaw;
        pk.pitch = this.pitch;

        this.broadcastPacket(pk);
    }

    @Override
    public void hide(Player player) {
        RemoveEntityPacket pk = new RemoveEntityPacket();
        pk.eid = this.getEid();

        player.dataPacket(pk);
    }


    /*public void setSkin(Skin skin){
        PlayerSkinPacket pk = new PlayerSkinPacket();
        pk.uuid = UUID.randomUUID();
        pk.skin = skin;
        pk.newSkinName = skin.getFullSkinId();
        pk.oldSkinName = this.skin.getFullSkinId();
        this.skin = skin;
        this.owner.broadcastPacket(pk);
    }*/

    public Skin getSkin(){
        return this.skin;
    }

    public void setItemInHand(Item item){
        this.item = item;

        MobEquipmentPacket pk = new MobEquipmentPacket();
        pk.eid = this.getEid();
        pk.item = this.item;
        pk.inventorySlot = pk.hotbarSlot = 0;
        pk.windowId = ContainerIds.INVENTORY;

        this.broadcastPacket(pk);
    }

    public Item getItemInHand(){
        return this.item;
    }

    public void setHelmet(Item item){
        this.helmet = item;
    }

    public void setChestplate(Item item){
        this.chest = item;
    }

    public void setLeggings(Item item){
        this.leggings = item;
    }

    public void setBoots(Item item){
        this.boots = item;
    }

    protected void sendArmors(){
        MobArmorEquipmentPacket pk = new MobArmorEquipmentPacket();
        pk.eid = this.getEid();
        pk.slots[0] = this.helmet;
        pk.slots[1] = this.chest;
        pk.slots[2] = this.leggings;
        pk.slots[3] = this.boots;

        this.broadcastPacket(pk);
    }

}
