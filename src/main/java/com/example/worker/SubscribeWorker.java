package com.example.worker;

import com.example.model.Product;
import com.example.model.User;
import com.example.model.enums.ProductCategory;
import com.example.repository.Listeners;
import com.example.service.StockManager;

import java.util.List;

public class SubscribeWorker {
    public void startApp(){
        Listeners listener = new Listeners();
        StockManager manager = new StockManager(listener);

        User user = new User();
        user.setName("John");
        user.setAge(71);
        Product product = new Product("2", ProductCategory.MEDICAL);
        Product product2 = new Product("4", ProductCategory.MEDICAL);
        Product product4 = new Product("5", ProductCategory.BOOKS);
        Product product5 = new Product("6", ProductCategory.DIGITAL);
        listener.setListeners(user, List.of(product, product2, product4, product5));
        manager.subscribe(user, product2);

        User user5 = new User();
        user5.setName("Peter");
        listener.setListeners(user5, List.of(product2));
        manager.subscribe(user5, product2);

        User user3 = new User();
        user3.setName("Dmytro");
        listener.setListeners(user3, List.of(product2));
        manager.subscribe(user3, product2);

        User user2 = new User();
        user2.setName("Serg");
        user2.setPremium(true);
        Product product3 = new Product("9", ProductCategory.DIGITAL);
        listener.setListeners(user2, List.of(product2, product3));
        manager.subscribe(user2, product3);

        manager.notifyUsers(product2);
    }
}
