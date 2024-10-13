package com.org.seoulpublicwifi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookMarkDto {
    private int id;
    private int groupId;
    private String mgrNo;
    private String register_date;
}
