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

package edu.chl.proton.model.documents;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ludvig on 2017-08-08.
 */
public interface IDocument {
    boolean doesExist();
    File getFile();
    String getPath();
    String getText();
    void setText(List<String> text);
    String getHTML();
    void save(String path) throws IOException;
    boolean save() throws IOException;
    void removeFile();
    String getDateForLastEdited();
    boolean isSaved();
    void aqcuireText();
}
