package com.kuraserver.effexor.map.entity.hostile;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import com.kuraserver.effexor.item.ItemCreator;
import com.kuraserver.effexor.map.entity.VanilaEntity;
import com.kuraserver.effexor.map.entity.action.test.TestAction;
import com.kuraserver.effexor.map.entity.behavior.ChaseTargetBehavior;
import com.kuraserver.effexor.map.entity.behavior.DirectAttackBehavior;
import com.kuraserver.effexor.map.entity.behavior.GravityBehavior;
import com.kuraserver.effexor.map.entity.behavior.LookAtTargetBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Wolf extends VanilaEntity {

    @Override
    public String getName(){
        return "Wolf";
    }

    public Wolf(double x, double y, double z, double yaw, double pitch) {
        super(x, y, z, yaw, pitch);

        this.healthHolder = true;
        this.healthbarHolder = true;
        this.maxHealth = 20;
        this.health = 20;

        this.bbHolder = true;
        this.width = 0.6f;
        this.height = 0.85f;

        this.chaseSpeed = 0.13;

        this.updateNameTag();
        this.setBBProperty();
        this.calculateBB();

        this.addBehavior(0, new LookAtTargetBehavior(this));
        this.addBehavior(1, new GravityBehavior(this));
        this.addBehavior(2, new ChaseTargetBehavior(this));
        this.addBehavior(3, new DirectAttackBehavior(this));
    }

    public Wolf(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    @Override
    public String getVanilaId() {
        return "minecraft:wolf";
    }

    @Override
    public void attackBy(Player player, int damage) {
        super.attackBy(player, damage);
        this.target = player;
    }

    @Override
    public void onUpdate(int currentTick) {
        super.onUpdate(currentTick);
        if(this.target != null){
            if(!this.target.isOnline() || !this.target.isAlive()) this.target = null;
        }
    }

    @Override
    public Vector3 getKnockBack(){
        Vector3 base = this.getDirectionVector();
        base.y = 0.1;
        return base;
    }

    @Override
    public ArrayList<Item> getDrops() {
        ArrayList<Item> drops = new ArrayList<Item>();
        drops.add(ItemCreator.create(Item.RAW_BEEF, 0, 1 + new Random().nextInt(2), new HashMap<String, Object>(){
            {
                put(ItemCreator.DATA_SHOW_DLORE, 1);
                put(ItemCreator.TAG_FOOD, 4);
                put(ItemCreator.TAG_SATURATION, 4.0f);
                put(ItemCreator.TAG_SLORE, "なんの処理もされておらず、血生臭い。");
            }
        }, "オオカミの肉"));
        return drops;
    }
}
