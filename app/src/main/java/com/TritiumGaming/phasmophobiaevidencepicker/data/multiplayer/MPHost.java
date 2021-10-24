package com.TritiumGaming.phasmophobiaevidencepicker.data.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * MPHost class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class MPHost extends MPMember{

    private final int DEFAULT_PORT = 0;
    private ServerSocket server = null;

    private final String lobbyName;

    /**
     * MPHost
     *
     * TODO
     *
     * @param lobbyName
     */
    public MPHost(String lobbyName){
        this.lobbyName = lobbyName;
    }

    /**
     * openServer
     *
     * TODO
     *
     * @throws IOException
     */
    public void openServer() throws IOException {
        server = new ServerSocket(0);
        System.out.println(server.getLocalPort());
    }

    /**
     * getLobbyCode
     *
     * TODO
     *
     * @return
     */
    public String getLobbyCode(){
        String lobbyCode = "";

        String ip = "24.47.180.250";
        String[] ipArray = ip.split("\\.");

        String alphabet = "123456789abcdefghijklmnopqrstuvwxyz";
        String[] hexa = new String[ipArray.length];

        //String[] binary = new String[ipArray.length];
        for (int i = 0; i < ipArray.length; i++) {
            int temp = Integer.parseInt(ipArray[i]);
            if (temp > 0 && temp <= 255) {
                //binary[i] = (Integer.toBinaryString(temp));
                //System.out.print("Binary: " + binary[i]);
                hexa[i] = (Integer.toHexString(temp));
                System.out.print(", Hex: " + hexa[i]);
                for(int j = 0; j < hexa[i].length(); j++) {
                    int loc = hexa[i].charAt(j);
                    lobbyCode += alphabet.charAt(alphabet.indexOf(loc)+9);
                }
                /*
                hexa[i] = tempHexa;
                System.out.println(", New Hex: " + hexa[i]);
                 */
            }
        }

        return lobbyCode;
    }

}
