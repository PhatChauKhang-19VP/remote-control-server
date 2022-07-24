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
        ServerGUI.main(args);
    }
}
