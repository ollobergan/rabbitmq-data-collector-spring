package uz.ollobergan.appsubscriber.dto;

import lombok.Data;

@Data
public class ReportCellDto {
    private int listNo;
    private int rowNo;
    private int colNo;
    private boolean numeric;
    private String value;
}
