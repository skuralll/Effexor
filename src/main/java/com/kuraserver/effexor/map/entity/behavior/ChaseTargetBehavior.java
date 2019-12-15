package com.kuraserver.effexor.map.entity.behavior;

import cn.nukkit.block.Block;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.math.Vector3;
import com.kuraserver.effexor.map.entity.EMapEntity;

public class ChaseTargetBehavior extends Behavior {

    public ChaseTargetBehavior(EMapEntity owner) {
        super(owner);
    }

    @Override
    public String getKey() {
        return "chasetarget";
    }

    @Override
    public void onUpdate(int currentTick) {
        super.onUpdate(currentTick);

        if(this.owner.getTarget() == null) return;

        double disX = this.owner.getTarget().x - this.owner.x;
        double disY = this.owner.getTarget().y - this.owner.y;
        double disZ = this.owner.getTarget().z - this.owner.z;

        double power = Math.sqrt(Math.pow(disX, 2) + Math.pow(disY, 2) + Math.pow(disZ, 2));

        double vectorX = disX / power * this.owner.getChaseSpeed();
        double vectorY = this.owner.isOnGround() ? 0 : disY / power * this.owner.getChaseSpeed();
        double vectorZ = disZ / power * this.owner.getChaseSpeed();

        double cX = this.owner.x + vectorX;
        double cY = this.owner.y + vectorY;
        double cZ = this.owner.z + vectorZ;

        AxisAlignedBB bb = new SimpleAxisAlignedBB(
                cX - this.owner.getWidth()/2,
                cY,
                cZ - this.owner.getWidth()/2,
                cX + this.owner.getWidth()/2,
                cY + this.owner.getHeight(),
                cZ + this.owner.getWidth()/2
        );
        Block[] collisions = this.owner.level.getCollisionBlocks(bb);
        if(collisions.length == 0){
            this.owner.setComponents(cX, cY, cZ);
            return;
        }

        //半ブロックのぼり
        bb.setMinY(bb.getMinY() + 0.5);
        bb.setMaxY(bb.getMaxY() + 0.5);
        collisions = this.owner.level.getCollisionBlocks(bb);
        if(collisions.length == 0){
            this.owner.setComponents(cX, cY + 0.5, cZ);
            return;
        }

        //1ブロック上り
        bb.setMinY(bb.getMinY() + 1);
        bb.setMaxY(bb.getMaxY() + 1);
        collisions = this.owner.level.getCollisionBlocks(bb);
        if(collisions.length == 0){
            this.owner.setComponents(cX, cY + 1, cZ);
            return;
        }

        bb.setMinY(cY);
        bb.setMaxY(cY + this.owner.getHeight());
        collisions = this.owner.level.getCollisionBlocks(bb);
        if(collisions.length > 0){
            Block block = collisions[0];
            this.owner.x += (this.owner.x - block.x) * 0.2;
            this.owner.y += (this.owner.z - block.z) * 0.2;
            return;
        }
    }
}
