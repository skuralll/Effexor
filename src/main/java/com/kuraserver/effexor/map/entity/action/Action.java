package com.kuraserver.effexor.map.entity.action;

import com.kuraserver.effexor.map.entity.EMapEntity;
import com.kuraserver.effexor.map.entity.behavior.Behavior;

import java.util.ArrayList;

abstract public class Action {

    protected EMapEntity entity;

    public Action(EMapEntity entity) {
        this.entity = entity;
    }

    public Class[] getLockBehaviors(){
        return new Class[]{};
    }

    public void open(){

    }

    public void close(){
        this.entity.removeAction(this);
    }

    public void onUpdate(int currentTick){

    }

}
