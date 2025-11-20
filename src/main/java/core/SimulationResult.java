package core;

public class SimulationResult { // kw
    public long totalRunTimeMillis;
    public int totalNodesExpanded;
    public int pathLength;
    public boolean reachedGoal;

    public long getTotalRunTimeMillis() {
        return totalRunTimeMillis;
    }

    public void setTotalRunTimeMillis(long totalRunTimeMillis) {
        this.totalRunTimeMillis = totalRunTimeMillis;
    }

    public int getTotalNodesExpanded() {
        return totalNodesExpanded;
    }

    public void setTotalNodesExpanded(int totalNodesExpanded) {
        this.totalNodesExpanded = totalNodesExpanded;
    }

    public int getPathLength() {
        return pathLength;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }

    public boolean isReachedGoal() {
        return reachedGoal;
    }

    public void setReachedGoal(boolean reachedGoal) {
        this.reachedGoal = reachedGoal;
    }

    public SimulationResult(long totalRunTimeMillis, int totalNodesExpanded, int pathLength) {
        this.totalRunTimeMillis = totalRunTimeMillis;
        this.totalNodesExpanded = totalNodesExpanded;
        this.pathLength = pathLength;
        this.reachedGoal = pathLength > 0;
    }

    @Override
    public String toString() {
        return String.format("SimulationResult{totalRunTimeMillis=%d, totalNodesExpanded=%d, pathLength=%d, reachedGoal=%s}",
                totalRunTimeMillis, totalNodesExpanded, pathLength, reachedGoal);
    }

}
