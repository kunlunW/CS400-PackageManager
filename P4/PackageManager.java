import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Filename: PackageManager.java Project: p4 Authors: Kunlun Wang & Deb Deppeler
 * 
 * email: kwang358@wisc.edu
 * 
 * PackageManager is used to process json package dependency files and provide function that make
 * that information available to other users.
 * 
 * Each package that depends upon other packages has its own entry in the json file.
 * 
 * Package dependencies are important when building software, as you must install packages in an
 * order such that each package is installed after all of the packages that it depends on have been
 * installed.
 * 
 * For example: package A depends upon package B, then package B must be installed before package A.
 * 
 * This program will read package information and provide information about the packages that must
 * be installed before any given package can be installed. all of the packages in
 * 
 * You may add a main method, but we will test all methods with our own Test classes.
 */

public class PackageManager {

  private Graph graph;

  /*
   * Package Manager default no-argument constructor.
   */
  public PackageManager() {
    this.graph = new Graph();
  }

  /**
   * Takes in a file path for a json file and builds the package dependency graph from it.
   * 
   * @param jsonFilepath the name of json data file with package dependency information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException if the give file cannot be read
   * @throws ParseException if the given json cannot be parsed
   */
  public void constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {

    try {// online source: https://www.geeksforgeeks.org/parse-json-java/
      Object obj = new JSONParser().parse(new FileReader(jsonFilepath));
      // typecast obj to JSONObject
      JSONObject JSONObject = (JSONObject) obj;
      // get packages, this is the package array
      JSONArray jsonArray = (JSONArray) JSONObject.get("packages");
      // we need to iterate the package
      Iterator<JSONObject> packageIterate = jsonArray.iterator();

      while (packageIterate.hasNext()) {
        // get the names
        JSONObject eachPackage = (JSONObject) packageIterate.next();
        String packageNames = (String) eachPackage.get("name");
        graph.addVertex(packageNames);
        // then we need to get the dependencies
        JSONArray dependencyArr = (JSONArray) eachPackage.get("dependencies");

        Iterator dependencyIterate = (Iterator) dependencyArr.iterator();

        while (dependencyIterate.hasNext()) {
          String dependencyVertex = (String) dependencyIterate.next();
          graph.addVertex(dependencyVertex);
          graph.addEdge(packageNames, dependencyVertex);

        }
      }

    }

    catch (FileNotFoundException e) {
      throw new FileNotFoundException();
    } catch (IOException e) {
      throw new IOException();
    } catch (ParseException e) {
      throw new ParseException(0);
    }
  }


  /**
   * Helper method to get all packages in the graph.
   * 
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return graph.getAllVertices();
  }

  /**
   * Given a package name, returns a list of packages in a valid installation order.
   * 
   * Valid installation order means that each package is listed before any packages that depend upon
   * that package.
   * 
   * @return List<String>, order in which the packages have to be installed
   * 
   * @throws CycleException if you encounter a cycle in the graph while finding the installation
   *         order for a particular package. Tip: Cycles in some other part of the graph that do not
   *         affect the installation order for the specified package, should not throw this
   *         exception.
   * 
   * @throws PackageNotFoundException if the package passed does not exist in the dependency graph.
   */
  public List<String> getInstallationOrder(String pkg)
      throws CycleException, PackageNotFoundException {
    List<String> order = new Stack<String>();

    // first, we need to check if pkg exists in the graph,
    // if it doesn't, then throw exception
    if (!graph.getAllVertices().contains(pkg)) {
      throw new PackageNotFoundException();
    }
    // we need to obtain the order, here I used a private helper to achieve the goal
    try {
      order = orderGetter(pkg, order);
    }
    // we need to detect if there is any cycle in the process
    catch (CycleException e) {
      throw new CycleException();
    }
    return order;
  }

  /**
   * Helper method to get the installation order
   *
   * @param pkg package
   * @param order
   * @throws CycleException the cycle exception
   */
  private List<String> orderGetter(String pkg, List<String> order) throws CycleException {

    List<String> dependencyList = graph.getAdjacentVerticesOf(pkg);
    // if the dlist is null and if the package is not in the order list
    if (dependencyList == null) {
      if (!order.contains(pkg))
        // we need to add package to the order list
        order.add(pkg);
      // and return the new order list
      return order;
      // if the dlist is not null
    } else {
      // we need to store the names of packages 
      for (int i = 0; i < dependencyList.size(); i++) {
        String names = dependencyList.get(i);
        // we need to get the adjacent vertex of each names
        List<String> adjVertex = graph.getAdjacentVerticesOf(names);
        // if the adjVertex is not null, and the adjvertex contains package 
        if (adjVertex != null && adjVertex.contains(pkg))
          // cycle occured 
          throw new CycleException();
        // recursion back 
        order = orderGetter(dependencyList.get(i), order);
      }
      order.add(pkg);
    }
    return order;
  }


