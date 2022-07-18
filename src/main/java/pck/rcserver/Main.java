package pck.rcserver;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import pck.rcserver.be.server.Server;
import pck.rcserver.windowAPI.WinAPI;

import java.util.Map;

public class Main {
    private static boolean run = true;
    public static void main(String[] args) {
        Server.create();
        Server.start();
        // System.out.println("Remote control server says hello");
        // WinAPI.screenshot();

        // Might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
        // GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of raw input
        //
        // System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown. Connected keyboards:");
        //
        // for (Map.Entry<Long, String> keyboard : GlobalKeyboardHook.listKeyboards().entrySet()) {
        //     System.out.format("%s: %s\n", keyboard.getKey(), keyboard.getValue());
        // }
        //
        // keyboardHook.addKeyListener(new GlobalKeyAdapter() {
        //
        //     @Override
        //     public void keyPressed(GlobalKeyEvent event) {
        //         System.out.println(event.getVirtualKeyCode());
        //         if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
        //             keyboardHook.shutdownHook();
        //         }
        //     }
        //
        //     @Override
        //     public void keyReleased(GlobalKeyEvent event) {
        //         //System.out.println(event);
        //     }
        // });
    }
}
