package com.gml.util;

import com.gml.dto.ClienteDto;
import com.gml.entity.Client;

import java.util.Date;

public class Utils {
    public static Client toClient(ClienteDto clienteDto) {
        return Client.builder()
                .sharedKey(clienteDto.getSharedKey())
                .bussinessId(clienteDto.getBussinessId())
                .email(clienteDto.getEmail())
                .phone(clienteDto.getPhone())
                .added(clienteDto.getAdded())
                .started(clienteDto.getStarted())
                .ended(clienteDto.getEnded())

                .build();
    }

    public static Client toClientUpdate(ClienteDto from ,Client to) {
        to.setEnded(from.getEnded());
        to.setEmail(from.getEmail());
        to.setPhone(from.getPhone());
        to.setStarted(from.getStarted());
        to.setBussinessId(from.getBussinessId());
        to.setUpdated(new Date());
        return to;
    }
}
