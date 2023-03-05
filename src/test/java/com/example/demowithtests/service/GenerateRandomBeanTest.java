package com.example.demowithtests.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateRandomBeanTest {

    @Test
    void getEmployee() {
        for (int i = 0; i <= 10; i++) {
            System.out.println((new GenerateRandomBean()).getEmployee());
        }

    }
}