import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
/*
Hakan Dogan 211401018
*/
//job class
class job {
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


//resource class
class Resource {
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

//scheduler class
public class JobScheduler {
    // Array based Min heap class
    public class MinHeap {
        
        //instance variables
        private job[] heap;
        private int size;
        private int maxsize;

        // constructor
        public MinHeap(int maxsize) {
            this.maxsize = maxsize;
            this.size = 0;
            heap = new job[this.maxsize + 1];
            heap[0] = new job(0, 0, 0);
        }

        // return parent index
        private int parent(int pos) {
            return pos / 2;
        }
        //resize heap if it is full
        private void resize() {
            job[] temp = new job[heap.length * 2];
            for (int i = 0; i < heap.length; i++) {
                temp[i] = heap[i];
            }
            heap = temp;
        }

        // return left child index
        private int leftChild(int pos) {
            return (2 * pos);
        }

        // return right child index
        private int rightChild(int pos) {
            return (2 * pos) + 1;
        }

        // check if node is leaf
        private boolean isLeaf(int pos) {
            if (pos >= (size / 2) && pos <= size) {
                return true;
            }
            return false;
        }

        // contains method for min heap
        public boolean contains(job j) {
            for (int i = 1; i <= size; i++) {
                if (heap[i].getId() == j.getId()) {
                    return true;
                }
            }
            return false;
        }

        // swap two nodes
        private void swap(int fpos, int spos) {
            job tmp;
            tmp = heap[fpos];
            heap[fpos] = heap[spos];
            heap[spos] = tmp;
        }

        // min heapify for arriving time
        private void minHeapify(int pos) {
            if (!isLeaf(pos)) {
                if (heap[pos].getArrivalTime() > heap[leftChild(pos)].getArrivalTime()
                        || heap[pos].getArrivalTime() > heap[rightChild(pos)].getArrivalTime()) {
                    if (heap[leftChild(pos)].getArrivalTime() < heap[rightChild(pos)].getArrivalTime()) {
                        swap(pos, leftChild(pos));
                        minHeapify(leftChild(pos));
                    } else {
                        swap(pos, rightChild(pos));
                        minHeapify(rightChild(pos));
                    }
                }
            }
        }

        private void minHeapifyUltra() {
            boolean cont = true;
            int walker = 1;
            while (cont) {
                if (leftChild(walker) <= size
                        && heap[walker].getArrivalTime() > heap[leftChild(walker)].getArrivalTime()) {
                    if (rightChild(walker) <= size
                            && heap[leftChild(walker)].getArrivalTime() < heap[rightChild(walker)].getArrivalTime()) {
                        swap(walker, leftChild(walker));
                        walker = leftChild(walker);
                    } else if (rightChild(walker) <= size
                            && heap[leftChild(walker)].getArrivalTime() > heap[rightChild(walker)].getArrivalTime()) {
                        swap(walker, rightChild(walker));
                        walker = rightChild(walker);
                    } else {
                        swap(walker, leftChild(walker));
                        walker = leftChild(walker);
                    }
                } else if (rightChild(walker) <= size
                        && heap[walker].getArrivalTime() < heap[rightChild(walker)].getArrivalTime()) {
                    swap(walker, rightChild(walker));
                    walker = rightChild(walker);
                } else {
                    cont = false;
                }
            }
        }

        // insert a node into the heap
        public void insert(job element) {
            if(size + 1 == maxsize){
                resize();
            }
            heap[++size] = element;
            int current = size;
            while (heap[current].getArrivalTime() < heap[parent(current)].getArrivalTime()) {
                swap(current, parent(current));
                current = parent(current);
            }
        }

        

        // print heap one line
        public void dandikPrint() {
            for (int i = 1; i <= size; i++) {
                System.out.print(heap[i].getId() + " ");
            }
            System.out.println();
        }

        // build the min heap using the minHeapifys
        public void minHeap() {
            for (int pos = (size / 2); pos >= 1; pos--) {
                minHeapify(pos);
            }
        }

        // method for sorting heap with remaining time
        public void sort() {
            job temp;
            for (int i = 1; i < size; i++) {
                for (int j = 1; j < size - i; j++) {
                    if (heap[j].getId() > heap[j + 1].getId()) {
                        temp = heap[j];
                        heap[j] = heap[j + 1];
                        heap[j + 1] = temp;
                    }
                }
            }
        }

        // remove and return the minimum element from the heap
        public job remove() {
            job popped = heap[1];
            heap[1] = heap[size--];
            minHeapify(1);
            return popped;
        }

        // poll method for min heap array based
        public job poll() {
            job popped = heap[1];
            heap[1] = heap[size--];
            minHeapifyUltra();
            return popped;
        }

        // return the minimum element from the heap
        public job peek() {
            return heap[1];
        }

