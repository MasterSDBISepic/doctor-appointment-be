package com.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private Long id;
    private String name;
    private Integer startMonday;
    private Integer endMonday;
    private Integer startTuesday;
    private Integer endTuesday;
    private Integer startWednesday;
    private Integer endWednesday;
    private Integer startThursday;
    private Integer endThursday;
    private Integer startFriday;
    private Integer endFriday;
    private Integer startSaturday;
    private Integer endSaturday;
    private Integer startSunday;
    private Integer endSunday;
}
