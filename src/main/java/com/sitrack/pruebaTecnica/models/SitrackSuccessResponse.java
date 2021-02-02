package com.sitrack.pruebaTecnica.models;

import lombok.Data;

/**
 * Representación del mensaje exitoso del servicio
 */
@Data
public class SitrackSuccessResponse {

    private String response;
    private String ack;

}
