package com.sitrack.pruebaTecnica.services;

import com.sitrack.pruebaTecnica.models.PositionReport;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class PositionReportFactory {

    @Autowired
    @Qualifier("gpsDop")
    private double[] gpsDops;

    @Autowired
    @Qualifier("degrees")
    private int[] degrees;

    private String loginCode = "98173";
    private String text = "Manuel Carbajal";
    private String textLabel = "TAG";
    private String reportType = "2";


    /**
     * Crea una nueva instancia de {@link PositionReport} con todos los datos necesarios para que sean enviados al
     * servicio REST
     * @return
     */
    public PositionReport createPositioReport() {
        PositionReport report = createBasePositionReport();
        report.setReportDate(getDateWithIso8601(new Date()));
        Position position = Position.getRandomPosition(new Random());
        report.setLatitud(position.latitude);
        report.setLongitude(position.longitude);
        report.setGpsDop(getRandomGpsDop(new Random()));
        report.setHeading(getRandomHeading(new Random()));
        report.setSpeed(getRandomGpsDop(new Random()));
        report.setSpeedlabel("GPS");
        report.setGpsSatellites(10);

        return report;
    }

    /**
     * Constructor necesario para crear un objeto con los datos standar que requiere.
     *
     * @return
     */
    private PositionReport createBasePositionReport() {
        return new PositionReport(loginCode, text, textLabel, reportType);
    }

    /**
     * Transforma una fecha al formato necesario para el envío de la información
     *
     * @param date
     * @return
     */
    private String getDateWithIso8601(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return sdf.format(date);
    }

    /**
     * Selecciona de forma random un valor del array gpsDops
     *
     * @param r
     * @return
     */
    private double getRandomGpsDop(Random r) {
        return gpsDops[r.nextInt(this.gpsDops.length)];
    }

    /**
     * Selecciona de forma random un valor del Array degrees
     * @param r
     * @return
     */
    private int getRandomHeading(Random r) {
        return degrees[r.nextInt(this.degrees.length)];
    }


    /**
     * Clase que contiene la logica para la creación de longitud y latitud para el objeto {@link PositionReport}
     */
    @Data
    private static class Position {

        private String latitude;
        private String longitude;

        /**
         * Crea una instancia de Position con datos random
         * @param r
         * @return
         */
        public static Position getRandomPosition(Random r) {
            Position p = new Position();
            p.latitude = createRandomData(r);
            p.longitude = createRandomData(r);
            return p;
        }

        /**
         * Realiza la selección random de un double para que sea asignado
         * @param r
         * @return
         */
        private static String createRandomData(Random r) {
            double lat = r.nextDouble();
            if(r.nextBoolean()) {
                lat = lat * -10.0;
            } else {
                lat = lat * 10.0;
            }
            return Double.toString(lat);
        }

    }



}
