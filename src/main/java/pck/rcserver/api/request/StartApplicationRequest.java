package pck.rcserver.api.request;

public class StartApplicationRequest extends BaseRequest {
    private String nameApp;

    public StartApplicationRequest(String nameApp) {
        super(REQUEST_TYPE.START_APP);
        this.nameApp = nameApp;
    }
}
