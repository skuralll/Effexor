package com.kuraserver.effexor.map.entity.test;

import cn.nukkit.Player;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.utils.SerializedImage;
import com.kuraserver.effexor.Effexor;
import com.kuraserver.effexor.map.entity.Humanoid;
import com.kuraserver.effexor.map.entity.VanilaEntity;
import com.kuraserver.effexor.utils.ResourceReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Objects;

public class TestEntityV2 extends Humanoid {

    public TestEntityV2(double x, double y, double z, double yaw, double pitch){
        super(x, y, z, yaw, pitch);
        this.skin = new Skin();
        this.skin.setSkinData(Objects.requireNonNull(ResourceReader.getBufferedImage("/skin/image/guildclerk.png")));
        this.skin.generateSkinId("blacksmith");
        //this.skin.setGeometryName("geometry.goblin");
        //this.skin.setGeometryData(ResourceReader.getGeometry("/skin/geometry/goblin.json"));
    }

    @Override
    public void show(Player player) {
        super.show(player);
    }

    @Override
    public void onTouch(Player player) {
        player.sendMessage("テストEntity2");
    }
}
