package pck.rcserver.api.response;

import pck.rcserver.api.request.REQUEST_TYPE;

public class Response extends BaseResponse{
    public Response(RESPONSE_STATUS status, String msg, REQUEST_TYPE type) {
        super(status, msg, type);
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                '}';
    }
}