  /**
   * Given two packages - one to be installed and the other installed, return a List of the packages
   * that need to be newly installed.
   * 
   * For example, refer to shared_dependecies.json - toInstall("A","B") If package A needs to be
   * installed and packageB is already installed, return the list ["A", "C"] since D will have been
   * installed when B was previously installed.
   * 
   * @return List<String>, packages that need to be newly installed.
   * 
   * @throws CycleException if you encounter a cycle in the graph while finding the dependencies of
   *         the given packages. If there is a cycle in some other part of the graph that doesn't
   *         affect the parsing of these dependencies, cycle exception should not be thrown.
   * 
   * @throws PackageNotFoundException if any of the packages passed do not exist in the dependency
   *         graph.
   */
  public List<String> toInstall(String newPkg, String installedPkg)
      throws CycleException, PackageNotFoundException {
    // first, we need to check if the package already exists in the graph
    // If the package does not exist in the dependency graph, then we need to throw
    // PackageNotFoundException
    if (!(getAllPackages().contains(newPkg) || !getAllPackages().contains(installedPkg))) {
      throw new PackageNotFoundException();
    }
    List<String> current;
    List<String> alreadyInstalled;
    // we need to get installation order of the new package 
    // as well as the order of the installed package 
    try {
      current = getInstallationOrder(newPkg);
      alreadyInstalled = this.getInstallationOrder(installedPkg);
      // we need to iterate and store the current package 
      String currentPkg;
      for (int i = 0; i < current.size(); i++) {
        currentPkg = current.get(i);
        // if the alreadInsteall list contains the current package
        if (alreadyInstalled.contains(currentPkg)) {
          // then we need to remove that package 
          current.remove(i);
          i--;
        }
      }
    } catch (CycleException e) {
      throw new CycleException();
    }
    return current;
  }

  /**
   * Return a valid global installation order of all the packages in the dependency graph.
   * 
   * assumes: no package has been installed and you are required to install all the packages
   * 
   * returns a valid installation order that will not violate any dependencies
   * 
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph
   * @throws PackageNotFoundException
   */
  public List<String> getInstallationOrderForAllPackages()
      throws CycleException, PackageNotFoundException {

    List<String> order = null;
    List<String> orderAfterIteration = null;

    Iterator<String> packageItr = getAllPackages().iterator();
    boolean iteration = false;
    String toInstall;
    String pkgNames;
    try {
      while (packageItr.hasNext()) {
        pkgNames = packageItr.next();
        order = this.getInstallationOrder(pkgNames);

        if (!iteration) {
          orderAfterIteration = order;
          iteration = true;
        } else {
          for (int i = 0; i < order.size(); i++) {
            toInstall = order.get(i);
            if (!orderAfterIteration.contains(toInstall)) {
              orderAfterIteration.add(toInstall);
            }
          }
        }
      }
    } catch (CycleException e) {
      throw new CycleException();
    }
    return orderAfterIteration;
  }

  /**
   * Find and return the name of the package with the maximum number of dependencies.
   * 
   * Tip: it's not just the number of dependencies given in the json file. The number of
   * dependencies includes the dependencies of its dependencies. But, if a package is listed in
   * multiple places, it is only counted once.
   * 
   * Example: if A depends on B and C, and B depends on C, and C depends on D. Then, A has 3
   * dependencies - B,C and D.
   * 
   * @return String, name of the package with most dependencies.
   * @throws CycleException if you encounter a cycle in the graph
   * @throws PackageNotFoundException
   */
  public String getPackageWithMaxDependencies() throws CycleException, PackageNotFoundException {

    Iterator<String> vertexIterator = graph.getAllVertices().iterator();
    String pkgNames;
    List<String> current = null;
    List<String> maxDependencies = null;
    boolean iteration = false;
    String max = null;
    while (vertexIterator.hasNext()) {
      pkgNames = vertexIterator.next();
      try {
        current = this.getInstallationOrder(pkgNames);
        if (!iteration) {
          maxDependencies = current;
          max = pkgNames;
          iteration = true;
        }
      } catch (CycleException e) {
        throw new CycleException();
      } catch (PackageNotFoundException e) {

      }

      if (maxDependencies.size() < current.size()) {
        maxDependencies = current;
        max = pkgNames;
      }
    }

    return max;
  }

  public static void main(String[] args) {
    System.out.println("PackageManager.main()");
  }

}
