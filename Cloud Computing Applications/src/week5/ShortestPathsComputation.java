package week5;

import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.conf.LongConfOption;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.IntWritable;

import java.io.IOException;

/**
 * Compute shortest paths from a given source.
 */
public class ShortestPathsComputation extends BasicComputation<IntWritable, IntWritable, IntWritable, IntWritable> {

    /**
     * The shortest paths id
     */
    public static final LongConfOption SOURCE_ID = new LongConfOption("SimpleShortestPathsVertex.sourceId", 1, "The shortest paths id");

    /**
     * Is this vertex the source id?
     *
     * @param vertex Vertex
     * @return True if the source id
     */
    private boolean isSource(Vertex<IntWritable, ?, ?> vertex) {
        return vertex.getId().get() == SOURCE_ID.get(getConf());
    }

    @Override
    public void compute(Vertex<IntWritable, IntWritable, IntWritable> vertex, Iterable<IntWritable> messages) throws IOException {
        // First superstep is special, because we initialize the values of every node and relax edges coming out of the source
        if (getSuperstep() == 0) {
            if (isSource(vertex)) {
                for (Edge<IntWritable, IntWritable> edge : vertex.getEdges()) {
                    sendMessage(edge.getTargetVertexId(), edge.getValue());
                }
                vertex.setValue(new IntWritable(0));
            } else {
                vertex.setValue(new IntWritable(-1));
            }
            vertex.voteToHalt();
            return;
        }

        boolean changed = false;
        // did we get a smaller id ?
        for (IntWritable message : messages) {
            int currentValue = vertex.getValue().get();
            if (currentValue == -1 || message.get() < currentValue) {
                vertex.setValue(message);
                changed = true;
            }
        }

        // propagate new component id to the neighbors
        if (changed) {
            int currentValue = vertex.getValue().get();
            for (Edge<IntWritable, IntWritable> edge : vertex.getEdges()) {
                sendMessage(edge.getTargetVertexId(), new IntWritable(currentValue + edge.getValue().get()));
            }
        }

        vertex.voteToHalt();
    }
}
