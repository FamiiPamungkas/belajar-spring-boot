package com.famipam.security.demo;

import java.util.Map;

public class MapDemo {

    private static final Map<String,String> MAP = Map.ofEntries(
            Map.entry("1","CPM"),
            Map.entry("2","PKF"),
            Map.entry("3","KPK"),
            Map.entry("4","MBI"),
            Map.entry("5","AGI")
    );

    public static void main(String[] args) {
        String cpm = getOrg("1");
        String kpk = getOrg("3");

        System.out.println("ORG = "+cpm+" - "+kpk);
    }

    private static String getOrg(String id){
        return MAP.getOrDefault(id,"CPM");
    }
}
