package com.kuraserver.effexor.form;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

public interface FormInterface {

    abstract public void handleResponse(Player player, FormResponse response);

}
