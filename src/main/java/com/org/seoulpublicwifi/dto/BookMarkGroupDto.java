package com.org.seoulpublicwifi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookMarkGroupDto {
    private int id;
    private String name;
    private int order;
    private String register_date;
    private String update_date;
}
