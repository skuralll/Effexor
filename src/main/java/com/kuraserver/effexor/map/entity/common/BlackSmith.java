package com.kuraserver.effexor.map.entity.common;

import cn.nukkit.Player;
import cn.nukkit.entity.data.Skin;
import com.kuraserver.effexor.form.npc.blacksmith.BlackSmithMainForm;
import com.kuraserver.effexor.map.entity.Humanoid;
import com.kuraserver.effexor.utils.ResourceReader;

import java.util.Objects;
import java.util.Random;

public class BlackSmith extends Humanoid {

    private String[] messages = new String[]{
            "やあ、俺になんの用だ？",
            "失敗しても責任は取らんぜ？"
    };
    public BlackSmith(double x, double y, double z, double yaw, double pitch) {
        super(x, y, z, yaw, pitch);

        this.skin = new Skin();
        this.skin.generateSkinId("blacksmith");
        this.skin.setSkinData(Objects.requireNonNull(ResourceReader.getBufferedImage("/skin/image/blacksmith.png")));
        this.setNameTagAlwaysVisible(true);
        this.setNameTag("鍛冶屋");
    }

    @Override
    public void show(Player player) {
        super.show(player);
    }

    @Override
    public void onTouch(Player player) {
        player.showFormWindow(new BlackSmithMainForm(this));
    }

    public String getRandomMessage(){
        return this.messages[new Random().nextInt(this.messages.length)];
    }
}
