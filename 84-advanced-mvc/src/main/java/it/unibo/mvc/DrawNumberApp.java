package it.unibo.mvc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * @param configFile containing the game's settings.
     * @param views
     *            the views to attach
     */
    public DrawNumberApp(final String configFile, final DrawNumberView... views) {
        /*
         * Side-effect proof
         */
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView view: views) {
            view.setObserver(this);
            view.start();
        }

        final var configBuilder = new Configuration.Builder();
        try (var file = new BufferedReader(
                new InputStreamReader(ClassLoader.getSystemResourceAsStream(configFile), StandardCharsets.UTF_8)
            )) {
            for (var line = file.readLine(); Objects.nonNull(line); line = file.readLine()) {
                final String[] tokens = line.split(":");
                final String key = tokens[0];
                final int value = Integer.parseInt(tokens[1].trim());

                if (key.contains("maximum")) {
                    configBuilder.updateMax(value);
                } else if (key.contains("minimum")) {
                    configBuilder.updateMin(value);
                } else if (key.contains("attempts")) {
                    configBuilder.updateAttempts(value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();    // NOPMD: allowed for exercise.
            displayError(e.getMessage());
        }

        final var config = configBuilder.build();
        this.model = new DrawNumberImpl(config);
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view: views) {
                view.numberIncorrect();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @SuppressFBWarnings()
    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

    // Displays the error message for each view.
    private void displayError(final String message) {
        for (final DrawNumberView view : views) {
            view.displayError(message);
        }
    }

    /**
     * @param args
     *            ignored
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp("config.yml",
                new DrawNumberViewImpl(),
                new DrawNumberViewImpl(),
                new PrintStreamView("logger.txt"),
                new PrintStreamView(System.out)
        );
    }

}
