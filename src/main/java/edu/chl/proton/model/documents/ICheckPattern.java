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

import edu.chl.proton.model.util.FontStyle;

import java.util.regex.*;

/**
 * Created by Mickaela on 2017-08-08.
 *
 * An interface to create a shared group of methods for checking patterns
 * with different implementations.
 */
public interface ICheckPattern {

    /**
     * @param pattern is the specified pattern name
     * @return the specified pattern
     */
    Pattern pattern(String pattern);

    /**
     * @param text is the text to check for any matches of the pattern
     * @return the formatted text
     */
    String checkPattern(String text);

    /**
     * @param text is the text to check for any matches of the pattern
     * @param style is specified formatted style for this kind of match
     * @return the formatted text
     */
    String checkPattern(String text, FontStyle style);

}
