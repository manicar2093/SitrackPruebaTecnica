package com.sitrack.pruebaTecnica.controllers;

import com.sitrack.pruebaTecnica.models.PositionReport;
import com.sitrack.pruebaTecnica.services.PositionReportFactory;
import com.sitrack.pruebaTecnica.services.PositionReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class PositionReportController {

    @Autowired
    private PositionReportService positionReportService;

    @Autowired
    private PositionReportFactory positionReportFactory;

    /**
     * Realiza el proceso de envió de la información cada minuto. Con el fixedDelay aseguramos que ningun otro proceso
     * creado por este Scheduled no se inicie hasta que el anterios haya sido completado
     */
    @Scheduled(fixedDelay = 60000)
    public void sendPositionToService() {
        PositionReport report = positionReportFactory.createPositioReport();
        HttpEntity<PositionReport> request = positionReportService.createHttpEntity(report);

        try {
            positionReportService.sendPosition(request);
        } catch (InterruptedException e) {
            log.error("Error al realizar la espera de la ejecución");
        }
    }

}
