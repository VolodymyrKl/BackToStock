package com.example.repository;

import com.example.model.Product;
import com.example.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Listeners {
    private Map<User, List<Product>> listeners;

    public Map<User, List<Product>> getListeners() {
        listeners = new HashMap<>();
        return listeners;
    }

    public void setListeners(User user, List<Product> products) {
        listeners.put(user, products);
    }
}
