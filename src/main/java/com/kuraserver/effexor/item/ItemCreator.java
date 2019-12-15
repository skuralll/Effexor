package com.kuraserver.effexor.item;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import com.kuraserver.effexor.table.ItemExpTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemCreator {

    public final static String DATA_SHOW_DLORE = "dlore";//アイテムの詳細情報を表示するかどうか
    public final static String TAG_SLORE = "slore";//アイテム説明

    public final static String TAG_ATK = "atk";//こうげき
    public final static String TAG_DEF = "def";//ぼうぎょ

    public final static String TAG_LEVEL = "lv";//武具レベル
    public final static String TAG_LEVEL_MAX = "lvmax";//最大武具レベル
    public final static String TAG_EXP = "exp";//武具経験値
    public final static String TAG_EXP_TYPE = "exptype";//武具経験値タイプ

    public final static String TAG_UNBREAK = "Unbreakable";//破壊されるか

    public final static String TAG_FOOD = "food";//空腹度回復
    public final static String TAG_SATURATION = "saturation";//隠し空腹度回復

    public static Item create(Integer id, Integer meta, Integer count, Map<String, Object> data, String name){
        Item item = Item.get(id, meta, count);
        boolean showDLore = false;

        ArrayList<String> itemLore = new ArrayList<String>();

        if(!data.isEmpty()){
            CompoundTag namedtag = new CompoundTag();
            for (String tag: data.keySet()) {
                switch (tag){

                    case DATA_SHOW_DLORE:
                        showDLore = (Integer) data.get(DATA_SHOW_DLORE) == 1;
                        break;
                    case TAG_SLORE:
                        itemLore.add(data.get(tag).toString());
                        break;


                    case ItemCreator.TAG_UNBREAK:
                        namedtag.putByte(tag, ((Integer)data.get(tag)).byteValue());
                        break;


                    case ItemCreator.TAG_ATK:
                        namedtag.putInt(tag, (Integer) data.get(tag));
                        break;
                    case ItemCreator.TAG_DEF:
                        namedtag.putInt(tag, (Integer) data.get(tag));
                        break;


                    case ItemCreator.TAG_LEVEL:
                        namedtag.putInt(tag, (Integer) data.get(tag));
                        break;
                    case ItemCreator.TAG_LEVEL_MAX:
                        namedtag.putInt(tag, (Integer) data.get(tag));
                        break;
                    case ItemCreator.TAG_EXP:
                        namedtag.putInt(tag, (Integer) data.get(tag));
                        break;
                    case ItemCreator.TAG_EXP_TYPE:
                        namedtag.putInt(tag, (Integer) data.get(tag));
                        break;


                    case ItemCreator.TAG_FOOD:
                        namedtag.putInt(tag, (Integer) data.get(tag));
                        break;
                    case ItemCreator.TAG_SATURATION:
                        namedtag.putFloat(tag, (Float) data.get(tag));
                        break;
                }
            }
            item.setNamedTag(namedtag);
        }

        if(showDLore){
            if(data.containsKey(TAG_LEVEL) && data.containsKey(TAG_EXP) && data.containsKey(TAG_EXP_TYPE) && data.containsKey(TAG_LEVEL_MAX)){
                int lv = ((Integer)data.get(TAG_LEVEL)).byteValue();
                int exp = ((Integer)data.get(TAG_EXP)).byteValue();
                int type = ((Integer)data.get(TAG_EXP_TYPE)).byteValue();
                itemLore.add("§aLv. §f " + lv + "  §bxp. §f" + exp + "/" + ItemExpTable.getNeedExp(lv, type));
            }
            if(data.containsKey(TAG_ATK)) itemLore.add("§cこうげき§f " + '+' + ((Integer)data.get(TAG_ATK)).byteValue());
            if(data.containsKey(TAG_DEF)) itemLore.add("§9ぼうぎょ§f " + '+' + ((Integer)data.get(TAG_DEF)).byteValue());
            if(data.containsKey(TAG_FOOD)) itemLore.add("§6えいよう§f " + '+' + ((Integer)data.get(TAG_FOOD)).byteValue());
            if(data.containsKey(TAG_SATURATION)) itemLore.add("§6はらもち§f " + '+' + ((Float)data.get(TAG_SATURATION)).byteValue());
        }

        if(!itemLore.isEmpty()){
            String lore = "§r§f";
            for(String s: itemLore){
                lore += s + "\n§r§f";
            }
            item.setLore(lore);
        }

        if(!name.equals("")) item.setCustomName(name);

        return item;
    }

    public static Item create(Integer id, Integer meta, Integer count){
        return create(id, meta, count, new HashMap<String, Object>(), "");
    }

    public static Item create(Integer id, Integer meta, Integer count, String name){
        return create(id, meta, count, new HashMap<String, Object>(), name);
    }

}
