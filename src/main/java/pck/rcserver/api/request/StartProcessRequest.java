package pck.rcserver.api.request;

public class StartProcessRequest extends BaseRequest {
    private String nameProcess;

    public StartProcessRequest(String nameProcess) {
        super(REQUEST_TYPE.START_PROCESS);
        this.nameProcess = nameProcess;
    }
}
