package com.kuraserver.effexor.map.entity.action.test;

import com.kuraserver.effexor.map.entity.EMapEntity;
import com.kuraserver.effexor.map.entity.action.Action;
import com.kuraserver.effexor.map.entity.behavior.Behavior;
import com.kuraserver.effexor.map.entity.behavior.ChaseTargetBehavior;

public class TestAction extends Action {

    //二秒間チェイスビヘイビアを無効化するアクション
    protected Integer i = 0;

    public TestAction(EMapEntity entity) {
        super(entity);
    }

    public Class[] getLockBehaviors(){
        return new Class[]{
                ChaseTargetBehavior.class
        };
    }

    @Override
    public void open(){

    }

    @Override
    public void close(){
        super.close();
    }

    @Override
    public void onUpdate(int currentTick){
        i++;
        System.out.println(i);
        if(i > 40){
            this.close();
        }
    }

}
