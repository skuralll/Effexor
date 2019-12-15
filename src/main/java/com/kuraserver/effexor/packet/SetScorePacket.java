package com.kuraserver.effexor.packet;

import cn.nukkit.network.protocol.DataPacket;
import com.kuraserver.effexor.packet.data.ScorePacketEntry;

public class SetScorePacket extends DataPacket {

    public final static byte TYPE_CHANGE = 0;
    public final static byte TYPE_REMOVE = 1;

    public byte type;
    public ScorePacketEntry[] entries;

    @Override
    public byte pid() {
        return 108;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();

        this.putByte(this.type);
        this.putUnsignedVarInt(this.entries.length);
        for(ScorePacketEntry entry: this.entries){
            this.putVarLong(entry.scoreboardId);
            this.putString(entry.objectiveName);
            this.putLInt(entry.score);
            this.putByte(entry.type);
            this.putString(entry.customName);
        }
    }
}
