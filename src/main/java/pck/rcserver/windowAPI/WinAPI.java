package pck.rcserver.windowAPI;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class WinAPI {
    public static boolean run = false;

    public static void main(String[] args) {
        // System.out.println(getListRunningProcess());
        // System.out.println(getListRunningApp());
        startProcess("notepad.exe");
        // stopProcess(???)
        // startApp("notepad.exe");
        // System.out.println(stopApp("notepad.exe"));
        // screenshot();

        // ArrayList<Integer> keypressList = new ArrayList<>();
        // GlobalKeyboardHook globalKeyboardHook = keyStrokeHook(keypressList);
        //
        // new Thread(() -> {
        //     try {
        //         Thread.sleep(5000);
        //
        //         keyStrokeUnhook(globalKeyboardHook);
        //         System.out.println(keypressList);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }).start();


        // shutdown();
    }

    public static boolean startProcess(String processName) {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", processName);

        try {
            Process p = pb.start();

            if (p.getErrorStream().available() > 0) {
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<WinProcess> getListRunningProcess() {
        ArrayList<WinProcess> runningProcesses = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "tasklist.exe");

            Process p = pb.start();

            Scanner sc = new Scanner(p.getInputStream());
            String line;

            line = sc.nextLine(); // trash
            line = sc.nextLine();
            line = sc.nextLine();

            while (sc.hasNextLine()) {

                line = sc.nextLine();

                String[] spitted = line.split("\s+");

                StringBuilder imageName = new StringBuilder(spitted[0]);
                int i = 1;
                for (; i <= spitted.length - 6; i++) {
                    imageName.append(" ").append(spitted[i]);
                }
                WinProcess winProcess = new WinProcess(imageName.toString(), Integer.parseInt(spitted[i]), spitted[i + 1], Integer.parseInt(spitted[i + 2]), String.format("%s", spitted[i + 3]));

                runningProcesses.add(winProcess);
            }

            sc.close();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return runningProcesses;
    }

    public static ArrayList<WinApp> getListRunningApp() {
        ArrayList<WinApp> winApps = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "tasklist.exe /apps");

            Process p = pb.start();

            Scanner sc = new Scanner(p.getInputStream());
            String line;

            line = sc.nextLine(); // trash
            line = sc.nextLine();
            line = sc.nextLine();

            while (sc.hasNextLine()) {

                line = sc.nextLine();

                String[] spitted = line.split("\s+");

                WinApp winApp = new WinApp(String.format("%s %s", spitted[0], spitted[1]), Integer.parseInt(spitted[2]), spitted[3], spitted[5]);

                winApps.add(winApp);
            }

            sc.close();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
            return winApps;
        }

        return winApps;
    }

    public static boolean stopProcess(int pid) {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", String.format("taskkill /pid %d", pid));

        try {
            Process p = pb.start();

            if (p.getErrorStream().available() > 0) {

                return false;
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }

    public static boolean startApp(String appName) {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", String.format("start %s", appName));

        try {
            Process p = pb.start();

            if (p.getErrorStream().available() > 0) {
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }

    public static boolean stopApp(String appName) {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", String.format("taskkill /im %s", appName));

        try {
            Process p = pb.start();

            if (p.getErrorStream().available() > 0) {
                p.destroy();
                return false;
            }
            p.destroy();
            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }

    public static boolean takeScreenshot() {
        BufferedImage image = null;
        try {
            image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "png", new File("screenshot.png"));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static GlobalKeyboardHook keyStrokeHook(ArrayList<Integer> listKeypress) {
        try {
            GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of raw input

            System.out.println("Global keyboard hook successfully started");

            keyboardHook.addKeyListener(new GlobalKeyAdapter() {
                @Override
                public void keyPressed(GlobalKeyEvent event) {
                    System.out.println(new Date() + " - " + event.getKeyChar() + " - " + event);
                    listKeypress.add(event.getVirtualKeyCode());
                }
            });

            return keyboardHook;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();

            return null;
        }
    }

    public static boolean keyStrokeUnhook(GlobalKeyboardHook keyboardHook) {
        try {
            keyboardHook.shutdownHook();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean shutdown() {
        // ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "shutdown -s -t 2000");
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "shutdown -s -t 2000");

        try {
            Process p = pb.start();

            Scanner sc = new Scanner(p.getErrorStream());

            boolean ret = true;

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);

                ret = false;
            }
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
