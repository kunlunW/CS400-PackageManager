import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

/**
 * PackageManger Test Class is testing methods in PackageManager.java
 * by constructing temporary graph
 * 
 * @author Kunlun Wang
 *
 */
class PackageManagerTest {

    /**
     * test basic parsing of PM
     */
    @Test
    void test_ConstructGraph() {
        PackageManager test= new PackageManager();
        try {
            test.constructGraph("valid.json");
        } catch (FileNotFoundException e) {
            fail("fail");
        } catch (IOException e) {
            fail("fail");
        } catch (ParseException e) {
            fail("fail");
        }
    }

    /**
     * test getAllPackages method
     */
    @Test
    void test_getAllPackages() {
        PackageManager test= new PackageManager();
        Set<String> set = new HashSet<String>();
        set.add("A");
        set.add("B");
        set.add("C");
        set.add("D");

        try {
            test.constructGraph("valid.json");    
            Set<String> allPackages = test.getAllPackages();
            if(allPackages.equals(set)) { 
                return;
            }
            fail("fail");
        }
        catch(Exception e) {
            fail("fail");
        }
    }
    /**
     * Testing if the method constructggraph will appropriately notices that the wrong filepath was given
     */
    @Test
    void test2_ConstructGraph_null_file() {
        PackageManager pm= new PackageManager();
        try {
            pm.constructGraph(" ");
        }
        catch(FileNotFoundException e){
            return; // this should happen with inappropriate jsonfilepath
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fail("TEST2 exception should have happened");
    }

   

    /**
     * testing getInstallationOrder method given A as an input
     */
    @Test
    void test4_getInstallationOrder_of_A() {
        PackageManager pm= new PackageManager();

        // result that I expect
        List<String> actual_result = new ArrayList<String>();
        actual_result.add("C");
        actual_result.add("D");
        actual_result.add("B");
        actual_result.add("A");

        try {
            pm.constructGraph("valid.json");

            if(pm.getInstallationOrder("A").equals(actual_result)) {
                return; // if they are equal it means it's a right answer
            }
            fail("shouldn't happen");
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("Exception shouldn't happen");         
        }
    }

    /**
     * testing toInstall method in packageManager class
     */
    @Test
    void test5_toInstall() {
        try {
            PackageManager pm= new PackageManager();
            pm.constructGraph("valid.json");

            List<String> actual_result = new ArrayList<String>();
            actual_result.add("A"); // this is answer that i expects

            if(pm.toInstall("A", "B").equals(actual_result)) {
                return; //TRUE!
            }
            fail("shouldn't happen");
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("fail");
        }
    }

    /**
     * testing getInstallationOrderForAllPackages method with valid.json file
     */
    @Test
    void test6_getInstallationOrderForAllPackages() {
        try {
            PackageManager pm= new PackageManager();
            pm.constructGraph("valid.json");

            List<String> actual_result = new ArrayList<String>();
            actual_result.add("C");
            actual_result.add("D");
            actual_result.add("B");
            actual_result.add("A");
            actual_result.add("E");

            if(pm.getInstallationOrderForAllPackages().equals(actual_result)){
                return; //passed!
            }
            fail("shouldn't happen");
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("this expection shouldn't happen");
        }

    }

    /**
     * checking if getPackageWithMaxDependencies method properly works
     * it should return A in valid.json file
     */
    @Test
    void test7_getPackageWithMaxDependencies() {
        try {
            PackageManager pm= new PackageManager();
            pm.constructGraph("valid.json");

            String actual_result = "A"; // "A" string is answer that i expect
            if(pm.getPackageWithMaxDependencies().equals(actual_result)){
                return; // see if the methods runs as I intended
                // TRUE!
            }
            fail("shouldn't happen");
        }
        catch(Exception e) {
            e.printStackTrace();
            fail("this expection shouldn't happen");
        }

    }

}