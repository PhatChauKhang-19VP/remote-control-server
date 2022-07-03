package pck.rcserver.api.request;

public class TestConnectionRequest extends BaseRequest {
    public TestConnectionRequest() {
        super(REQUEST_TYPE.TEST_CONNECTION);
    }

    @Override
    public String toString() {
        return "TestConnectionReq{" +
                "type=" + type +
                '}';
    }
}
