package it.unibo.mvc;

import java.util.List;

/**
 *
 */
public interface Controller {

    /**
     * Sets a string to be printed next.
     * @param string to print.
     */
    void setNextString(final String string);

    /**
     * @return the next string to be printed.
     */
    String getNextString();

    /**
     * @return a {@code List<String>} containing
     * all the previously printed strings.
     */
    List<String> getPrintedStringHistory();

    /**
     * Prints the current string.
     * @throws IllegalStateException if the current string is unset.
     */
    void printCurrentString() throws IllegalStateException;

}
