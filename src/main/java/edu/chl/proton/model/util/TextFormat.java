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

package edu.chl.proton.model.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-08-09.
 *
 * This class is used for formatting text.
 */

public class TextFormat {

    /**
     * Takes in a String of HTML and separates the content in each paragraph-tag into a String.
     * Each String is added to an ArrayList that is returned.
     *
     * @param html is the HTML content that needs to be formatted
     * @return a list of rows that is stripped of HTML-tags
     */

    public static List<String> html2text(String html) {
        ArrayList<String> rowList = new ArrayList<>();
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Element table = doc.select("body").get(0);
        Elements rows = table.select("p");
        for (Element row : rows) {
            rowList.add(row.text());
        }
        return rowList;
    }

}
