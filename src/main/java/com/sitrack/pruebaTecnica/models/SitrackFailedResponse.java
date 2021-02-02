package com.sitrack.pruebaTecnica.models;

import lombok.Data;

/**
 * Representación de la respuesta de error del servicio
 */
@Data
public class SitrackFailedResponse {

    private String userMessage;
    private Integer errorCode;
    private String detailedMessage;
    private Integer responseCode;

}
