package com.woorizip.woorizip;

public class Test {

    public static void main(String[] args) {
        test(EnumTest.aa);
    }

    public static void test(Enum<?> e){
        System.out.println("e = " + e);
    }
}

enum EnumTest {
    aa,bb,cc,dd;
}
