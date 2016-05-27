package network.social.com.androidfinal.model;

/**
 * Created by Cagdas on 09.05.2016.
 */
public class TaskLocation {
    private String coordinateX;
    private String coordinateY;
    private String delivery;

    public TaskLocation() {
    }

    public TaskLocation(String coordinateX, String coordinateY, String delivery) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.delivery = delivery;
    }

    public String getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(String coordinateX) {
        this.coordinateX = coordinateX;
    }

    public String getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(String coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
