package pck.rcserver.api.request;

public class StopProcessRequest extends BaseRequest {
    private Integer pid;

    public StopProcessRequest(Integer pid) {
        super(REQUEST_TYPE.STOP_PROCESS);
        this.pid = pid;
    }
}