        // return the size of the heap
        public int size() {
            return size;
        }

        // check if the heap is empty
        public boolean isEmpty() {
            return size == 0;
        }

        // method for toString heap
        @Override
        public String toString() {
            int maxDepth = (int) (Math.log(size) / Math.log(2));

            StringBuilder ab = new StringBuilder();
            for (int d = maxDepth; d >= 0; d--) { 
                int layerLength = (int) Math.pow(2, d); 

                StringBuilder line = new StringBuilder(); 
                for (int i = layerLength; i < (int) Math.pow(2, d + 1); i++) {

                    if (d != maxDepth) {
                        line.append(" ".repeat((int) Math.pow(2, maxDepth - d)));
                    }

                    int loops = maxDepth - d;
                    if (loops >= 2) {
                        loops -= 2;
                        while (loops >= 0) {
                            line.append(" ".repeat((int) Math.pow(2, loops)));
                            loops--;
                        }
                    }

                    if (i <= size) {
                        line.append(String.format("%-2s", heap[i]));
                    } else {
                        line.append("--");
                    }

                    line.append(" ".repeat((int) Math.pow(2, maxDepth - d)));

                    loops = maxDepth - d;
                    if (loops >= 2) {
                        loops -= 2;
                        while (loops >= 0) {
                            line.append(" ".repeat((int) Math.pow(2, loops)));
                            loops--;
                        }
                    }
                }
                ab.insert(0, line.toString() + "\n"); 
            }
            return ab.toString();
        }

    }
    //instance variables
    public MinHeap schedulerTree;
    public Integer timer = 0;
    public String filePath;
    public HashMap<Integer, ArrayList<Integer>> dependencyMap;                                                            
    public ArrayList<Resource> resources;
    public ArrayList<job> jobList;
    public ArrayList<job> tempJobList;
    public ArrayList<String> lineList;
    public ArrayList<job> processedJobs;
    public ArrayList<DPnode> dependencyBlockedJobs;
    public ArrayList<job> resourceBlockedJobs;
    public StringBuilder allTimeString;

    //constructor
    public JobScheduler(String filePath) {
        this.filePath = filePath;
        schedulerTree = new MinHeap(30);
        dependencyMap = new HashMap<Integer, ArrayList<Integer>>();
        lineList = new ArrayList<String>();
        readAndStore(filePath);
        lineList.trimToSize();
        resources = new ArrayList<Resource>();
        jobList = new ArrayList<job>();
        tempJobList = new ArrayList<job>();
        processedJobs = new ArrayList<job>();
        dependencyBlockedJobs = new ArrayList<DPnode>();
        resourceBlockedJobs = new ArrayList<job>();
        allTimeString = new StringBuilder();
        allTimeString.append("     R1  R2\n");
    }

    //method for inserting given dependencies into program
    public void insertDependencies(String dependencyPath) {
        File file = new File(dependencyPath);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] lineArray = line.split(" ");
            int jobId = Integer.parseInt(lineArray[0]);
            if (dependencyMap.get(jobId) != null)
                dependencyMap.get(jobId).add(Integer.parseInt(lineArray[1]));
            else {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(Integer.parseInt(lineArray[1]));
                dependencyMap.put(jobId, temp);
            }
            

        }
        sc.close();
    }

