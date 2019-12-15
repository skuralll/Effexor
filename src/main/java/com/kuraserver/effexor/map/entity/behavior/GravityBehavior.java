package com.kuraserver.effexor.map.entity.behavior;

import cn.nukkit.block.Block;
import cn.nukkit.math.AxisAlignedBB;
import com.kuraserver.effexor.map.entity.EMapEntity;

public class GravityBehavior extends Behavior {

    public GravityBehavior(EMapEntity owner) {
        super(owner);
    }

    @Override
    public String getKey() {
        return "gravity";
    }

    @Override
    public void onUpdate(int currentTick) {
        super.onUpdate(currentTick);
        if(!this.owner.isOnGround()){
            this.owner.setNowGravity(this.owner.getNowGravity() + this.owner.getGravity());

            AxisAlignedBB bb = this.owner.getBB().clone();
            bb.setMinY(this.owner.getGravity());
            Block[] blocks = this.owner.level.getCollisionBlocks(bb);
            if(blocks.length > 0){
                double high = 0;
                for (Block block: blocks){
                    if(block.getBoundingBox().getMaxY() > high){
                        high = block.getBoundingBox().getMaxY();
                    }
                }
                this.owner.y = high;
                this.owner.setNowGravity(0);
            }
            else{
                this.owner.y -= this.owner.getNowGravity();
            }
        }
        else{
            this.owner.setNowGravity(0);
        }
    }
}
