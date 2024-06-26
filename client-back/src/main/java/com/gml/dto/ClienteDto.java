package com.gml.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ClienteDto {

    private String sharedKey;
    @NotNull(message = "It must not be null")
    private String bussinessId;
    @NotNull(message = "It must not be null")
    private String email;
    @NotNull(message = "It must not be null")
    private String phone;

    private Date added;
    @NotNull(message = "It must not be null")
    private Date started;
    @NotNull(message = "It must not be null")
    private Date ended;


}
