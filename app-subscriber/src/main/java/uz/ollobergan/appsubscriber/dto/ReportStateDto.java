package uz.ollobergan.appsubscriber.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties
public class ReportStateDto {
    private int stateId;
    private String id;
    private String reportId;
    private String inspectorId;
    private String inspectorName;
    private Date created;
    private String host;
    private String clientIp;
    private boolean onTime;
    private Date dateSent;
    private String dataSource;
    private int certificateId;
    private boolean inspector;
    private Object errors;
    private Object errorReason;
    private int klsReportType;
}
