package com.famipam.security.controller.api;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseController {
    final public static int SUCCESS_CODE = 200;
    final public static String SUCCESS = "SUCCESS";

    protected Map<String, Object> findDifference(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, Object> diff = new LinkedHashMap<>();

        // stream method
        Map<String, Object> filtered = map2.entrySet().stream()
                .peek(m -> {
                    if (m.getValue() == null && map1.get(m.getKey()) != null) diff.put(m.getKey(), null);
                }).filter(m -> m.getValue() != null && !m.getValue().equals(map1.get(m.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        diff.putAll(filtered);


        // loop method
//        for (Map.Entry<String, Object> m : map2.entrySet()) {
//            if (m.getValue() == null && map1.get(m.getKey()) != null) {
//                diff.put(m.getKey(), m.getValue());
//                continue;
//            }
//            if (m.getValue() == null) continue;
//            if (!m.getValue().equals(map1.get(m.getKey()))) {
//                diff.put(m.getKey(), m.getValue());
//            }
//        }
        return diff;
    }
}
