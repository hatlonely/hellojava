package guava;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {
    @Test
    public void testGraph() {
        {
            MutableGraph<Integer> graph = GraphBuilder.undirected().build();    // 无向图
            graph.putEdge(0, 1);
            graph.putEdge(0, 3);
            graph.putEdge(0, 4);
            graph.putEdge(1, 2);
            graph.putEdge(2, 3);
            graph.putEdge(3, 4);
            System.out.println(graph);
            assertFalse(graph.isDirected());
            assertEquals(graph.inDegree(0), 3);     // 入度
            assertEquals(graph.outDegree(0), 3);    // 出度
            for (Integer i : graph.adjacentNodes(0)) {           // 相邻顶点
                assertTrue(ImmutableSet.of(1, 3, 4).contains(i));
            }
        }
        {
            MutableGraph<Integer> graph = GraphBuilder.directed().build();
            graph.putEdge(0, 1);
            graph.putEdge(0, 3);
            graph.putEdge(1, 2);
            graph.putEdge(2, 3);
            graph.putEdge(3, 4);
            graph.putEdge(4, 0);
            System.out.println(graph);
            assertTrue(graph.isDirected());
            assertEquals(graph.inDegree(0), 1);     // 入度
            assertEquals(graph.outDegree(0), 2);    // 出度
            for (Integer i : ImmutableSet.of(1, 3, 4)) {           // 相邻顶点
                assertTrue(graph.adjacentNodes(0).contains(i));
            }
            for (Integer i : ImmutableSet.of(1, 3)) {   // 出节点
                assertTrue(graph.successors(0).contains(i));
            }
            for (Integer i : ImmutableSet.of(4)) {      // 入节点
                assertTrue(graph.predecessors(0).contains(i));
            }
            System.out.println(graph.incidentEdges(0));  // 相邻边
        }
    }

    @Test
    public void testValueGraph() {
        MutableValueGraph<Integer, Integer> graph = ValueGraphBuilder.directed().build();   // 带权有向图
        graph.putEdgeValue(0, 1, 10);
        graph.putEdgeValue(0, 3, 15);
        graph.putEdgeValue(1, 2, 8);
        graph.putEdgeValue(2, 3, 4);
        graph.putEdgeValue(3, 4, 3);
        graph.putEdgeValue(4, 0, 1);
        System.out.println(graph);

        assertTrue(graph.hasEdgeConnecting(1, 2));
        assertEquals(graph.edgeValue(1, 2).orElse(0), Integer.valueOf(8));
    }

    @Test
    public void testNetwork() {
        // 任意两个节点之间有且只有一条边
        MutableNetwork<Integer, Integer> network = NetworkBuilder.directed().build();
        network.addEdge(0, 1, 10);
        network.addEdge(0, 3, 15);
        network.addEdge(1, 2, 8);
        network.addEdge(2, 3, 4);
        network.addEdge(3, 4, 3);
        network.addEdge(4, 0, 1);
        System.out.println(network);
    }
}
