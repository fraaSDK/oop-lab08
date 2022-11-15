package it.unibo.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 *
 */
public final class SimpleController implements Controller {

    private String currentString;
    private List<String> stringHistory = new ArrayList<>();

    @Override
    public void setNextString(final String string) {
        this.currentString = Objects.requireNonNull(string);
    }

    @Override
    public String getNextString() {
        return Objects.requireNonNull(this.currentString);
    }

    @Override
    public List<String> getPrintedStringHistory() {
        return new ArrayList<>(stringHistory);
    }

    @Override
    public void printCurrentString() throws IllegalStateException {
        if (Objects.nonNull(this.currentString)) {
            System.out.println(this.currentString);
            stringHistory.add(this.currentString);
        } else {
            throw new IllegalStateException();
        }
    }

}
