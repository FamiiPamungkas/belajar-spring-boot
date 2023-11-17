package com.famipam.security.demo;

public class ArrayDemo {
    private final static String[] ORGANIZATIONS = {"CPM", "PKF", "KPK", "MBI", "AGI"};

    public static void main(String[] args) {
        String cpm = getOrg(0);
        String kpk = getOrg(2);

        System.out.println("ORG = "+cpm+" - "+kpk);
    }

    private static String getOrg(int index) {
        try {
            return ORGANIZATIONS[index];
        } catch (Exception e) {
            return "CPM";
        }
    }
}
