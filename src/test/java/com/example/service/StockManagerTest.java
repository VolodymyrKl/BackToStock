package com.example.service;

import com.example.model.Product;
import com.example.model.User;
import com.example.model.enums.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StockManagerTest {
    User user = new User();
    Product product = new Product("2", ProductCategory.BOOKS);
    Map<User, List<Product>> allUsers;
    Map<User, List<Product>> subscribers;


    @BeforeEach
    void implementDBForTesting() {
        allUsers = new HashMap<>();
        subscribers = new HashMap<>();
        user.setPremium(true);
        allUsers.put(user, List.of(new Product("19", ProductCategory.BOOKS)));
        allUsers.put(new User(), List.of(new Product("19", ProductCategory.DIGITAL),
                new Product("36", ProductCategory.BOOKS)));
        allUsers.put(new User(), List.of(new Product("19", ProductCategory.MEDICAL)));
        allUsers.put(user, List.of(new Product("19", ProductCategory.MEDICAL), product));
    }

    @Test
    void shouldSubscribeUserToProduct() {
        if (allUsers.containsKey(user)) {
            subscribers.put(user, List.of(product));
        }
        assertFalse(subscribers.isEmpty());
    }

    @Test
    void shouldGetSubscribedUsers() {
        List<User> subscribedUsers = allUsers.entrySet()
                .stream()
                .filter(p -> p.getValue().contains(product))
                .map(Entry::getKey)
                .collect(Collectors.toList());
        assertFalse(subscribedUsers.isEmpty());
    }

    @Test
    void shouldNotifyUsersIfKnownThatProductWillAvailable() {
        User user1 = new User();
        user1.setAge(72);
        User user2 = new User();
        user2.setPremium(true);
        List<User> users = List.of(user1, user2);
        notifyElderThan(users);
        notifyThatIsPremiumUser(users);
    }

    private void notifyThatIsPremiumUser(List<User> users) {
        boolean isNotified;
        isNotified = false;
        List<User> premiumUser = users.stream()
                .filter(User::isPremium)
                .collect(Collectors.toList());
        if (!premiumUser.isEmpty()) {
            isNotified = true;
        }
        assertTrue(isNotified);
    }

    private void notifyElderThan(List<User> users) {
        boolean isNotified = false;
        List<User> userElderThan = users.stream()
                .filter(u -> u.getAge() > 70)
                .collect(Collectors.toList());
        if (!userElderThan.isEmpty()) {
            isNotified = true;
        }
        assertTrue(isNotified);
    }
}