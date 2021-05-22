package sample;

public class Passenger {

    private String train_number;
    private String pid;
    private String name;
    private String age;
    private String gender;
    private String type;
    private String pnr;
    private String travelDate;
    private String seat_number;
    private String wl_number;

    public Passenger(){
        this.train_number = "";
        this.pid = "";
        this.name = "";
        this.age = "";
        this.gender = "";
        this.type = "";
        this.pnr = "";
        this.travelDate = "";
        this.seat_number = "";
        this.wl_number = "";
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    public String getWl_number() {
        return wl_number;
    }

    public void setWl_number(String wl_number) {
        this.wl_number = wl_number;
    }

    public Passenger(String train_number, String pid, String name, String age, String gender, String type, String pnr, String travelDate, String seat_number, String wl_number){

        this.train_number = train_number;
        this.pid = pid;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.type = type;
        this.pnr = pnr;
        this.travelDate = travelDate;
        this.seat_number = seat_number;
        this.wl_number = wl_number;
    }

    public String getTrain_number() {
        return train_number;
    }

    public void setTrain_number(String train_number) {
        this.train_number = train_number;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }
}
