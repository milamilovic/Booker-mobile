package com.example.bookingapp.dto.users;

public class CreateReportUserDTO {
    private Long reportedId;
    private Long reporterId;
    private String reason;

    public CreateReportUserDTO() {
    }

    public CreateReportUserDTO(Long reportedId, Long reporterId, String reason) {
        this.reportedId = reportedId;
        this.reporterId = reporterId;
        this.reason = reason;
    }

    public Long getReportedId() {
        return reportedId;
    }

    public void setReportedId(Long reportedId) {
        this.reportedId = reportedId;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
