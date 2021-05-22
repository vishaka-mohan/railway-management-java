package sample;

public class Journey {

    private String train_number;
    private String train_name;
    private String source;
    private String destination;
    private String days;
    private String departure;
    private String arrival;
    private String distance;
    private String duration;

    public Journey(){
        this.train_name ="";
        this.train_number="";
        this.source="";
        this.destination="";
        this.days ="";
        this.departure="";
        this.arrival="";
        this.distance="";
        this.duration="";

    }

    public Journey(String train_number, String train_name, String source, String destination, String days,  String departure,String arrival, String distance, String duration){
        this.train_name = train_name;
        this.train_number= train_number;
        this.source= source;
        this.destination= destination;
        this.days = days;
        this.departure= departure;
        this.arrival= arrival;
        this.distance= distance;
        this.duration= duration;
    }



    public String getTrain_number() {
        return train_number;
    }

    public void setTrain_number(String train_number) {
        this.train_number = train_number;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTrain_name() {
        return train_name;
    }

    public void setTrain_name(String train_name) {
        this.train_name = train_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
