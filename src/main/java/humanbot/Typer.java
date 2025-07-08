package humanbot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Typer {
    private final Robot robot;

    public Typer() throws AWTException {
        robot = new Robot();
    }

    public void type(String text) {
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(Character.toUpperCase(c));
                robot.keyRelease(Character.toUpperCase(c));
                robot.keyRelease(KeyEvent.VK_SHIFT);
            } else {
                switch (c) {
                    case '(': pressShiftKey(KeyEvent.VK_9); break;
                    case ')': pressShiftKey(KeyEvent.VK_0); break;
                    case '{': pressShiftKey(KeyEvent.VK_OPEN_BRACKET); break;
                    case '}': pressShiftKey(KeyEvent.VK_CLOSE_BRACKET); break;
                    case '"': pressShiftKey(KeyEvent.VK_QUOTE); break;
                    case ';': pressKey(KeyEvent.VK_SEMICOLON); break;
                    case '.': pressKey(KeyEvent.VK_PERIOD); break;
                    case ' ': pressKey(KeyEvent.VK_SPACE); break;
                    case '\n': pressKey(KeyEvent.VK_ENTER); break;
                    default:
                        int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                        if (keyCode != KeyEvent.CHAR_UNDEFINED) {
                            pressKey(keyCode);
                        }
                }
            }
            sleep(50, 200);
        }
        pressKey(KeyEvent.VK_ENTER);
    }

    private void pressShiftKey(int key) {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(key);
        robot.keyRelease(key);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    private void pressKey(int key) {
        robot.keyPress(key);
        robot.keyRelease(key);
    }

    private void sleep(int min, int max) {
        try {
            Thread.sleep((int) (Math.random() * (max - min)) + min);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
