package humanbot;

import java.util.List;
import java.util.Random;

public class BotController {
    private volatile boolean running = false;
    private final List<String> snippets;
    private final Random random = new Random();

    public BotController(List<String> snippets) {
        this.snippets = snippets;
    }

    public void start(Typer typer, MouseHumanizer mouseHumanizer) {
        running = true;
        new Thread(() -> run(typer, mouseHumanizer)).start();
    }

    public void stop() {
        running = false;
        System.out.println("Bot stopped!");
    }

    private void run(Typer typer, MouseHumanizer mouseHumanizer) {
        while (running) {
            String snippet = snippets.get(random.nextInt(snippets.size()));
            typer.type(snippet);
            mouseHumanizer.move();
            sleeper(10000, 30000);
        }
    }

    private void sleeper(int min, int max) {
        try {
            Thread.sleep(random.nextInt(max - min) + min);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
