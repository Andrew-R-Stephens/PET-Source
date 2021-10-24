package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import androidx.lifecycle.ViewModel;

/**
 * MPClientViewModel class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class MPClientViewModel extends ViewModel {

    private String hostIP = "";
    private final short defaultPort = 11900;
    private int connectedPort = defaultPort;

    private String nameAlias = "NA";

    /**
     * getHostIP
     *
     * TODO
     *
     * @return
     */
    public String getHostIP() {
        return hostIP;
    }

    /**
     * setHostIP
     *
     * TODO
     *
     * @param hostIP
     */
    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    /**
     * getConnectedPort
     *
     * TODO
     */
    public int getConnectedPort() {
        return connectedPort;
    }

    /**
     * setConnectedPort
     *
     * TODO
     *
     * @param connectedPort
     */
    public void setConnectedPort(int connectedPort) {
        this.connectedPort = connectedPort;
    }

    /**
     * getNameAlias
     *
     * TODO
     *
     * @return
     */
    public String getNameAlias() {
        return nameAlias;
    }

    /**
     * setNameAlias
     *
     * TODO
     *
     * @param nameAlias
     */
    public void setNameAlias(String nameAlias) {
        this.nameAlias = nameAlias;
    }

    /**
     * init
     *
     * TODO
     */
    public void init() {
        reset();
    }

    /**
     * reset
     *
     * TODO
     */
    public void reset() {
        hostIP = "";
        connectedPort = defaultPort;
        nameAlias = "NA";
    }
}
