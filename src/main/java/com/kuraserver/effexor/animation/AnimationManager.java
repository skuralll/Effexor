package com.kuraserver.effexor.animation;

import cn.nukkit.Server;
import cn.nukkit.scheduler.NukkitRunnable;
import com.kuraserver.effexor.Effexor;

import java.util.ArrayList;

public class AnimationManager {

    private ArrayList<Animation> animations = new ArrayList<>();

    public AnimationManager() {
        new NukkitRunnable() {
            @Override
            public void run() {
                try {
                    for (Animation animation : animations) {
                        animation.onUpdate(Server.getInstance().getTick());
                    }
                }catch (Exception e){

                }
            }
        }.runTaskTimer(Effexor.getInstance(), 1, 1);
    }

    public void addAnimation(Animation animation){
        this.animations.add(animation);
    }

    public void removeAnimation(Animation animation){
        this.animations.remove(animation);
    }

}
