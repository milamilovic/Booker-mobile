package com.example.bookingapp.dto.users;

import java.util.Date;
public class UserReportDTO {
    private Long id;
    private Long reportedId;
    private Long reporterId;
    private String reason;
    private Date date;

    public UserReportDTO() {
    }

    public UserReportDTO(Long id, Long reportedId, Long reporterId, String reason, Date date) {
        this.id = id;
        this.reportedId = reportedId;
        this.reporterId = reporterId;
        this.reason = reason;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
