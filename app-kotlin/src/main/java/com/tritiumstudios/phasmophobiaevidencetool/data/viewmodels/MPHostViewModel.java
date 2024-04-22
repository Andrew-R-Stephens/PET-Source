package com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels;

import androidx.lifecycle.ViewModel;

/**
 * MPHostViewModel class
 *
 * @author TritiumGamingStudios
 */
public class MPHostViewModel extends ViewModel {

    private String hostIP = "";
    private final short defaultPort = 11900;
    private int connectedPort = defaultPort;

    private String nameAlias = "NA";

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public int getConnectedPort() {
        return connectedPort;
    }

    public void setConnectedPort(int connectedPort) {
        this.connectedPort = connectedPort;
    }

    public String getNameAlias() {
        return nameAlias;
    }

    public void setNameAlias(String nameAlias) {
        this.nameAlias = nameAlias;
    }

    public void init() {
        reset();
    }

    public void reset() {
        hostIP = "";
        connectedPort = defaultPort;
        nameAlias = "NA";
    }
}
