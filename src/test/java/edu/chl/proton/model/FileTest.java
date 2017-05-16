package edu.chl.proton.model;



import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Stina Werme
 * Created by stinawerme on 09/05/17.
 */
public class FileTest {

    @Test
    public void saveFileTest() throws IOException {
        File classUnderTest = new File("hej.txt");
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            list.add("Line " + i);
        }
        classUnderTest.save(list);
        List<String> records = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(classUnderTest.getPath()));
            String line;
            while((line = reader.readLine()) != null) {
                records.add(line);
            }
            reader.close();
        } catch (Exception e) {}

        assertTrue("The file classUnderTest should not be null", classUnderTest != null);
        assertTrue("list should be equal to records", list.equals(records));
    }
}