package easyapps.ms.com.config_annotation.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import easyapps.ms.com.config_annotation.model.AbModel;

public class LocalMap {
    private static final Map<String, Object> localMap = new HashMap<>();

    public static Map<String, Object> getLocalMap() {
        return localMap;
    }

    public static void storeData(Map<String, List<AbModel>> map) {
        localMap.clear();
        for (Map.Entry<String, List<AbModel>> entry : map.entrySet()) {
            for (AbModel model : entry.getValue()) {
                localMap.put(model.getFieldName(), model.getData());
            }
        }
    }

    public static void clearData(){
         localMap.clear();
    }

}
