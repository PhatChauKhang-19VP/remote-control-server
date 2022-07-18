package pck.rcserver.api.response;

import pck.rcserver.api.request.REQUEST_TYPE;
import pck.rcserver.windowAPI.WinApp;

import java.util.ArrayList;

public class GetListRunningAppResponse extends BaseResponse {
    private ArrayList<WinApp> winApps = new ArrayList<>();

    public GetListRunningAppResponse(RESPONSE_STATUS status, String msg, REQUEST_TYPE type) {
        super(status, msg, type);
    }

    public GetListRunningAppResponse(RESPONSE_STATUS status, String msg, REQUEST_TYPE type, ArrayList<WinApp> winApps) {
        super(status, msg, type);
        this.winApps = winApps;
    }

    public ArrayList<WinApp> getWinApps() {
        return winApps;
    }

    public void setWinApps(ArrayList<WinApp> winApps) {
        this.winApps = winApps;
    }

    @Override
    public String toString() {
        return "GetListRunningAppResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                ", winApps size =" + winApps.size() +
                '}';
    }
}
