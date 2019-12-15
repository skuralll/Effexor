package com.kuraserver.effexor.map.entity.behavior;

import com.kuraserver.effexor.map.entity.EMapEntity;

public class LookAtTargetBehavior extends Behavior {

    public LookAtTargetBehavior(EMapEntity owner) {
        super(owner);
    }

    @Override
    public String getKey() {
        return "lookattarget";
    }

    @Override
    public void onUpdate(int currentTick) {
        super.onUpdate(currentTick);
        if(this.owner.getTarget() != null){
            this.owner.lookAt(this.owner.getTarget());
        }
    }
}
