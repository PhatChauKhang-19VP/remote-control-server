package pck.rcserver.api;

import pck.rcserver.api.request.BaseRequest;
import pck.rcserver.api.request.REQUEST_TYPE;
import pck.rcserver.api.request.TestConnectionRequest;
import pck.rcserver.be.server.Client;

import java.io.DataInputStream;
import java.io.IOException;

public class API {
    public static void main(String[] args) {
    }

    public static BaseRequest getClientRequest(Client client){
        try {
            DataInputStream dataIn = client.getDataIn();

            REQUEST_TYPE requestType = REQUEST_TYPE.valueOf(dataIn.readUTF());

            switch(requestType) {
                case TEST_CONNECTION -> {
                    return new TestConnectionRequest();
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
}
