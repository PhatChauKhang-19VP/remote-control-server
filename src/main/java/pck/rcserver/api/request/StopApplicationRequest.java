package pck.rcserver.api.request;

public class StopApplicationRequest extends BaseRequest {
    private Integer id;

    public StopApplicationRequest(Integer id) {
        super(REQUEST_TYPE.STOP_APP);
        this.id = id;
    }
}
