package com.dermacon.downloader.hook;

import com.dermacon.downloader.logic.Downloader;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private Downloader downloader;

    private boolean ctrlTriggered = false;
    private boolean cTriggered = false;

    public GlobalKeyListener(Downloader downloader) {
        this.downloader = downloader;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
//        System.out.println("raw code: " + e.getRawCode());
//        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        switch (e.getKeyCode()) {
            case NativeKeyEvent.VC_C:
                cTriggered = true;
                break;
            case NativeKeyEvent.VC_CONTROL_R:
            case NativeKeyEvent.VC_CONTROL_L:
                ctrlTriggered = true;
                break;
            case NativeKeyEvent.VC_F9:
                ctrlTriggered = true;
                cTriggered = true;
                break;
        }

        if (cTriggered && ctrlTriggered) {
//            System.out.println("Download triggered");
            try {
                Thread.sleep(10);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            downloader.downloadClipboardLink();
            ctrlTriggered = false;
            cTriggered = false;
        }


        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        switch (e.getKeyCode()) {
            case NativeKeyEvent.VC_C:
                cTriggered = false;
                break;
            case NativeKeyEvent.VC_CONTROL_R:
            case NativeKeyEvent.VC_CONTROL_L:
                ctrlTriggered = false;
                break;
            case NativeKeyEvent.VC_F9:
                ctrlTriggered = false;
                cTriggered = false;
                break;
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }

}


