package com.kuraserver.effexor.map.entity;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.*;
import cn.nukkit.item.Item;
import cn.nukkit.level.Location;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.EntityEventPacket;
import cn.nukkit.network.protocol.MoveEntityAbsolutePacket;
import cn.nukkit.network.protocol.SetEntityDataPacket;
import cn.nukkit.scheduler.NukkitRunnable;
import com.kuraserver.effexor.Effexor;
import com.kuraserver.effexor.animation.animations.DamageAnimation;
import com.kuraserver.effexor.map.EMap;
import com.kuraserver.effexor.map.entity.action.Action;
import com.kuraserver.effexor.map.entity.behavior.Behavior;

import java.util.*;

abstract public class EMapEntity extends Location {

    protected EMap owner = null;

    protected long eid;

    protected UUID uuid;

    protected EntityMetadata metadata;

    protected boolean closed = false;

    /*HP関係*/
    protected boolean healthHolder = false;
    protected boolean healthbarHolder = false;
    protected int maxHealth = 0;
    protected int health = 0;

    protected boolean damageable = false;

    /**/
    protected double chaseSpeed = 0.05;
    protected double attackDistance = 1;
    protected int attackDelay = 10;
    protected double attackRange = 0.8;
    protected int attack = 2;
    protected int defence = 1;

    /*当たり判定関係*/
    protected boolean bbHolder = false;
    protected AxisAlignedBB bb = new SimpleAxisAlignedBB(0, 0, 0, 0, 0, 0);
    protected float size = 1;
    protected float width = 1;
    protected float eyeHeight = 1;
    protected float height = 1;

    /*モーション*/
    protected double lastX = 0;
    protected double lastY = 0;
    protected double lastZ = 0;

    protected double motionX = 0;
    protected double motionY = 0;
    protected double motionZ = 0;

    /*重力*/
    protected double gravity = 0.08;
    protected double gravityNow = 0;
    //protected boolean fallFlag = false;

    /*ターゲット*/
    protected Map<Player, Integer> hates = new HashMap<Player, Integer>();
    protected Player target;

    /*ビヘイビア*/
    protected Map<Integer, Behavior> behaviors = new TreeMap<Integer, Behavior>();
    /*アクション*/
    protected Map<String, Action> actions = new HashMap<>();

