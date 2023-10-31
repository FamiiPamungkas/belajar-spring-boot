package com.famipam.security.controller.api;

import java.util.Map;
import java.util.stream.Collectors;

public class BaseController {
    final public static int SUCCESS_CODE = 200;
    final public static String SUCCESS = "SUCCESS";

    protected Map<String, Object> findDifference(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, Object> diff;
        diff = map2.entrySet().stream()
                .filter(m -> !m.getValue().equals(map1.get(m.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return diff;
    }
}
