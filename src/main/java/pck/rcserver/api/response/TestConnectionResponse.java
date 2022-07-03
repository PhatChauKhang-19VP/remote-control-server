package pck.rcserver.api.response;

import pck.rcserver.api.request.REQUEST_TYPE;

public class TestConnectionResponse extends BaseResponse {
    public TestConnectionResponse(RESPONSE_STATUS status, String msg) {
        super(status, msg, REQUEST_TYPE.TEST_CONNECTION);
    }
}
