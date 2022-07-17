package pck.rcserver.api.request;

public class GetListRunningProcessRequest extends BaseRequest {
    public GetListRunningProcessRequest() {
        super(REQUEST_TYPE.GET_LIST_RUNNING_PROCESS);
    }

    public String toString() {
        return "GetListRunningProcessRequest{" +
                "type=" + type +
                '}';
    }
}
