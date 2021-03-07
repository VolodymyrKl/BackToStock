package com.example.service;

import com.example.model.Product;
import com.example.model.User;
import com.example.model.enums.ProductCategory;
import com.example.repository.Listeners;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class StockManager implements BackToStockService {
    private final Map<User, List<Product>> allUsers;
    private final Map<User, List<Product>> subscribers;

    public StockManager(Listeners listener) {
        allUsers = listener.getListeners();
        subscribers = new HashMap<>();
    }

    @Override
    public void subscribe(User user, Product product) {
        if (allUsers.containsKey(user)) {
            List<Product> products = new ArrayList<>();
            for (int i = 0; i < allUsers.get(user).size(); i++) {
                if (allUsers.get(user).get(i).equals(product)) {
                    products.add(product);
                    int index = subscribedUsers(product).indexOf(user);
                    User currentUser = subscribedUsers(product).get(index);
                    subscribers.put(currentUser, products);
                }
            }
        }
    }

    @Override
    public List<User> subscribedUsers(Product product) {
        return allUsers.entrySet()
                .stream()
                .filter(p -> p.getValue().contains(product))
                .map(Entry::getKey)
                .collect(Collectors.toList());
    }

    public void notifyUsers(Product product) {
        List<User> currentUsers = new ArrayList<>(subscribers.keySet());
        notifyUsersWithPrime(product, currentUsers);
        notifyUsersElderThan(product, currentUsers);
        notifyUsersMedical(product, currentUsers);
        update(currentUsers, product);
    }

    private void notifyUsersMedical(Product product, List<User> currentUsers) {
        if (product.getCategory().equals(ProductCategory.MEDICAL)) {
            update(currentUsers, product);
            currentUsers.clear();
        }
    }

    private void notifyUsersElderThan(Product product, List<User> currentUsers) {
        int agePriority = 70;
        List<User> userElderThan = currentUsers
                .stream()
                .filter(u -> u.getAge() > agePriority)
                .collect(Collectors.toList());
        updateListIfExit(product, currentUsers, userElderThan);
    }

    private void notifyUsersWithPrime(Product product, List<User> currentUsers) {
        List<User> premiumUser = currentUsers
                .stream()
                .filter(User::isPremium)
                .collect(Collectors.toList());
        updateListIfExit(product, currentUsers, premiumUser);
    }

    private void updateListIfExit(Product product, List<User> currentUsers, List<User> matchedUsers) {
        if (!matchedUsers.isEmpty()) {
            update(matchedUsers, product);
            currentUsers.removeAll(matchedUsers);
        }
    }

    public void update(List<User> users, Product product) {
        for (User user : users) {
            System.out.println(user.getName() + " is notified, " +
                    "when product id " + product.getId() + " with type " + product.getCategory() + " come back to stock.");
        }
    }
}
