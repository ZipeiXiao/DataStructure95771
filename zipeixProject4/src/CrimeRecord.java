public class CrimeRecord {
    private double x;
    private double y;
    private int time;
    private String street;
    private String offense;
    private String date;
    private String tract;
    private double latitude;
    private double longitude;

    public CrimeRecord(double x, double y, int time, String street, String offense, String date, String tract, double latitude, double longitude) {
        this.x = x;
        this.y = y;
        this.time = time;
        this.street = street;
        this.offense = offense;
        this.date = date;
        this.tract = tract;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getOffense() {
        return offense;
    }

    public void setOffense(String offense) {
        this.offense = offense;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTract() {
        return tract;
    }

    public void setTract(String tract) {
        this.tract = tract;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
