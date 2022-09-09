package com.learning.readinglist.util;

public enum EnBookType {
    Science("science"),
    Software("software"),
    Religious("religious"),
    Guide("guide"),
    History("history"),
    Art("art"),
    Lab("lab");

    private String value;

    EnBookType(String value) {
        this.value =value;
    }
}
