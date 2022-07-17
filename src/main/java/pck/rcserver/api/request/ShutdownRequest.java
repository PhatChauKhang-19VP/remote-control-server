package pck.rcserver.api.request;

public class ShutdownRequest extends BaseRequest {
    public ShutdownRequest() {
        super(REQUEST_TYPE.SHUTDOWN);
    }
}
