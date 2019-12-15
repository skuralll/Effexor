package com.kuraserver.effexor.packet;

import cn.nukkit.network.protocol.DataPacket;

public class SetDisplayObjectivePacket extends DataPacket {

    public static final byte NETWORK_ID = 107;

    public String displaySlot;
    public String objectiveName;
    public String displayName;
    public String criteriaName;
    public int sortOrder;

    @Override
    public byte pid() {
        return SetDisplayObjectivePacket.NETWORK_ID;
    }

    @Override
    public void encode() {
        this.reset();

        putString(this.displaySlot);
        putString(this.objectiveName);
        putString(this.displayName);
        putString(this.criteriaName);
        putVarInt(this.sortOrder);
    }

    @Override
    public void decode() {

    }
}
