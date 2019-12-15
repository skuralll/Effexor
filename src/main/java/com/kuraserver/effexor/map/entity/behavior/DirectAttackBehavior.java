package com.kuraserver.effexor.map.entity.behavior;

import com.kuraserver.effexor.Effexor;
import com.kuraserver.effexor.map.entity.EMapEntity;

public class DirectAttackBehavior extends Behavior {

    private int delay = 0;

    public DirectAttackBehavior(EMapEntity owner) {
        super(owner);
    }

    @Override
    public String getKey() {
        return "directattack";
    }

    @Override
    public void onUpdate(int currentTick) {
        super.onUpdate(currentTick);
        if(this.owner.getTarget() == null) return;

        this.delay++;
        if(this.delay > this.owner.getAttackDelay() && this.owner.distance(this.owner.getTarget()) <= this.owner.getAttackRange()){
            this.owner.attackTo(this.owner.getTarget(), this.owner.getAttack(), this.owner.getKnockBack());
            this.delay = 0;
        }
    }
}
