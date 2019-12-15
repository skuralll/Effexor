package com.kuraserver.effexor.item;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;

public class ItemReader {

    Item item;

    public ItemReader(Item item) {
        this.item = item;
    }

    public int getAttack(){
        int atk = 0;

        CompoundTag tag = this.item.getNamedTag();
        if(tag != null){
            atk = tag.getInt(ItemCreator.TAG_ATK);
        }

        return atk;
    }

    public int getDefence(){
        int def = 0;

        CompoundTag tag = this.item.getNamedTag();
        if(tag != null){
            def = tag.getInt(ItemCreator.TAG_DEF);
        }

        return def;
    }

    public int getFood(){
        int food = -1;

        CompoundTag tag = this.item.getNamedTag();
        if(tag != null){
            food = tag.getInt(ItemCreator.TAG_FOOD);
        }

        return food;
    }

    public float getSaturation(){
        float saturation = -1;

        CompoundTag tag = this.item.getNamedTag();
        if(tag != null){
            saturation = tag.getFloat(ItemCreator.TAG_FOOD);
        }

        return saturation;
    }

}
