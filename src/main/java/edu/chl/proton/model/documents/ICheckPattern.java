package edu.chl.proton.model.documents;

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
