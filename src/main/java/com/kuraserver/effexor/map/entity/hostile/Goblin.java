package com.kuraserver.effexor.map.entity.hostile;

import cn.nukkit.Player;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AnimatePacket;
import com.kuraserver.effexor.item.ItemCreator;
import com.kuraserver.effexor.item.ItemReader;
import com.kuraserver.effexor.map.entity.Humanoid;
import com.kuraserver.effexor.map.entity.VanilaEntity;
import com.kuraserver.effexor.map.entity.behavior.ChaseTargetBehavior;
import com.kuraserver.effexor.map.entity.behavior.DirectAttackBehavior;
import com.kuraserver.effexor.map.entity.behavior.GravityBehavior;
import com.kuraserver.effexor.map.entity.behavior.LookAtTargetBehavior;
import com.kuraserver.effexor.table.ItemExpTable;
import com.kuraserver.effexor.utils.ResourceReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Goblin extends Humanoid {

    @Override
    public String getName(){
        return "Goblin";
    }

    public Goblin(double x, double y, double z, double yaw, double pitch) {
        super(x, y, z, yaw, pitch);

        this.healthHolder = true;
        this.healthbarHolder = true;
        this.maxHealth = 5;
        this.health = 5;

        this.bbHolder = true;
        this.width = 0.6f;
        this.height = 1.8f;
        this.size = 0.8f;

        this.chaseSpeed = 0.19;

        this.attackDelay = 7;
        this.attackRange = 0.7;
        this.attack = 1;

        this.updateNameTag();
        this.applySize();
        this.setBBProperty();
        this.calculateBB();

        this.addBehavior(0, new LookAtTargetBehavior(this));
        this.addBehavior(1, new GravityBehavior(this));
        this.addBehavior(2, new ChaseTargetBehavior(this));
        this.addBehavior(3, new DirectAttackBehavior(this));

        this.skin = new Skin();
        this.skin.setSkinData(Objects.requireNonNull(ResourceReader.getBufferedImage("/skin/image/goblin.png")));
        this.skin.generateSkinId("goblin");
        this.skin.setGeometryName("geometry.goblin");
        this.skin.setGeometryData(ResourceReader.getGeometry("/skin/geometry/goblin.json"));

        if(new Random().nextInt(2) == 0) {
            this.item = ItemCreator.create(268, 50, 1, new HashMap<String, Object>(){
                {
                    put(ItemCreator.TAG_UNBREAK, 1);

                    put(ItemCreator.DATA_SHOW_DLORE, 1);
                    put(ItemCreator.TAG_SLORE, "錆に侵され、朽ちた剣。今にも壊れそうだ。");

                    put(ItemCreator.TAG_ATK, 2 + new Random().nextInt(3));

                    put(ItemCreator.TAG_LEVEL_MAX, 5);
                    put(ItemCreator.TAG_LEVEL, 1);
                    put(ItemCreator.TAG_EXP, 0);
                    put(ItemCreator.TAG_EXP_TYPE, ItemExpTable.TYPE_POW3);

                }
            }, "朽ちた剣");
        }

        if(new Random().nextInt(2) == 0) {
            this.helmet = ItemCreator.create(298, 50, 1, new HashMap<String, Object>(){
                {
                    put(ItemCreator.TAG_UNBREAK, 1);
                    put(ItemCreator.TAG_DEF,  1 + new Random().nextInt(1));
                    put(ItemCreator.TAG_SLORE, "長い年月を経て、くたびれた帽子");
                    put(ItemCreator.DATA_SHOW_DLORE, 1);

                    put(ItemCreator.TAG_LEVEL_MAX, 5);
                    put(ItemCreator.TAG_LEVEL, 1);
                    put(ItemCreator.TAG_EXP, 0);
                    put(ItemCreator.TAG_EXP_TYPE, ItemExpTable.TYPE_POW3);
                }
            }, "くたびれた帽子");
        }

        if(new Random().nextInt(2) == 0) {
            this.chest = ItemCreator.create(299, 50, 1, new HashMap<String, Object>(){
                {
                    put(ItemCreator.TAG_UNBREAK, 1);
                    put(ItemCreator.TAG_DEF,  1 + new Random().nextInt(3));
                    put(ItemCreator.TAG_SLORE, "長い年月を経て、くたびれた胸当て");
                    put(ItemCreator.DATA_SHOW_DLORE, 1);

                    put(ItemCreator.TAG_LEVEL_MAX, 5);
                    put(ItemCreator.TAG_LEVEL, 1);
                    put(ItemCreator.TAG_EXP, 0);
                    put(ItemCreator.TAG_EXP_TYPE, ItemExpTable.TYPE_POW3);
                }
            }, "くたびれた胸当て");
        }

        if(new Random().nextInt(2) == 0) {
            this.leggings = ItemCreator.create(300, 50, 1, new HashMap<String, Object>(){
                {
                    put(ItemCreator.TAG_UNBREAK, 1);
                    put(ItemCreator.TAG_DEF,  1 + new Random().nextInt(2));
                    put(ItemCreator.TAG_SLORE, "長い年月を経て、くたびれた脛当て");
                    put(ItemCreator.DATA_SHOW_DLORE, 1);

                    put(ItemCreator.TAG_LEVEL_MAX, 5);
                    put(ItemCreator.TAG_LEVEL, 1);
                    put(ItemCreator.TAG_EXP, 0);
                    put(ItemCreator.TAG_EXP_TYPE, ItemExpTable.TYPE_POW3);
                }
            }, "くたびれた脛当て");
        }

        if(new Random().nextInt(2) == 0) {
            this.boots = ItemCreator.create(301, 50, 1, new HashMap<String, Object>(){
                {
                    put(ItemCreator.TAG_UNBREAK, 1);
                    put(ItemCreator.TAG_DEF, 1 + new Random().nextInt(1));
                    put(ItemCreator.TAG_SLORE, "長い年月を経て、くたびれたブーツ");
                    put(ItemCreator.DATA_SHOW_DLORE, 1);

                    put(ItemCreator.TAG_LEVEL_MAX, 5);
                    put(ItemCreator.TAG_LEVEL, 1);
                    put(ItemCreator.TAG_EXP, 0);
                    put(ItemCreator.TAG_EXP_TYPE, ItemExpTable.TYPE_POW3);
                }
            }, "くたびれたブーツ");
        }

        if(this.getAttack() > 1){
            this.attackDelay *= 1.2;
            this.chaseSpeed *= 0.95;
        }

        if(this.getDefence() > 1){
            this.chaseSpeed *= (1 - this.getDefence() * 0.01);
        }
    }

    public Goblin(double x, double y, double z) {
        this(x, y, z, 0, 0);
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
        Vector3 base = this.getDirectionVector().multiply(0.7);
        base.y = 0.1;
        return base;
    }

    @Override
    public ArrayList<Item> getDrops() {
        ArrayList<Item> drops = new ArrayList<Item>();
        if(new Random().nextInt(4) == 0) drops.add(this.item);
        if(new Random().nextInt(4) == 0) drops.add(this.helmet);
        if(new Random().nextInt(4) == 0) drops.add(this.chest);
        if(new Random().nextInt(4) == 0) drops.add(this.leggings);
        if(new Random().nextInt(4) == 0) drops.add(this.boots);
        return drops;
    }

    @Override
    public void attackTo(Player player, int damage, Vector3 knockback) {
        super.attackTo(player, damage, knockback);

        AnimatePacket pk = new AnimatePacket();
        pk.eid = this.getEid();
        pk.action = AnimatePacket.Action.SWING_ARM;
        this.broadcastPacket(pk);
    }

    @Override
    public int getAttack() {
        return 1 + new ItemReader(this.item).getAttack();
    }

    @Override
    public int getDefence() {
        int def = 0;
        def += new ItemReader(this.helmet).getDefence();
        def += new ItemReader(this.chest).getDefence();
        def += new ItemReader(this.leggings).getDefence();
        def += new ItemReader(this.boots).getDefence();
        return def;
    }
}
