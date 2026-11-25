package dev.vortexnode.sbrowserx.extension;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public final class SBrowserXWebRtcManager {
    private static final String TAG = "SBrowserXProfilePrefs";

    private SBrowserXWebRtcManager() {}

    public static void applyToProfile(Context context) {
        if (context == null) return;
        try {
            String policy = SBrowserXPrefs.getWebRtcPolicy(context);
            boolean antiFingerprint = SBrowserXPrefs.isFingerprintProtectionEnabled(context);

            File sbrowserDir = context.getDir("sbrowser", Context.MODE_PRIVATE);
            File defaultDir = new File(sbrowserDir, "Default");
            if (!defaultDir.exists()) {
                defaultDir.mkdirs();
            }
            File prefsFile = new File(defaultDir, "Preferences");
            JSONObject root;
            if (prefsFile.isFile() && prefsFile.length() > 0) {
                String text = readUtf8(prefsFile);
                root = text.trim().startsWith("{") ? new JSONObject(text) : new JSONObject();
            } else {
                root = new JSONObject();
            }

            JSONObject webrtc = root.optJSONObject("webrtc");
            if (webrtc == null) {
                webrtc = new JSONObject();
                root.put("webrtc", webrtc);
            }
            webrtc.put("ip_handling_policy", policy);
            boolean allowNonProxiedUdp = !SBrowserXPrefs.WEBRTC_DISABLE_NON_PROXIED_UDP.equals(policy);
            webrtc.put("nonproxied_udp_enabled", allowNonProxiedUdp);

            // Sync native Chromium/Terrace fingerprinting protection preference
            root.put("enable_block_fingerprinting", antiFingerprint);

            File tmpFile = new File(defaultDir, "Preferences.sbrowserx.tmp");
            writeUtf8(tmpFile, root.toString());
            if (!tmpFile.renameTo(prefsFile)) {
                writeUtf8(prefsFile, root.toString());
                tmpFile.delete();
            }
            Log.i(TAG, "Applied WebRTC=" + policy + ", antiFingerprint=" + antiFingerprint);
        } catch (Throwable t) {
            Log.e(TAG, "Failed to apply profile prefs", t);
        }
    }

    private static String readUtf8(File file) throws Exception {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = fis.read(buf)) != -1) {
                bos.write(buf, 0, n);
            }
            return bos.toString("UTF-8");
        }
    }

    private static void writeUtf8(File file, String content) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
            fos.getFD().sync();
        }
    }
}
