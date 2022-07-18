package pck.rcserver.api.response;

import pck.rcserver.api.request.REQUEST_TYPE;

import java.util.Arrays;

public class TakeScreenshotResponse extends BaseResponse {
    private byte[] buffer;

    public TakeScreenshotResponse(RESPONSE_STATUS status, String msg, REQUEST_TYPE type) {
        super(status, msg, type);
    }

    public TakeScreenshotResponse(RESPONSE_STATUS status, String msg, REQUEST_TYPE type, byte[] buffer) {
        super(status, msg, type);
        this.buffer = buffer;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    @Override
    public String toString() {
        return "TakeScreenshotResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                ", buffer size =" + buffer.length +
                '}';
    }
}
