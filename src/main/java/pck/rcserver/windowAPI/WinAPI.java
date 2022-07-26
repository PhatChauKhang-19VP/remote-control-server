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
        // System.out.println(startProcess("notepad12312"));
        // getListRunningProcess();
        // getListRunningApp();
        // System.out.println(startProcess("notepad345.exe "));
        // stopProcess(???)
//        System.out.println(startApp("chrome123"));;
//        System.out.println(startApp("chrome"));;
        // System.out.println(stopApp(1111111));
        // screenshot();

        // ArrayList<Integer> keypressList = new ArrayList<>();
        // GlobalKeyboardHook globalKeyboardHook = keyStrokeHook(keypressList);
        //
        // new Thread(() -> {
        //     try {
        //         Thread.sleep(30000);
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
        try {
            String command = ("powershell.exe " + processName);
            Process p = Runtime.getRuntime().exec(command);

            p.waitFor();

            try {
                return p.exitValue() == 0;
            } catch (Exception e) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<WinProcess> getListRunningProcess() {
        ArrayList<WinProcess> winProcesses = new ArrayList<>();
        try {
            String command = ("powershell.exe gps | select Name, Id, VM");

            Process p = Runtime.getRuntime().exec(command);

            Scanner sc = new Scanner(p.getInputStream());

            sc.nextLine(); // trash
            System.out.println(sc.nextLine()); // header
            sc.nextLine(); // trash

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] vals = line.trim().split("\s+");

                if (vals.length != 3) continue;

                WinProcess winProcess = new WinProcess(
                        vals[0],
                        Integer.parseInt(vals[1].replace(",", "")),
                        Double.parseDouble(vals[2].replace(",", "")) / 1024D / 1024D
                );

                winProcesses.add(winProcess);
                System.out.println(winProcess);
            }

            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return winProcesses;
    }

    public static boolean stopProcess(int pid) {
        String command = "powershell.exe " + String.format("taskkill /f /pid %d", pid);

        try {
            Process p = Runtime.getRuntime().exec(command);

            p.waitFor();

            try {
                return p.exitValue() == 0;
            } catch (Exception e) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    public static ArrayList<WinApp> getListRunningApp() {
        ArrayList<WinApp> winApps = new ArrayList<>();
        try {
            String command = "powershell.exe gps | ? { $_.MainWindowTitle } | select Name, Id, VM";

            // Executing the command
            Process p = Runtime.getRuntime().exec(command);

            Scanner sc = new Scanner(p.getInputStream());

            sc.nextLine(); // trash
            sc.nextLine();
            sc.nextLine();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] vals = line.trim().split("\s+");

                if (vals.length != 3) continue;

                WinApp winApp = new WinApp(
                        vals[0],
                        Integer.parseInt(vals[1]),
                        Double.parseDouble(vals[2].replace(",", "")) / 1024D / 1024D
                );

                winApps.add(winApp);
                System.out.println(winApp);
            }

            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
            return winApps;
        }

        return winApps;
    }

    public static boolean startApp(String appName) {
        ProcessBuilder pb = new ProcessBuilder("powershell.exe ", String.format("start %s", appName));

        try {
            Process p = pb.start();

            //Thread.sleep(3000);
            p.waitFor();

            try {
                return p.exitValue() == 0;
            } catch (Exception e) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    public static boolean stopApp(int pid) {
        ProcessBuilder pb = new ProcessBuilder("powershell.exe", String.format("taskkill /f /pid %d", pid));

        try {
            Process p = pb.start();

            p.waitFor();

            try {
                return p.exitValue() == 0;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
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
        ProcessBuilder pb = new ProcessBuilder("powershell.exe ", "shutdown -s -t 10");

        try {
            Process p = pb.start();

            p.waitFor();

            try {
                return p.exitValue() == 0;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
