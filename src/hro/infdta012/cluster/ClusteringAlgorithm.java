package hro.infdta012.cluster;

import java.util.List;

public interface ClusteringAlgorithm {

	void assignPoints(List<Point> points, Cluster[] clusters);

	void setCenters(Cluster[] clusters);

	void initClusters(Cluster[] clusters, List<Point> points);

}
