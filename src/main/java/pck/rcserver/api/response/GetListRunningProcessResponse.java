package pck.rcserver.api.response;

import pck.rcserver.api.request.REQUEST_TYPE;
import pck.rcserver.windowAPI.WinProcess;

import java.util.ArrayList;

public class GetListRunningProcessResponse extends BaseResponse {
    public ArrayList<WinProcess> winProcesses = new ArrayList<>();
    public GetListRunningProcessResponse(RESPONSE_STATUS status, String msg, REQUEST_TYPE type) {
        super(status, msg, type);
    }

    public GetListRunningProcessResponse(RESPONSE_STATUS status, String msg, REQUEST_TYPE type, ArrayList<WinProcess> winProcesses) {
        super(status, msg, type);
        this.winProcesses = winProcesses;
    }

    @Override
    public String toString() {
        return "GetListRunningProcessResponse{" +
                "winProcesses size =" + winProcesses.size() +
                '}';
    }
}
