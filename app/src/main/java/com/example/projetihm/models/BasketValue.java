package com.example.projetihm.models;

public class BasketValue {
    private int value = 0;

    public void increment () {
        value++;
    }

    public void decrement() {
        if (value <= 1) {
            value = 0;
        } else {
            value--;
        }
    }
    public int get () {
        return value;
    }
}
