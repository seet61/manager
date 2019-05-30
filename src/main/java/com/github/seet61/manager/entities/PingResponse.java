package com.github.seet61.manager.entities;

import lombok.Data;

import java.util.Date;

@Data
public class PingResponse {
    private final Date date;
    private final String status;

    public PingResponse(Date date, String status) {
        this.date = date;
        this.status = status;
    }
}
