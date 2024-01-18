package com.example.bookingapp.model;

import com.example.bookingapp.enums.NotificationType;

public class NotificationListing {
    private Long id;
    private Long userId;
    private String time;
    private String content;
    private NotificationType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
