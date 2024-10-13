package com.org.seoulpublicwifi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class HistoryDto {
    private int id;                     //id
    private String lat;                 //lat
    private String lnt;                 //lnt
    private String searchDate;          //조회일자

}
