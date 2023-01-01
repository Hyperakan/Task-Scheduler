import java.util.ArrayList;

public class Resource {
    private int id;
    private job currJob;
    private ArrayList<job> jobs;
    private boolean isBusy = false;

    public Resource(int id) {
        this.id = id;
        jobs = new ArrayList<job>();
    }

    //getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public job getCurrJob() {
        return currJob;
    }
    public void setCurrJob(job currJob) {
        this.currJob = currJob;
        jobs.add(currJob);
    }
    public boolean isBusy() {
        return isBusy;
    }
    public void setBusy(boolean busy) {
        isBusy = busy;
    }





}
