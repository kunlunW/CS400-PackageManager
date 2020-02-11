import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Filename: Graph.java Project: p4 Authors: Kunlun Wang & Deb Deppeler Email: kwang358@wisc.edu
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {

  // First of all, we need to define a few data structure:
  // we have two options for DS:
  // This is a list structure that stores all the vertices in the graph W
  private List<String> vertexList;
  // we could also use a 2D arrayList to store all the edges
  private List<List<String>> edgeList;
  // This is an adjacency matrix
  private boolean[][] adjMatrix;

  // int var to store the number of vertices in the graph, aka order
  private int vertexNum;

  // int var to store the number of edges in the graph, aka edges
  private int edgeNum;


  /*
   * Default no-argument constructor
   */
  public Graph() {
    // we initilize the adjMatrix to have a large size of 200*200
    // and if the matrix number of vertices exceeds the size,
    // we could expand its size
    this.adjMatrix = new boolean[200][200];
    // initialize the edgeList
    this.edgeList = new ArrayList<>();
    this.vertexList = new ArrayList<String>();
    this.vertexNum = 0;
    this.edgeNum = 0;
  }

  /**
   * Add new vertex to the graph.
   *
   * If vertex is null or already exists, method ends without adding a vertex or throwing an
   * exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   */

  public void addVertex(String vertex) {
    // if the vertex to be added is null && the vertexList contains such a vertex
    // then we immediately proceed to return
    if (vertex != null && !this.vertexList.contains(vertex)) {
      // we add the vertex to the vertexList
      vertexList.add(vertex);
      // And we since there is one more vertex, we need to increment the
      // amount of arrayList in edgeList
      edgeList.add(new ArrayList<>());
      // we need to increment the vertex number
      this.vertexNum++;
    }
  }

  // private boolean [][] expandAdjMatrix(boolean[][] adjMatrix) {
  // int reSize = 2 * adjMatrix.length;
  // boolean[][] reSizedMatrix = new boolean [reSize][reSize];
  // for (int i=0; i<adjMatrix.length; i++) {
  // for (int j=0; j<adjMatrix[i].length; j++) {
  // reSizedMatrix[i][j] = adjMatrix[i][j];
  // }
  // }
  // return reSizedMatrix;
  // }
  //

  /**
   * Remove a vertex and all associated edges from the graph.
   * 
   * If vertex is null or does not exist, method ends without removing a vertex, edges, or throwing
   * an exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   */
  public void removeVertex(String vertex) {
    // if the vertex is not null && the vertexList contains such a vertex
    // we proceed to removal
    if (vertex != null && this.vertexList.contains(vertex)) {
      for (int i = 0; i < vertexNum; i++) {
        // if the element at index i of the vertexList equals the vertex to be removed,
        // then we need to remove that element
        if (vertexList.get(i).equals(vertex)) {
          vertexList.remove(i);
          // decrement the vertex number
          vertexNum--;
        }
      }
    }
  }

  /**
   * Add the edge from vertex1 to vertex2 to this graph. (edge is directed and unweighted) If either
   * vertex does not exist, add vertex, and add edge, no exception is thrown. If the edge exists in
   * the graph, no edge is added and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in the graph 3. the
   * edge is not in the graph
   */
  public void addEdge(String vertex1, String vertex2) {
    // first check if both of the vertices are not null
    // if (vertex1 == null || vertex2 == null) {
    // return;
    // }
    // then check if both vertices are in the graph,
    // if either or both of the vertices are not in the graph,
    // we need to add it/them to the graph.
    // if (!vertexList.contains(vertex1) || !vertexList.contains(vertex2)) {
    // addVertex(vertex1);
    // addVertex(vertex2);
    // }

    // now we need to check if there is an edge between vertex1 and vertex2

    // if (!adjMatrix[vertexList.indexOf(vertex1)] [vertexList.indexOf(vertex2)]) {
    // adjMatrix[vertexList.indexOf(vertex1)] [vertexList.indexOf(vertex2)] = true;
    // edgeNum++;
    // }
    // }


    // first, we need to make sure that both vertices are not null
    if (vertex1 != null && vertex2 != null) {
      // then we need to check for the duplicate of that vertex in the vertexList
      addChecker(vertex1);
      addChecker(vertex2);
      for (int i = 0; i < vertexNum; i++) {
        // here we add an edge from vertex 1 to vertex 2
        if (vertexList.get(i).equals(vertex1)) {
          if (!edgeList.get(i).contains(vertex2)) {
            edgeList.get(i).add(vertex2);
            // increment the edge number
            this.edgeNum++;
          }
        }
      }
    }


  }

  /**
   * helper method to check for duplicate of the vertex to be added if the vertext to be added
   * doesnt exist in the vertexList, then we need to add it to the vertex list
   * 
   */

  private void addChecker(String toAddVertex) {
    for (int i = 0; i < this.vertexNum; i++) {
      // check if the vertex exists in the vertexList or not
      if (!vertexList.get(i).equals(toAddVertex)) {
        // if not, add to the vertexList
        addVertex(toAddVertex);
      }
    }
  }

  /**
   * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed and unweighted) If
   * either vertex does not exist, or if an edge from vertex1 to vertex2 does not exist, no edge is
   * removed and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in the graph 3. the
   * edge from vertex1 to vertex2 is in the graph
   */
  public void removeEdge(String vertex1, String vertex2) {
    // first we need to check if either of vertex is null
    // if (!(vertex1 != null && vertex2 != null)) {
    // return;
    // }
    //
    // then we need to check if both vertices are in the graph
    // if (!(vertexList.contains(vertex1) && vertexList.contains(vertex2))) {
    // return;
    // }
    //
    // if (adjMatrix[vertexList.indexOf(vertex1)] [vertexList.indexOf(vertex2)]) {
    // adjMatrix[vertexList.indexOf(vertex1)] [vertexList.indexOf(vertex2)] = false;
    // edgeNum--;
    // }
    // }


    // make sure that both of the vertices are not null
    if (vertex1 != null && vertex2 != null) {
      for (int i = 0; i < this.vertexNum; i++) {
        // we use the same method as we used in the previous method
        // to remove the edge
        if (vertexList.get(i).equals(vertex1)) {
          if (edgeList.get(i).contains(vertex2)) {
            // remove edge from the edgeList
            edgeList.get(i).remove(vertex2);
            // decrement
            this.edgeNum--;
          }
        }
      }
    }
  }

  /**
   * Returns a Set that contains all the vertices
   * 
   */
  public Set<String> getAllVertices() {
    // declare a set that contains all the elements from the vertexList
    Set<String> vertexSet = new HashSet<String>(this.vertexList);
    return vertexSet;
  }

  /**
   * Get all the neighbor (adjacent) vertices of a vertex
   * 
   * @param vertex the vertex where we try to find its neighbors
   *
   */
  public List<String> getAdjacentVerticesOf(String vertex) {
    // first we declare a list that will store all the neighbors
    // of a given vertex
    List<String> neighborList = new ArrayList<String>();
    // if the vertex is not null
    if (vertex != null) {
      // and the vertexList should contain such a vertex
      if (vertexList.contains(vertex)) {
        // First of all we go to the source of the vertex(scr, from)
        for (int i = 0; i < this.vertexNum; i++) {
          // If we found such a vertex
          if (vertexList.get(i).equals(vertex)) {
            // then we proceed to the get all the elements in that arrayList
            for (int j = 0; j < edgeList.get(i).size(); j++) {
              // get all the elements
              neighborList.add(edgeList.get(i).get(j));
            }
          }
        }
      }
    }
    return neighborList;
  }

  /**
   * Returns the number of edges in this graph.
   */
  public int size() {
    return edgeNum;
  }

  /**
   * Returns the number of vertices in this graph.
   */
  public int order() {
    return vertexNum;
  }
}
