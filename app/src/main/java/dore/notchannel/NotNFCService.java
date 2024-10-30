package dore.notchannel;

import android.app.Service;
import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.os.IBinder;
import java.nio.charset.StandardCharsets;

public class NotNFCService extends HostApduService {

    private static final String TAG = "NotNFCService";

    private static final String SELECT_APDU_HEADER = "00A40400";
    private static final String RESPONSE_OK = "9000";
    private static final String RESPONSE_ERROR = "6F00"; // Generic error

    // Dynamic JSON message that can be updated from Unity
    private static String jsonResponse = "{\"uid\":775}";

    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        String apduHex = bytesToHex(apdu);

        // Check if it's the SELECT command (APDU header check)
        if (apduHex.startsWith(SELECT_APDU_HEADER)) {
            // Convert the JSON message to bytes and return it, followed by the success status
            byte[] jsonResponseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
            byte[] responseWithStatus = concatArrays(jsonResponseBytes, hexToBytes(RESPONSE_OK));
            return responseWithStatus;
        }

        // If APDU is not recognized, return error
        return hexToBytes(RESPONSE_ERROR);
    }

    @Override
    public void onDeactivated(int reason) {
        // Handle card deactivation (not required for basic example)
    }

    // Method to allow Unity to update the JSON response
    public static void setJsonResponse(String newJsonResponse) {
        jsonResponse = newJsonResponse;
    }

    // Helper method to convert a byte array to a hex string
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    // Helper method to convert a hex string to a byte array
    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    // Helper method to concatenate two byte arrays
    private byte[] concatArrays(byte[] first, byte[] second) {
        byte[] result = new byte[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}