
public class job {
    private int arrivalTime;
    private int id;
    private int duration;
    private String priority;

    public job(int id, int duration, int arrivalTime) {
        this.id = id;
        this.duration = duration;
        this.arrivalTime = arrivalTime;
    }
    //getters and setters
    public int getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return ""+id;
    }
}