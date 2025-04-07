package org.example;

import org.example.db.DatabaseInitializer;
import org.example.entities.User;
import org.example.repository.UserRepository;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        Scanner sc = new Scanner(System.in);
        boolean leave = false;
        int option;

        while (!leave) {
            System.out.println("Enter 1 to add a user, 2 to list all users, or 3 to exit.");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    DatabaseInitializer.init();

                    UUID id = UUID.randomUUID();
                    User newUser = new User(id, "João da Silva", "joao@example.com", "senha123");
                    userRepository.save(newUser);
                    break;
                case 2:
                    List<User> users = userRepository.findAll();
                    try {
                        for (User user : users) {
                            System.out.println("Name: " + user.getName() + " | Email: " + user.getEmail());
                        }
                    } catch (Exception e) {
                        System.out.println("Database is empty");
                    }
                    break;
                default:
                    System.out.println("Invalid option");
                    leave = true;
                    break;
            }
        }
    }
}