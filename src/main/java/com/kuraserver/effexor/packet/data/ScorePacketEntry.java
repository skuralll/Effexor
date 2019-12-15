package com.kuraserver.effexor.packet.data;

public class ScorePacketEntry {

    public final static int TYPE_PLAYER = 1;
    public final static int TYPE_ENTITY = 2;
    public final static int TYPE_FAKE_PLAYER = 3;

    public int scoreboardId;
    public String objectiveName;
    public int score;

    public byte type;

    public int entityUniqueId;
    public String customName;

}
