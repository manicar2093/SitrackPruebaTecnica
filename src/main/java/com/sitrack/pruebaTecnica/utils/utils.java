package com.sitrack.pruebaTecnica.utils;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@Component
@Log
public class utils {

    /**
     * Crea un bean para {@link MessageDigest} del algoritmo MD5
     *
     * @return MessageDigest
     */
    @Bean("MD5")
    public MessageDigest createMD5Bean() {
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.info("Error creando bean de MD5. Detalles: ");
            log.info(e.toString());
        }
        return md;
    }

    /**
     * Crea un Array de int para la asignación aleatoria del heading de {@link com.sitrack.pruebaTecnica.models.PositionReport}
     * @return
     */
    @Bean("degrees")
    public int[] createDegreesBean(){
        return IntStream.range(0, 359).toArray();
    }

    /**
     * Crea un Array de double para la asignación aleatoria del gpsDop de {@link com.sitrack.pruebaTecnica.models.PositionReport}
     * @return
     */
    @Bean("gpsDop")
    public double[] createGpsDopBean(){
        return IntStream.range(0, 20).asDoubleStream().toArray();
    }

}
