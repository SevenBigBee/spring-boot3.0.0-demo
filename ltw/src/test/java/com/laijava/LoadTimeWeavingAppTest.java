package com.laijava;

import com.laijava.example.Cat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoadTimeWeavingAppTest {
    @Test
    void contextLoads() {
        Cat c  = new Cat();
        System.out.println(c.noise());
    }
}