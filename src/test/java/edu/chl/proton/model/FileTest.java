/*
 * Proton Text - A Markdown text editor
 * Copyright (C) 2017  Anton Levholm, Ludvig Ekman, Mickaela Södergren
 * and Stina Werme
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.chl.proton.model;

import edu.chl.proton.model.util.FileUtility;
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
        FileUtility classUnderTest = new FileUtility("hej.txt");
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