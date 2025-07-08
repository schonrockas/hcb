package humanbot;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

public class MouseHumanizer {
    private final Robot robot;
    private final Random random = new Random();

    public MouseHumanizer() throws AWTException {
        robot = new Robot();
    }

    public void move() {
        Point p = MouseInfo.getPointerInfo().getLocation();
        int x = p.x + random.nextInt(100) - 50;
        int y = p.y + random.nextInt(100) - 50;
        robot.mouseMove(x, y);
        sleep(500, 1000);
        robot.mouseMove(p.x, p.y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void sleep(int min, int max) {
        try {
            Thread.sleep(random.nextInt(max - min) + min);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