    public EMapEntity(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    public EMapEntity(double x, double y, double z, double yaw, double pitch) {
        super(x, y, z, yaw, pitch);
        this.eid = Entity.entityCount++;

        this.metadata = new EntityMetadata();

        this.updateNameTag();
        //this.applySize();
        //this.setBBProperty();
        this.calculateBB();
    }

    public void setOwner(EMap map){
        this.owner = map;
    }

    public EMap getOwner(){
        return this.owner;
    }

    public long getEid(){
        return this.eid;
    }

    public String getName(){
        return "";
    }

    public void updateNameTag(){
        String tag = "";

        if(this.healthbarHolder){
            this.setNameTagAlwaysVisible(true);
            float healthPercentage = this.health <= 0 ? 0 : (float)this.health / this.maxHealth;
            String color = "";
            if(healthPercentage > 0.5) color = "§a";
            else if(healthPercentage > 0.1) color = "§6";
            else color = "§c";

            String healthbar = this.getName() + " " + color + Math.round(healthPercentage * 100) + '%';
            tag += healthbar;
        }

        this.setNameTag(tag);
    }

    public void setNameTag(String tag){
        this.setDataproperty(new StringEntityData(Entity.DATA_NAMETAG, tag));
    }

    public void setNameTagAlwaysVisible(boolean bool){
        this.setDataproperty(new ByteEntityData(Entity.DATA_ALWAYS_SHOW_NAMETAG, bool?1:0));
    }

    protected void applySize(){
        this.width *= this.size;
        this.eyeHeight *= this.size;
        this.height *= this.size;

        this.metadata.putFloat(Entity.DATA_SCALE, this.size);
    }

    public void setBBProperty(){
        this.setDataproperty(new FloatEntityData(Entity.DATA_BOUNDING_BOX_WIDTH, this.width));
        this.setDataproperty(new FloatEntityData(Entity.DATA_BOUNDING_BOX_HEIGHT, this.height));
    }

    public void calculateBB(){
        float halfWidth = this.width/2;
        this.bb.setBounds(
                this.x - halfWidth,
                this.y,
                this.z - halfWidth,
                this.x + halfWidth,
                this.y + this.height,
                this.z + halfWidth
        );
    }

    public void setDataproperty(EntityData data){
        this.metadata.put(data);

        this.sendMetadata();
    }

    public EntityData getDataproperty(int id){
        return this.metadata.get(id);
    }

    public EntityMetadata getMetadata(){
        return this.metadata;
    }

    public void sendMetadata(){
        SetEntityDataPacket pk = new SetEntityDataPacket();
        pk.eid = this.getEid();
        pk.metadata = this.metadata;

        this.broadcastPacket(pk);
    }

    public void sendPosition(){
        MoveEntityAbsolutePacket pk = new MoveEntityAbsolutePacket();
        pk.eid = this.getEid();
        pk.x = this.x;
        pk.y = this.y;
        pk.z = this.z;
        pk.yaw = this.yaw;
        pk.headYaw = this.yaw;
        pk.pitch = this.pitch;

        this.broadcastPacket(pk);
    }

    public void attackBy(Player player, int damage){
        if(!this.healthHolder) return;

        if(!Effexor.getInstance().getEplayerManager().get(player).canAttackTo(this)) return;
        Effexor.getInstance().getEplayerManager().get(player).updateLastAttackTime();

        damage = damage - this.getDefence() > 0 ? damage - this.getDefence() : 1;

        this.health -= damage;

        this.updateNameTag();
        Effexor.getInstance().getAnimationManager().addAnimation(new DamageAnimation(player, this, damage));

        for (Behavior behavior: this.behaviors.values()) {
            behavior.onAttackBy(player, damage);
        }

        if(this.health > 0){
            this.hurtAnimation();
        }
        else{
            this.death();
            Effexor.getInstance().getEplayerManager().get(player).killEMapEntity(this);
        }
    }

    public void attackTo(Player player, int damage, Vector3 knockback){
        Effexor.getInstance().getEplayerManager().get(player).attack(damage, knockback);
    }

    protected void hurtAnimation(){
        EntityEventPacket pk = new EntityEventPacket();
        pk.eid = this.getEid();
        pk.event = EntityEventPacket.HURT_ANIMATION;

        this.broadcastPacket(pk);
    }

    protected void deathAnimation(){
        EntityEventPacket pk = new EntityEventPacket();
        pk.eid = this.getEid();
        pk.event = EntityEventPacket.DEATH_ANIMATION;

        this.broadcastPacket(pk);
    }

    public void broadcastPacket(DataPacket pk){
        if(this.owner != null) Server.broadcastPacket(this.getOwner().getPlayers().values(), pk);
    }

    public void death(){
        this.deathAnimation();
        new NukkitRunnable() {
            EMapEntity eMapEntity;
            public NukkitRunnable setOwner(EMapEntity entity){
                this.eMapEntity = entity;
                return this;
            }
            @Override
            public void run() {
                this.eMapEntity.hideAll();
            }
        }.setOwner(this).runTaskLater(Effexor.getInstance(), 20);
        this.close();
    }

    public void open(){

    }

    public boolean isClosed(){
        return this.closed;
    }

    public void close(){
        this.closed = true;
        this.getOwner().removeEntity(this);
    }

    public void show(Player player){

    }

    public void showAll(){
        for (Player player: this.owner.getPlayers().values()) show(player);
    }

    public void hide(Player player){

    }

    public void hideAll(){
        for (Player player: this.owner.getPlayers().values()) hide(player);
    }

    public void onUpdate(int currentTick){
        if(this.isClosed()) return;

        ArrayList<Class> locks = new ArrayList<Class>();
        for (Action action: this.actions.values()){
            action.onUpdate(currentTick);
            locks.addAll(Arrays.asList(action.getLockBehaviors()));
        }

        for (Behavior behavior: this.behaviors.values()) {
            if(!locks.contains(behavior.getClass())) behavior.onUpdate(currentTick);
        }

        this.move(this.x, this.y, this.z);

        if(this.y < 0){
            this.death();
        }
    }

    public void onTouch(Player player){
        this.attackBy(player, Effexor.getInstance().getEplayerManager().get(player).getAttack());
    }

    public double getWidth(){
        return this.width;
    }

    public double getHeight(){
        return this.height;
    }

    public void setRotation(float yaw, float pitch){
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void lookAt(Vector3 target){
        float pitch = 0;
        float yaw = 0;
        double horizontal = Math.sqrt( Math.pow(target.x - this.x, 2) + Math.pow(target.z - this.z, 2));
        double vertical = target.y - this.y;
        pitch = (float) Math.toDegrees(-Math.atan2(vertical, horizontal));
        double xDist = target.x - this.x;
        double zDist = target.z - this.z;
        yaw = (float) Math.toDegrees(Math.atan2(zDist, xDist)) - 90;
        if(yaw < 0) yaw += 360;

        this.setRotation(yaw, pitch);
    }

    public void tryMovement(){
        double x = this.x + this.motionX;
        double y = this.y + this.motionY;
        double z = this.z + this.motionZ;

        this.move(x, y, z);
    }

    public void move(double x, double y, double z){
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;

        this.x = x;
        this.y = y;
        this.z = z;
        this.calculateBB();
        this.sendPosition();
    }

    public Vector3 getMotion(){
        return new Vector3(this.motionX, this.motionY, this.motionZ);
    }

    public void setMotion(Vector3 motion){
        this.motionX = motion.x;
        this.motionY = motion.y;
        this.motionZ = motion.z;
    }

    public Player getTarget(){
        return this.target;
    }

    public boolean isOnGround(){
        AxisAlignedBB bb = this.bb.clone();
        bb.setMinY(bb.getMinY() - 0.001);
        return this.level.getCollisionBlocks(bb).length > 0;
    }

    public void setNowGravity(double gravity){
        this.gravityNow = gravity;
    }

    public double getNowGravity(){
        return this.gravityNow;
    }

    public double getGravity(){
        return this.gravity;
    }

    public AxisAlignedBB getBB(){
        return this.bb;
    }

    public double getChaseSpeed(){
        return this.chaseSpeed;
    }

    public int getAttackDelay(){
        return this.attackDelay;
    }

    public double getAttackRange(){
        return this.attackRange;
    }

    public int getAttack(){
        return this.attack;
    }

    public int getDefence(){
        return this.defence;
    }

    public Vector3 getKnockBack(){
        return new Vector3(0,0,0);
    }

    public ArrayList<Item> getDrops(){
        return new ArrayList<Item>();
    }

    /*ビヘイビア*/
    public void addBehavior(Integer priority, Behavior behavior){
        behavior.open();
        this.behaviors.put(priority, behavior);
    }

    /*アクション*/
    public void addAction(Action action){
        action.open();
        this.actions.put(action.getClass().toString(), action);
    }

    public void removeAction(Action action){
        this.actions.remove(action.getClass().toString());
    }

}
