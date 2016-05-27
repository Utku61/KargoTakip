package network.social.com.androidfinal.model;

import java.util.Date;

/**
 * Created by Cagdas on 06.05.2016.
 */
public class Gorev {
    private int coordinateId;
    private int userId;
    private String coordinateX;
    private String coordinateY;
    private String getTimeCoordinate;
    private String reachingTimeCoordinate;
    private String customerName;
    private String customerLastName;
    private String customerTcNo;
    private float cargoPay;
    private String delivery;


    public Gorev() {
    }

    public Gorev(int coordinateId, int userId, String coordinateX, String coordinateY, String getTimeCoordinate, String reachingTimeCoordinate,
                 String customerName, String customerLastName, String customerTcNo, float cargoPay, String delivery) {
        this.coordinateId = coordinateId;
        this.userId = userId;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.getTimeCoordinate = getTimeCoordinate;
        this.reachingTimeCoordinate = reachingTimeCoordinate;
        this.customerName = customerName;
        this.customerLastName = customerLastName;
        this.customerTcNo = customerTcNo;
        this.cargoPay = cargoPay;
        this.delivery = delivery;
    }

    public int getCoordinateId() {
        return coordinateId;
    }

    public void setCoordinateId(int coordinateId) {
        this.coordinateId = coordinateId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(String coordinateX) {
        this.coordinateX = coordinateX;
    }

    public String getGetTimeCoordinate() {
        return getTimeCoordinate;
    }

    public void setGetTimeCoordinate(String getTimeCoordinate) {
        this.getTimeCoordinate = getTimeCoordinate;
    }

    public String getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(String coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getReachingTimeCoordinate() {
        return reachingTimeCoordinate;
    }

    public void setReachingTimeCoordinate(String reachingTimeCoordinate) {
        this.reachingTimeCoordinate = reachingTimeCoordinate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerTcNo() {
        return customerTcNo;
    }

    public void setCustomerTcNo(String customerTcNo) {
        this.customerTcNo = customerTcNo;
    }

    public float getCargoPay() {
        return cargoPay;
    }

    public void setCargoPay(float cargoPay) {
        this.cargoPay = cargoPay;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }


    @Override
    public String toString() {
        return "Gorev{" +
                "coordinateId=" + coordinateId +
                ", userId=" + userId +
                ", coordinateX='" + coordinateX + '\'' +
                ", coordinateY='" + coordinateY + '\'' +
                ", getTimeCoordinate=" + getTimeCoordinate +
                ", reachingTimeCoordinate=" + reachingTimeCoordinate +
                ", customerName='" + customerName + '\'' +
                ", customerLastName='" + customerLastName + '\'' +
                ", customerTcNo='" + customerTcNo + '\'' +
                ", cargoPay=" + cargoPay +
                ", delivery='" + delivery + '\'' +
                '}';
    }
}
