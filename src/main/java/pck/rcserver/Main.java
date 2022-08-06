package pck.rcserver;

public class Main {
    private static boolean run = true;

    public static void main(String[] args) {
        ServerGUI.main(args);
        ServerGUI.stopServer();
    }
}
