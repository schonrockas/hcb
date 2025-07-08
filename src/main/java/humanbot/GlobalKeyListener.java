package humanbot;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {
    private final BotController controller;

    public GlobalKeyListener(BotController controller) {
        this.controller = controller;
    }

    public void register() throws Exception {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_Q &&
            e.getModifiers() == (NativeKeyEvent.CTRL_MASK | NativeKeyEvent.ALT_MASK)) {
            controller.stop();
        }
    }

    @Override public void nativeKeyReleased(NativeKeyEvent e) {}
    @Override public void nativeKeyTyped(NativeKeyEvent e) {}
}
