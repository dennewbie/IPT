package com.prog3.ipt.Model.LineRide;

import java.time.LocalDate;
import java.util.Objects;

public class Notice {
    private String noticeID;
    private LocalDate noticeDate;
    private String noticeName;
    private String noticeText;
    private String rideID;
    private String lineID;



    public Notice(String noticeID, LocalDate date, String noticeName, String text, String rideID, String lineID) {
        this.noticeID = noticeID;
        this.noticeDate = date;
        this.noticeName = noticeName;
        this.noticeText = text;
        this.rideID = rideID;
        this.lineID = lineID;
    }

    // Setters
    public void setNoticeID(String noticeID) { this.noticeID = noticeID; }
    public void setNoticeDate(LocalDate date) { this.noticeDate = date; }
    public void setNoticeName(String noticeName) { this.noticeName = noticeName; }
    public void setNoticeText(String text) { this.noticeText = text; }
    public void setRideID(String rideID) { this.rideID = rideID; }
    public void setLineID(String lineID) { this.lineID = lineID; }

    // Getters
    public String getNoticeID() { return noticeID; }
    public LocalDate getNoticeDate() { return noticeDate; }
    public String getNoticeName() { return noticeName; }
    public String getNoticeText() { return noticeText; }
    public String getRideID() { return rideID; }
    public String getLineID() { return lineID; }

    // Others
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notice)) return false;
        Notice notice = (Notice) o;
        return getNoticeID().equals(notice.getNoticeID()) && getNoticeDate().equals(notice.getNoticeDate()) && getNoticeName().equals(notice.getNoticeName()) && getNoticeText().equals(notice.getNoticeText()) && getRideID().equals(notice.getRideID()) && getLineID().equals(notice.getLineID());
    }
    @Override
    public int hashCode() { return Objects.hash(getNoticeID(), getNoticeDate(), getNoticeName(), getNoticeText(), getRideID(), getLineID()); }
    @Override
    public String toString() { return "Notice{ noticeID='" + noticeID + '\'' + ", noticeDate=" + noticeDate + ", noticeName='" + noticeName + '\'' + ", noticeText='" + noticeText + '\'' + ", rideID='" + rideID + '\'' + ", lineID='" + lineID + '\'' + '}'; }
}
