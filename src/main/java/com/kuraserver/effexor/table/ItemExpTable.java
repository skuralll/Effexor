package com.kuraserver.effexor.table;

public class ItemExpTable {

    public static final int TYPE_POW3 = 0;

    public static Integer getNeedExp(int nowLevel, int type){
        int exp = 0;

        switch(type){

            case TYPE_POW3:
                exp = (int)Math.pow(nowLevel + 1, 3);
                break;

        }

        return exp;
    }

}
