package com.example.projetihm.models;

public class BasketValue {
    private int value;

    public BasketValue () {
        this.value = 1;
    }

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
