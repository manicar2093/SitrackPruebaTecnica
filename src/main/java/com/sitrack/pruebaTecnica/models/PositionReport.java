package com.sitrack.pruebaTecnica.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class PositionReport {

    private String loginCode;
    private String reportDate;
    private String reportType;
    private String latitud;
    private String longitude;
    private Double gpsDop;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Double speed;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer heading;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String speedlabel;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer gpsSatellites;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String text;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String textlabel;

    public PositionReport(String loginCode, String text, String textLabel, String reportType) {
        this.loginCode = loginCode;
        this.text = text;
        this.textlabel = textLabel;
        this.reportType = reportType;
    }
}
