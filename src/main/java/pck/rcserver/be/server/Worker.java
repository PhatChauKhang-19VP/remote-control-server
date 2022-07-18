package pck.rcserver.be.server;

import pck.rcserver.api.API;
import pck.rcserver.api.request.*;
import pck.rcserver.api.response.*;
import pck.rcserver.helper.FileHelper;
import pck.rcserver.windowAPI.WinAPI;
import pck.rcserver.windowAPI.WinApp;
import pck.rcserver.windowAPI.WinProcess;

import java.io.File;
import java.util.ArrayList;

public class Worker extends Thread {
    private final Client client;

    public Worker(Client client) {
        this.client = client;
    }

    //Handle client request and send response from server to client
    public void run() {
        System.out.println("Processing: " + client.getSocket());

        while (!Server.getServerSocket().isClosed() && client.getSocket() != null && !client.getSocket().isClosed()) {
            BaseRequest request = API.getClientRequest(client);

            System.out.println("Worker receive request: " + request);

            BaseResponse res = handleClientRequest(request);
            if (res == null) {
                break;
            }

            //Send response to client
            API.sendResponse(client, res);

            System.out.println("Worker send response: " + res);

            System.out.println("worker is waiting for another request");
        }
        System.out.println("worker terminated");
    }

    public BaseResponse handleClientRequest(BaseRequest request) {
        if (request == null) {
            return null;
        }

        REQUEST_TYPE requestType = request.getType();

        switch (requestType) {
            case TEST_CONNECTION -> {
                Request req = (Request) request;
                return new Response(RESPONSE_STATUS.SUCCESS, "Server is now working", requestType);
            }

            case GET_LIST_RUNNING_PROCESS -> {
                Request getListRunningProcessRequest = (Request) request;

                ArrayList<WinProcess> winProcesses = WinAPI.getListRunningProcess();

                if (winProcesses == null) {
                    return new GetListRunningProcessResponse(
                            RESPONSE_STATUS.FAILED,
                            "Server or win API error",
                            REQUEST_TYPE.GET_LIST_RUNNING_PROCESS
                    );
                }

                return new GetListRunningProcessResponse(
                        RESPONSE_STATUS.SUCCESS,
                        "Get running processes successfully",
                        REQUEST_TYPE.GET_LIST_RUNNING_PROCESS,
                        winProcesses
                );
            }

            case START_PROCESS -> {
                StartProcessRequest startProcessRequest = (StartProcessRequest) request;

                if (WinAPI.startProcess(startProcessRequest.getProcessName())) {
                    return new Response(
                            RESPONSE_STATUS.SUCCESS,
                            "Process with name = " + startProcessRequest.getProcessName() + " successfully",
                            REQUEST_TYPE.START_PROCESS
                    );
                }

                return new Response(
                        RESPONSE_STATUS.FAILED,
                        "Start process failed",
                        REQUEST_TYPE.START_PROCESS
                );
            }
            case STOP_PROCESS -> {
                StopProcessRequest stopProcessRequest = (StopProcessRequest) request;

                if (WinAPI.stopProcess(stopProcessRequest.getPid())) {
                    return new Response(
                            RESPONSE_STATUS.SUCCESS,
                            "Process with id = " + stopProcessRequest.getPid() + " has been killed",
                            REQUEST_TYPE.STOP_PROCESS
                    );
                }

                return new Response(
                        RESPONSE_STATUS.FAILED,
                        "Stop process failed",
                        REQUEST_TYPE.STOP_PROCESS
                );
            }
            case GET_LIST_RUNNING_APP -> {
                Request getListRunningAppReq = (Request) request;

                ArrayList<WinApp> winApps = WinAPI.getListRunningApp();

                if (winApps == null) {
                    return new GetListRunningAppResponse(
                            RESPONSE_STATUS.FAILED,
                            "Server or WinAPI error",
                            REQUEST_TYPE.GET_LIST_RUNNING_APP
                    );
                }

                return new GetListRunningAppResponse(
                        RESPONSE_STATUS.SUCCESS,
                        "Get apps successfully",
                        REQUEST_TYPE.GET_LIST_RUNNING_APP,
                        winApps
                );
            }
            case START_APP -> {
                StartApplicationRequest startApplicationRequest = (StartApplicationRequest) request;

                if (WinAPI.startApp(startApplicationRequest.getAppName())) {
                    return new Response(
                            RESPONSE_STATUS.SUCCESS,
                            "App with name = " + startApplicationRequest.getAppName() + " has been started successfully",
                            REQUEST_TYPE.START_APP
                    );
                }

                return new Response(
                        RESPONSE_STATUS.FAILED,
                        "App with name = " + startApplicationRequest.getAppName() + " has been started failed",
                        REQUEST_TYPE.START_APP
                );
            }
            case STOP_APP -> {
                StopApplicationRequest stopApplicationRequest = (StopApplicationRequest) request;

                if (WinAPI.stopApp(stopApplicationRequest.getAppName())) {
                    return new Response(
                            RESPONSE_STATUS.SUCCESS,
                            "App with name = " + stopApplicationRequest.getAppName() + " has been stopped",
                            REQUEST_TYPE.STOP_APP
                    );
                }

                return new Response(
                        RESPONSE_STATUS.FAILED,
                        "Stop app failed",
                        REQUEST_TYPE.STOP_APP
                );
            }
            case TAKE_SCREENSHOT -> {
                Request getListRunningAppReq = (Request) request;
                if (WinAPI.takeScreenshot()) {
                    File file = new File("screenshot.png");
                    byte[] buffer = FileHelper.getFileBuffer(file);

                    return new TakeScreenshotResponse(
                            RESPONSE_STATUS.SUCCESS,
                            "Take screenshot successfully",
                            REQUEST_TYPE.TAKE_SCREENSHOT,
                            buffer

                    );
                }

                return new TakeScreenshotResponse(
                        RESPONSE_STATUS.FAILED,
                        "Take screenshot failed",
                        REQUEST_TYPE.TAKE_SCREENSHOT,
                        null
                );
            }
            case KEYSTROKE_HOOK -> {
                Request keyStrokeHook = (Request) request;

                client.setKeyboardHook(WinAPI.keyStrokeHook(client.getKeypressList()));

                if (client.getKeyboardHook() != null) {
                    return new Response(
                            RESPONSE_STATUS.SUCCESS,
                            "Keystroke hook activated",
                            REQUEST_TYPE.KEYSTROKE_HOOK
                    );
                }

                return new Response(
                        RESPONSE_STATUS.FAILED,
                        "Keystroke hook failed",
                        REQUEST_TYPE.KEYSTROKE_HOOK
                );
            }

            case KEYSTROKE_UNHOOK -> {
                Request keyStrokeUnhook = (Request) request;

                if (WinAPI.keyStrokeUnhook(client.getKeyboardHook())) {
                    return new KeyStrokeUnhookResponse(
                            RESPONSE_STATUS.SUCCESS,
                            "Keystroke unhook successfully",
                            REQUEST_TYPE.KEYSTROKE_UNHOOK,
                            client.getKeypressList()
                    );
                }

                return new KeyStrokeUnhookResponse(
                        RESPONSE_STATUS.FAILED,
                        "Keystroke unhook failed",
                        REQUEST_TYPE.KEYSTROKE_UNHOOK,
                        client.getKeypressList()
                );
            }
            case SHUTDOWN -> {
                if (WinAPI.shutdown()) {
                    return new Response(
                            RESPONSE_STATUS.SUCCESS,
                            "Target PC will shut down in 2 second",
                            REQUEST_TYPE.SHUTDOWN
                    );
                }

                return new Response(
                        RESPONSE_STATUS.FAILED,
                        "PC shut down failed",
                        REQUEST_TYPE.SHUTDOWN
                );
            }
            default -> {
                return null;
            }
        }
    }
}
