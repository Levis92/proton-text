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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludvig on 2017-05-26.
 * Plain document type, which do not follow markdown standard or any other standard.
 */
public class Plain implements IDoc {

    private List<String> lines = new ArrayList<>();

    public List<String> getLines(){
        return lines;
    }

    public void setText(List<String> str){
        this.lines = str;
    }

    @Override
    public String getHTML(){
        String tmp = "";
        for(String str : lines){
            tmp = tmp + "<p style=\"width:100%\">" +str + "</p>";
        }
        return tmp;
    }

    @Override
    public String getText(){
        return getHTML();
    }
}
