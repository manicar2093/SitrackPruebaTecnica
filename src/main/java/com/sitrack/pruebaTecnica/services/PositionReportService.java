package com.sitrack.pruebaTecnica.services;

import com.google.gson.Gson;
import com.sitrack.pruebaTecnica.models.PositionReport;
import com.sitrack.pruebaTecnica.models.SitrackFailedResponse;
import com.sitrack.pruebaTecnica.models.SitrackSuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class PositionReportService {

    @Autowired
    @Qualifier("MD5")
    MessageDigest md;

    private String urlService = "https://test-externalrgw.ar.sitrack.com/frame";
    private String application = "ReportGeneratorTest";
    private String secretKey = "ccd517a1-d39d-4cf6-af65-28d65e192149";

    /**
     * Realiza el envío de la informacíon al urlService.
     * @param data
     * @throws InterruptedException
     */
    public void sendPosition(HttpEntity<PositionReport> data) throws InterruptedException {
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<String> response;
        SitrackSuccessResponse sitrackSuccessResponse;
        SitrackFailedResponse sitrackFailedResponse;

        try{
            response = rt.exchange(this.urlService,
                    HttpMethod.PUT,
                    data,
                    String.class);
        } catch (Exception e){
            log.error("Error al realizar la petición al servidor. Detalles: ", e);
            throw e;
        }

        if(response.getStatusCode().equals(HttpStatus.OK)) {
            sitrackSuccessResponse = new Gson().fromJson(response.getBody(), SitrackSuccessResponse.class);
            log.info("Petición exitosa!");
            log.info(sitrackSuccessResponse.toString());
            return;
        }

        if (response.getStatusCode().equals(HttpStatus.TOO_MANY_REQUESTS) ||
            response.getStatusCode().value() >= HttpStatus.INTERNAL_SERVER_ERROR.value()) {

            sitrackFailedResponse = new Gson().fromJson(response.getBody(), SitrackFailedResponse.class);
            log.info("Respuesta con error", sitrackFailedResponse.toString());
            log.info(sitrackFailedResponse.toString());

            // Esperamos 10 segundos para continuar con la ejecución.
            Thread.sleep(10000);
            //Cuando termina la espera se hace una recursión a este método
            sendPosition(data);
        }

    }

    /**
     * Crea la estructura de la solicitud con todos los headers y body necesarios
     *
     * @param positionReport
     * @return
     */
    public HttpEntity<PositionReport> createHttpEntity(PositionReport positionReport) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", createAuthorizationHeader());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<PositionReport>(positionReport, headers);
    }


    /**
     * Crea el standar solicitado para la cabecera Authorization para que la solicitud sea aceptada
     * @return
     */
    private String createAuthorizationHeader() {
        long timestamp = new Date().getTime() / 1000L;
        String hash = createSignature(timestamp);
        String base = "SWSAuth application=\"%s\", signature=\"%s\",timestamp=\"%s\"";
        return String.format(base, this.application, hash, timestamp);
    }

    /**
     * Crea el signature requerido para el cabecero Authorization
     * @param timestamp
     * @return
     */
    private String createSignature(long timestamp) {
        String toEncode = String.format("%s%s%s", this.application, this.secretKey, timestamp);
        byte[] toMd5 = this.md.digest(toEncode.getBytes());
        return Base64.getEncoder().encodeToString(toMd5);

    }

}
