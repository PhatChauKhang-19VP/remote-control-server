package pck.rcserver;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import pck.rcserver.windowAPI.WinApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class Test1 {
    public static void main(String[] args) {
        ProcessHandle.allProcesses()
                .forEach(process -> System.out.println(processDetails(process)));
    }

    private static String processDetails(ProcessHandle process) {
        return process.info().toString();
    }

    private static String text(Optional<?> optional) {
        return optional.map(Object::toString).orElse("-");
    }
}

class EnumAllWindowNames {
    public static String[] ignoreArray = {"Default", "MSCTFIME", "???????", "Msg",
            "SysFader"};

    public static void main(String[] args) {
        List<String> winNameList = getAllWindowNames();
        for (String winName : winNameList) {
            System.out.println(winName);
        }
    }

    public static List<String> getAllWindowNames() {
        final List<String> windowNames = new ArrayList<String>();
        final User32 user32 = User32.INSTANCE;
        user32.EnumWindows((hWnd, arg) -> {
            byte[] windowText = new byte[512];
            user32.GetWindowTextA(hWnd, windowText, 512);
            String wText = Native.toString(windowText).trim();
            if (!ignore(wText)) {
                if (user32.GetParent(hWnd) == null) {

                    windowNames.add(wText);
                }
            }
            return true;
        }, null);

        return windowNames;
    }

    public static boolean ignore(String text) {
        for (String ignoreText : ignoreArray) {
            if (ignoreText.contains(text)) {
                return true;
            }
        }
        return false;
    }

    static interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

        boolean EnumWindows(WNDENUMPROC lpEnumFunc, Pointer userData);

        int GetWindowTextA(Pointer hWnd, byte[] lpString, int nMaxCount);

        Pointer GetWindow(Pointer hWnd, int uCmd);

        Pointer GetParent(Pointer hWnd);

        Pointer GetAncestor(Pointer hWnd, int gaFlags);

        interface WNDENUMPROC extends StdCallCallback {
            boolean callback(Pointer hWnd, Pointer arg);
        }
    }

}

public class MainTest {
    public static void main(String[] args) {
        ArrayList<WinApp> winApps = new ArrayList<>();
        try {
            Process[] processes;

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(winApps.size());
    }
}

class PowerShellCommand {

    public static void main(String[] args) throws IOException {

        //String command = "powershell.exe  your command";
        //Getting the version
        // String command = "gps | ? { $_.MainWindowTitle }";
        String command = "powershell.exe gps | ? { $_.MainWindowTitle }";
        // Executing the command
        Process powerShellProcess = Runtime.getRuntime().exec(command);
        // Getting the results
        powerShellProcess.getOutputStream().close();
        String line;
        System.out.println("Standard Output:");
        BufferedReader stdout = new BufferedReader(new InputStreamReader(
                powerShellProcess.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
        }
        stdout.close();
        System.out.println("Standard Error:");
        BufferedReader stderr = new BufferedReader(new InputStreamReader(
                powerShellProcess.getErrorStream()));
        while ((line = stderr.readLine()) != null) {
            System.out.println(line);
        }
        stderr.close();
        System.out.println("Done");

    }

}
