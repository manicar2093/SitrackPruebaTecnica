package com.sitrack.pruebaTecnica.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PositionReportControllerTest {

    @Autowired
    private PositionReportController positionReportController;

    @Test
    void sendPositionToService() {
        positionReportController.sendPositionToService();
    }
}