    //method for reading and storing the file
    public void readAndStore(String path) {
        lineList.clear();
        File file = new File(path);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            lineList.add(line);
        }
        sc.close();
    }

    //is our job.txt continiues
    private int s = -1;
    public boolean stillContinues() {
        s++;
        if (s < lineList.size())
            return true;

        return false;
    }

    //run method for running the program
    public void run() {
        for (Resource r : resources) {
            if (r.getCurrJob() != null)
                r.getCurrJob().setDuration(r.getCurrJob().getDuration() - 1);

            if (r.getCurrJob() != null && r.getCurrJob().getDuration() == 0) {
                processedJobs.add(r.getCurrJob());
                r.setBusy(false);
                r.setCurrJob(null);
            }
        }

        setDependencyBlockedJobs();
        MinHeap temp = new MinHeap(schedulerTree.maxsize);
        boolean b = false;
        resourceBlockedJobs.clear();
        while (!schedulerTree.isEmpty()) {
            b = false;
            job j = schedulerTree.poll();
            if (!this.DPContain(j)) {
                for (Resource r : resources) {
                    if (!r.isBusy()) {
                        r.setCurrJob(j);
                        r.setBusy(true);
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    resourceBlockedJobs.add(j);
                    temp.insert(j);
                }
            } else {
                temp.insert(j);
            }
        }

        schedulerTree = temp;
        temp = null;
        tempJobList.clear();

        //timer++;

        allTimeString.append(timer + "     ");

        for (Resource r : resources) {
            if (r.getCurrJob() != null)
                allTimeString.append(r.getCurrJob().getId() + "   ");
            else
                allTimeString.append("    ");
        }

        allTimeString.append("\n");

        readAndStore(filePath);
    }

    //method for setting resource count
    public void setResourcesCount(Integer count) {
        for (int i = 1; i <= count; i++) {
            resources.add(new Resource(i));
        }

    }

    //method for inserting job
    public void insertJob() {
        if (!lineList.get(s).equals("no job")) {
            String[] lineArray = lineList.get(s).split(" ");
            int id = Integer.parseInt(lineArray[0]);
            int duration = Integer.parseInt(lineArray[1]);
            job j = new job(id, duration, timer);
            schedulerTree.insert(j);
            jobList.add(j);
        }
         timer++;
    }

    //method for printing completed jobs
    public void completedJobs() {
        System.out.print("Completed Jobs: ");
        for (job j : processedJobs) {

            System.out.print(j.getId() + ", ");
        }
        System.out.println();

    }

    //method for setting dependency blocked jobs
    public void setDependencyBlockedJobs() {
        ArrayList<job> tempList = new ArrayList<job>();

        for (job j : jobList) {
            tempList.add(j);
        }

        tempList.removeAll(processedJobs);

        for (job j : tempList) {
            ArrayList<Integer> dList = dependencyMap.get(j.getId());
            if (dList != null) {
                for (int i : dList) {
                    for (job k : jobList) {
                        if (k.getId() == i) {
                            if ((!processedJobs.contains(k)) && !DPContain(j, k)) {
                                dependencyBlockedJobs.add(new DPnode(j, k));
                            } else if (processedJobs.contains(k) && DPContain(j, k)) {
                                DPRemove(j, k);
                            }
                        }
                    }

                }
            }
        }

    }

    //method for printing dependencyBlockedJobs
    public void dependencyBlockedJobs() {
        System.out.print("Dependency Blocked Jobs: ");
        for (DPnode j : dependencyBlockedJobs) {
            System.out.print("(" + j.getFJob().getId() + "," + j.getSJob().getId() + ")");
        }
        System.out.println();
    }

    //method for printing resourceBlockedJobs
    public void resourceBlockedJobs() {
        System.out.print("Resource Blocked Jobs: ");
        for (job j : resourceBlockedJobs) {
            System.out.print(j.getId() + ", ");
        }
        System.out.println();

    }

    //method for printing currently working jobs
    public void workingJobs() {
        System.out.print("Working Jobs: ");
        for (Resource r : resources) {
            if (r.isBusy() == true) {
                System.out.print("(" + r.getCurrJob().getId() + "," + r.getId() + ")");
            }
        }
        System.out.println();
    }

    //method for running all remaining jobs
    public void runAllRemaining() {
        timer++;
        boolean cont = false;
        for (Resource r : resources) {
            if (r.isBusy()) {
                cont = true;
            }
        }
        while (cont) {
            this.run();
            cont = false;
            for (Resource r : resources) {
                if (r.isBusy()) {
                    cont = true;
                }
            }
            timer++;
        }
       //for our given test case, i think that we need to see 12th timer
       //but if we do not need we can add the following line
        // allTimeString.delete(allTimeString.length()-16, allTimeString.length()-1);
    }

    //prints all time line
    public void allTimeLine() {
        System.out.println(allTimeString);
    }

    //to String method for JobScheduler class
    public String toString() {

        return schedulerTree.toString();
    }

    //is Dp contains given job
    public Boolean DPContain(job a) {
        for (DPnode j : dependencyBlockedJobs) {
            if (j.getFJob().getId() == a.getId())
                return true;
        }
        return false;
    }

    //overloaded method for is DP contains given jobs
    public Boolean DPContain(job a, job b) {
        for (DPnode j : dependencyBlockedJobs) {
            if (j.getFJob().getId() == a.getId() && j.getSJob().getId() == b.getId())
                return true;
        }
        return false;
    }

    //removes given jobs from DP
    public void DPRemove(job a, job b) {
        for (int i = 0; i < dependencyBlockedJobs.size(); i++) {
            if (dependencyBlockedJobs.get(i).getFJob().getId() == a.getId() &&
                    dependencyBlockedJobs.get(i).getSJob().getId() == b.getId()) {
                dependencyBlockedJobs.remove(i);
            }
        }
    }

    //class for dependency blocked jobs
    class DPnode {
        private job firstJob;
        private job secondJob;

        public DPnode(job a, job b) {
            firstJob = a;
            secondJob = b;
        }

        public job getFJob() {
            return this.firstJob;
        }

        public job getSJob() {
            return this.secondJob;
        }
    }
}