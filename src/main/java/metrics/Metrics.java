package metrics;

import core.PathResult;

public class Metrics {
    public static String summarize(PathResult r){
        String length = (r.path == null) ? "0" : String.valueOf(r.path.size());
        return String.format("success=%s, pathLen=%s, nodesExpanded=%d, runtimeMs=%d",
                r.success, length, r.nodesExpanded, r.millis);
    }
}

