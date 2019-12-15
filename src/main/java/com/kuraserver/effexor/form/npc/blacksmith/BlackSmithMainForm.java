package com.kuraserver.effexor.form.npc.blacksmith;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.window.FormWindowSimple;
import com.kuraserver.effexor.form.FormInterface;
import com.kuraserver.effexor.map.entity.common.BlackSmith;

public class BlackSmithMainForm extends FormWindowSimple implements FormInterface {

    public BlackSmithMainForm(BlackSmith smith) {
        super("鍛冶屋", smith.getRandomMessage());
        this.addButton(new ElementButton("§l武器を強化する§r§7\n"));
    }

    @Override
    public void handleResponse(Player player, FormResponse response) {

    }

}
