package humanbot;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainApp {

    private static boolean running = false;
    private static List<String> snippets = new ArrayList<>();
    private static Robot robot;
    private static Random random = new Random();

    public static void main(String[] args) throws Exception {
        robot = new Robot();
        loadSnippets();

        JFrame frame = new JFrame("Human Coder Bot");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Start Bot");
        JButton stopButton = new JButton("Stop Bot");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running = true;
                new Thread(() -> runBot()).start();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running = false;
            }
        });

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(stopButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
                if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_Q &&
                    nativeEvent.getModifiers() == (NativeKeyEvent.CTRL_MASK | NativeKeyEvent.ALT_MASK)) {
                    running = false;
                    System.out.println("Bot stopped by global hotkey!");
                }
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent nativeEvent) {}

            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeEvent) {}
        });
    }

    private static void loadSnippets() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/snippets.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                snippets.add(line.trim());
            }
        }
        br.close();
    }

    private static void runBot() {
        while (running) {
            String snippet = snippets.get(random.nextInt(snippets.size()));
            typeSnippet(snippet);
            moveMouseRandom();
            sleepRandom(10000, 30000);
        }
    }

    private static void typeSnippet(String text) {
        for (char c : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                continue;
            }
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
            sleepRandom(50, 200);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    private static void moveMouseRandom() {
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();
        int x = mousePoint.x + random.nextInt(100) - 50;
        int y = mousePoint.y + random.nextInt(100) - 50;
        robot.mouseMove(x, y);
        sleepRandom(500, 1000);
        robot.mouseMove(mousePoint.x, mousePoint.y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private static void sleepRandom(int min, int max) {
        try {
            Thread.sleep(random.nextInt(max - min) + min);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
