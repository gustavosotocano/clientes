package com.gml.entity;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Setter
@Getter
public class Client {
    @Id
    private String sharedKey;
    private String email;
    private String phone;
    private Date added;
    private Date started;
    private Date ended;
    private String bussinessId;
    private Date updated;
}
