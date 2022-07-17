package pck.rcserver.be.server;

import pck.rcserver.api.API;
import pck.rcserver.api.request.BaseRequest;
import pck.rcserver.api.request.REQUEST_TYPE;
import pck.rcserver.api.request.TestConnectionRequest;
import pck.rcserver.api.response.BaseResponse;
import pck.rcserver.api.response.RESPONSE_STATUS;
import pck.rcserver.api.response.TestConnectionResponse;

public class Worker extends Thread {
    private final Client client;

    public Worker(Client client) {
        this.client = client;
    }

    //Handle client request and send response from server to client
    public void run() {
        while(!Server.getServerSocket().isClosed()
                && client.getSocket() != null
                && client.getSocket().isClosed()) {
            BaseResponse res = handleClientRequest();
        }
    }

    public BaseResponse handleClientRequest() {
        BaseRequest request = API.getClientRequest(client);

        if(request == null) {
            return null;
        }

        REQUEST_TYPE requestType = request.getType();

        switch (requestType) {
            case TEST_CONNECTION -> {
                TestConnectionRequest testConnectionRequest = (TestConnectionRequest)request;
                return new TestConnectionResponse(RESPONSE_STATUS.SUCCESS, "Server is now working");
            }

            default -> {
                return null;
            }
        }
    }
}
