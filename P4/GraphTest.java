import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

/**
 * 
 * This is the class which test all implementations in the Directed and unweighted graph
 */
public class GraphTest {

  
  
  /**
   * Test whether the graph returns correct order after add null vertex.
   */
  @Test
  public void test_addNullVertex() {
    Graph testGraph = new Graph();
    testGraph.addVertex("A");
    testGraph.addVertex("B");
    int orderResult = testGraph.order();
    if (orderResult != 5) {
      fail("The order of the graph should be 5, but the result is " + orderResult);
    }
  }

  /**
   * Test whether the graph returns correct order after add some vertex and then remove NULL.
   */
  @Test
  public void test005_addVertex_removeNullVertex() {
    Graph testGraph005 = new Graph();
    testGraph005.addVertex("A");
    testGraph005.addVertex("B");
    testGraph005.addVertex("C");
    testGraph005.addVertex("D");
    testGraph005.addVertex("E");
    testGraph005.removeVertex(null);
    int orderResult = testGraph005.order();
    if (orderResult != 5) {
      fail("The order of the graph should be 5, but the result is " + orderResult);
    }
  }

  /**
   * Test whether the graph returns correct order after add vertex that already contained.
   */
  @Test
  public void test006_addContainedVertex() {
    Graph testGraph006 = new Graph();
    testGraph006.addVertex("A");
    testGraph006.addVertex("B");
    testGraph006.addVertex("C");
    testGraph006.addVertex("D");
    testGraph006.addVertex("E");
    testGraph006.addVertex("E");
    int orderResult = testGraph006.order();
    if (orderResult != 5) {
      fail("The order of the graph should be 5, but the result is " + orderResult);
    }
  }

  /**
   * Test whether the graph returns correct order after add some vertex and then remove the node
   * that not contained in the graph.
   */
  @Test
  public void test007_addVertex_removeNotContainedVertex() {
    Graph testGraph007 = new Graph();
    testGraph007.addVertex("A");
    testGraph007.addVertex("B");
    testGraph007.addVertex("C");
    testGraph007.addVertex("D");
    testGraph007.addVertex("E");
    testGraph007.removeVertex("F");
    int orderResult = testGraph007.order();
    if (orderResult != 5) {
      fail("The order of the graph should be 5, but the result is " + orderResult);
    }
  }

  /**
   * Test whether the graph returns correct size and order after add both vertex not exits
   */
  @Test
  public void test008_addEdgeWithBothVertexNotExist() {
    Graph testGraph008 = new Graph();
    testGraph008.addVertex("A");
    testGraph008.addVertex("B");
    testGraph008.addVertex("C");
    // without vertex "D","E"
    testGraph008.addEdge("A", "B");
    testGraph008.addEdge("A", "E");
    testGraph008.addEdge("B", "E");
    testGraph008.addEdge("B", "C");
    testGraph008.addEdge("C", "B");
    testGraph008.addEdge("D", "C");
    testGraph008.addEdge("D", "E");
    testGraph008.addEdge("E", "D");
    testGraph008.removeEdge("C", "B");
    int sizeResult = testGraph008.size();
    int orderResult = testGraph008.order();
    if (sizeResult != 7 && orderResult != 5) {
      fail("The size of the graph should be 7, but the result is " + sizeResult
          + "The order of the graph should be 5, but the result is " + orderResult);
    }
  }

  /**
   * Test whether the graph returns correct size and order after remove edge when both vertices are
   * both in the graph
   */
  @Test
  public void test009_removeEdgeWithBothVertexNotExist() {
    Graph testGraph009 = new Graph();
    testGraph009.addVertex("A");
    testGraph009.addVertex("B");
    testGraph009.addVertex("C");
    // without vertex "D","E"
    testGraph009.addEdge("A", "B");
    testGraph009.addEdge("A", "E");
    testGraph009.addEdge("B", "E");
    testGraph009.addEdge("B", "C");
    testGraph009.addEdge("C", "B");
    testGraph009.addEdge("D", "C");
    testGraph009.addEdge("D", "E");
    testGraph009.addEdge("E", "D");
    testGraph009.removeEdge("F", "G");
    int sizeResult = testGraph009.size();
    int orderResult = testGraph009.order();
    if (sizeResult != 7 && orderResult != 5) {
      fail("The size of the graph should be 7, but the result is " + sizeResult
          + "The order of the graph should be 5, but the result is " + orderResult);
    }
  }
  
  /**
   * Test whether the graph returns correct size and order after remove edges that are not in the graph
   */
  @Test
  public void test010_removeEdgWithEdgeNotInGraph() {
    Graph testGraph010 = new Graph();
    testGraph010.addVertex("A");
    testGraph010.addVertex("B");
    testGraph010.addVertex("C");
    // without vertex "D","E"
    testGraph010.addEdge("A", "B");
    testGraph010.addEdge("A", "E");
    testGraph010.addEdge("B", "E");
    testGraph010.addEdge("B", "C");
    testGraph010.addEdge("C", "B");
    testGraph010.addEdge("D", "C");
    testGraph010.addEdge("D", "E");
    testGraph010.addEdge("E", "D");
    testGraph010.removeEdge("F", "G");
    testGraph010.removeEdge("A", "C");
    int sizeResult = testGraph010.size();
    int orderResult = testGraph010.order();
    if (sizeResult != 7 && orderResult != 5) {
      fail("The size of the graph should be 7, but the result is " + sizeResult
          + "The order of the graph should be 5, but the result is " + orderResult);
    }
  }

/**
  * Test whether the graph returns correct list of adjacent vertices after add and remove some
  edges.
  */
  @Test
  public void test011_addEdge_removeEdge_getAdjacentVerticesOf() {
  Graph testGraph011 = new Graph();
  testGraph011.addVertex("A");
  testGraph011.addVertex("B");
  testGraph011.addVertex("C");
  testGraph011.addVertex("D");
  testGraph011.addVertex("E");
  testGraph011.addEdge("A", "B");
  testGraph011.addEdge("A", "E");
  testGraph011.addEdge("B", "E");
  testGraph011.addEdge("B", "C");
  testGraph011.addEdge("C", "B");
  testGraph011.addEdge("D", "C");
  testGraph011.addEdge("D", "E");
  testGraph011.addEdge("E", "D");
  testGraph011.removeEdge("C", "B");
  
  List<String> getAdjacentVerticesOfExpected = new ArrayList<String>();
  getAdjacentVerticesOfExpected.add("B");
  getAdjacentVerticesOfExpected.add("E");
  List<String> getAdjacentVerticesOfResult = testGraph011.getAdjacentVerticesOf("A");
  // Checking for every element in expected List
  for (final String element : getAdjacentVerticesOfExpected) {
  // if result List has the current element
  if (!getAdjacentVerticesOfResult.contains(element)) {
  fail("The expected List of adjacent vertice of the graph should be "
  + getAdjacentVerticesOfExpected + " , but the result is " + getAdjacentVerticesOfResult);
  }
  }
  }



}