package pck.rcserver.api;

import pck.rcserver.api.request.*;
import pck.rcserver.api.response.*;
import pck.rcserver.be.server.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class API {
    public static void main(String[] args) {
    }

    public static BaseRequest getClientRequest(Client client){
        try {
            DataInputStream dataIn = client.getDataIn();

            //Read request type
            REQUEST_TYPE requestType = REQUEST_TYPE.valueOf(dataIn.readUTF());

            switch(requestType) {
                case TEST_CONNECTION -> {
                    return new TestConnectionRequest();
                }

                case GET_LIST_RUNNING_PROCESS -> {
                    return new GetListRunningProcessRequest();
                }

                case START_PROCESS -> {
                    String nameProcess = dataIn.readUTF();
                    return new StartProcessRequest(nameProcess);
                }

                case STOP_PROCESS -> {
                    Integer pid = dataIn.readInt();
                    return new StopProcessRequest(pid);
                }

                case GET_LIST_RUNNING_APP -> {
                    return new GetListRunningAppRequest();
                }

                case START_APP -> {
                    String nameApp = dataIn.readUTF();
                    return new StartApplicationRequest(nameApp);
                }

                case STOP_APP -> {
                    Integer id = dataIn.readInt();
                    return new StopApplicationRequest(id);
                }

                case SHUTDOWN -> {
                    return new ShutdownRequest();
                }

                default -> {
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean sendResponse(Client client, BaseResponse response){
        try {
            DataOutputStream dataOut = client.getDataOut();

            //Read request type
            REQUEST_TYPE requestType = response.getType();

            switch(requestType) {
                case TEST_CONNECTION -> {
                    TestConnectionResponse testConnectionResponse = (TestConnectionResponse) response;

                    dataOut.writeUTF(testConnectionResponse.getType().name());
                    dataOut.writeUTF(testConnectionResponse.getStatus().name());
                    dataOut.writeUTF(testConnectionResponse.getMsg());

                    return true;
                }

                default -> {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
