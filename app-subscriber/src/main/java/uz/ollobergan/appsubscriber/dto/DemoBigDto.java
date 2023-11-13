package uz.ollobergan.appsubscriber.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DemoBigDto {
    private String reportId;
    private String tin;
    private int na2Id;
    private int year;
    private int ns10Code;
    private int ns11Code;
    private String periodType;
    private int period;
    private ReportStateDto reportState;
    private String packageId;
    private int type;
    private Date created;
    private Object updated;
    private int legacyId;
    private int packageGroupId;
    private int packageCode;
    private boolean deleted;
    private Object docContentType;
    private Object docExt;
    private List<ReportCellDto> reportCells;
    private String ns10Name;
    private String ns11Name;
    private String tinName;
    private String na2Name;
    private String periodName;
    private boolean hasKlsCells;
    private boolean hasAttachment;
    private int mriCode;
}
