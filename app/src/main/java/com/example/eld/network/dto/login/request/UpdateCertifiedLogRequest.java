package com.example.eld.network.dto.login.request;

import java.util.List;

public class UpdateCertifiedLogRequest {
    private List<String> id;

    public UpdateCertifiedLogRequest(List<String> id) {
        this.id = id;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }
}
