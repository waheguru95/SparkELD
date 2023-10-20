package com.example.eld.network.dto.login.response;

public class AddAttendanceRecordResponseModel {
    private int statusCode;
    private String message;
    private int data;
    private boolean status;

    // Default constructor (required for deserialization)
    public AddAttendanceRecordResponseModel() {
    }

    // Getter and setter methods for the fields
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}
