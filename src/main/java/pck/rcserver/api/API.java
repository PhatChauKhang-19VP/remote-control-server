package pck.rcserver.api;

import pck.rcserver.api.request.*;
import pck.rcserver.api.response.*;
import pck.rcserver.be.server.Client;
import pck.rcserver.windowAPI.WinApp;
import pck.rcserver.windowAPI.WinProcess;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class API {
    public static void main(String[] args) {
    }

    public static BaseRequest getClientRequest(Client client) {
        try {
            DataInputStream dataIn = client.getDataIn();

            //Read request type
            REQUEST_TYPE requestType = REQUEST_TYPE.valueOf(dataIn.readUTF());

            switch (requestType) {
                case TEST_CONNECTION, GET_LIST_RUNNING_PROCESS, GET_LIST_RUNNING_APP, TAKE_SCREENSHOT, KEYSTROKE_HOOK, KEYSTROKE_UNHOOK, SHUTDOWN -> {
                    return new Request(requestType);
                }

                case START_PROCESS -> {
                    String processName = dataIn.readUTF();
                    return new StartProcessRequest(processName);
                }

                case STOP_PROCESS -> {
                    Integer pid = dataIn.readInt();
                    return new StopProcessRequest(pid);
                }

                case START_APP -> {
                    String nameApp = dataIn.readUTF();
                    return new StartApplicationRequest(nameApp);
                }

                case STOP_APP -> {
                    String appName = dataIn.readUTF();
                    return new StopApplicationRequest(appName);
                }

                default -> {
                    return null;
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean sendResponse(Client client, BaseResponse response) {
        try {
            DataOutputStream dataOut = client.getDataOut();

            //Read request type
            REQUEST_TYPE requestType = response.getType();

            switch (requestType) {
                case TEST_CONNECTION, START_PROCESS, STOP_PROCESS, START_APP, STOP_APP, KEYSTROKE_HOOK, SHUTDOWN -> {
                    Response res = (Response) response;

                    dataOut.writeUTF(res.getType().name());
                    dataOut.writeUTF(res.getStatus().name());
                    dataOut.writeUTF(res.getMsg());

                    return true;
                }
                case GET_LIST_RUNNING_PROCESS -> {
                    GetListRunningProcessResponse res = (GetListRunningProcessResponse) response;

                    dataOut.writeUTF(res.getType().name());
                    dataOut.writeUTF(res.getStatus().name());
                    dataOut.writeUTF(res.getMsg());

                    // number processes
                    dataOut.writeInt(res.winProcesses.size());

                    for (WinProcess winProcess : res.winProcesses) {
                        dataOut.writeUTF(winProcess.getImageName());
                        dataOut.writeInt(winProcess.getPid());
                        dataOut.writeUTF(winProcess.getSessionName());
                        dataOut.writeInt(winProcess.getSessionId());
                        dataOut.writeUTF(winProcess.getMemUsage());
                    }

                    return true;
                }

                case GET_LIST_RUNNING_APP -> {
                    GetListRunningAppResponse res = (GetListRunningAppResponse) response;

                    dataOut.writeUTF(res.getType().name());
                    dataOut.writeUTF(res.getStatus().name());
                    dataOut.writeUTF(res.getMsg());

                    // number apps
                    dataOut.writeInt(res.getWinApps().size());

                    for (WinApp winApp : res.getWinApps()) {
                        dataOut.writeUTF(winApp.getImageName());
                        dataOut.writeInt(winApp.getPid());
                        dataOut.writeUTF(winApp.getMemUsage());
                        dataOut.writeUTF(winApp.getPackageName());
                    }

                    return true;
                }
                case TAKE_SCREENSHOT -> {
                    TakeScreenshotResponse res = (TakeScreenshotResponse) response;

                    dataOut.writeUTF(res.getType().name());
                    dataOut.writeUTF(res.getStatus().name());
                    dataOut.writeUTF(res.getMsg());

                    // write file buffer
                    dataOut.writeInt(res.getBuffer().length);
                    dataOut.write(res.getBuffer());

                    return true;
                }
                case KEYSTROKE_UNHOOK -> {
                    KeyStrokeUnhookResponse res = (KeyStrokeUnhookResponse) response;

                    dataOut.writeUTF(res.getType().name());
                    dataOut.writeUTF(res.getStatus().name());
                    dataOut.writeUTF(res.getMsg());

                    //size of array
                    dataOut.writeInt(res.getKeypressList().size());
                    for (int keycode : res.getKeypressList()) {
                        dataOut.writeInt(keycode);
                    }

                    return true;
                }
                default -> {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
