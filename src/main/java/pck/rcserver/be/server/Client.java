package pck.rcserver.be.server;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lc.kra.system.keyboard.GlobalKeyboardHook;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private final Socket socket;
    private final StringProperty username;
    private ArrayList<Integer> keypressList = new ArrayList<>();
    private GlobalKeyboardHook keyboardHook = null;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    public Client(Socket socket) {
        username = new SimpleStringProperty();
        username.setValue("unknown" + socket.getPort());

        this.socket = socket;

        try {
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public DataInputStream getDataIn() {
        return dataIn;
    }

    public void setDataIn(DataInputStream dataIn) {
        this.dataIn = dataIn;
    }

    public DataOutputStream getDataOut() {
        return dataOut;
    }

    public void setDataOut(DataOutputStream dataOut) {
        this.dataOut = dataOut;
    }

    public ArrayList<Integer> getKeypressList() {
        return keypressList;
    }

    public void setKeypressList(ArrayList<Integer> keypressList) {
        this.keypressList = keypressList;
    }

    public GlobalKeyboardHook getKeyboardHook() {
        return keyboardHook;
    }

    public void setKeyboardHook(GlobalKeyboardHook keyboardHook) {
        this.keyboardHook = keyboardHook;
    }
}
