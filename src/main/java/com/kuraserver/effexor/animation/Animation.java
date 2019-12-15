package com.kuraserver.effexor.animation;

import com.kuraserver.effexor.Effexor;

abstract public class Animation {

    public void open(){

    }

    public void close(){
        Effexor.getInstance().getAnimationManager().removeAnimation(this);
    }

    public void onUpdate(int currentTick){

    }

}
