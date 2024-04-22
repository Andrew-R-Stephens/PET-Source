package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.evidence.children.mp.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * MPHost class
 *
 * @author TritiumGamingStudios
 */
public class MPHost extends AbstractMPMember {

    private final int DEFAULT_PORT = 0;
    @Nullable
    private ServerSocket server = null;

    private final String lobbyName;

    /**
     * @param lobbyName
     */
    public MPHost(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    /**
     * @throws IOException
     */
    public void openServer() throws IOException {
        server = new ServerSocket(0);
        System.out.println(server.getLocalPort());
    }

    /**
     * @return
     */
    @NonNull
    public String getLobbyCode() {
        StringBuilder lobbyCode = new StringBuilder();

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
                for (int j = 0; j < hexa[i].length(); j++) {
                    int loc = hexa[i].charAt(j);
                    lobbyCode.append(alphabet.charAt(alphabet.indexOf(loc) + 9));
                }
                /*
                hexa[i] = tempHexa;
                System.out.println(", New Hex: " + hexa[i]);
                 */
            }
        }

        return lobbyCode.toString();
    }

}
