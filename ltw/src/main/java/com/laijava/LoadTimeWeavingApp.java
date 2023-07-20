package com.laijava;

import com.laijava.example.Cat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
 public class LoadTimeWeavingApp {
    public static void main(String[] args) {
        try {
            SpringApplication.run(LoadTimeWeavingApp.class, args);
            Cat c = new Cat();
            System.out.println(c.noise());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

