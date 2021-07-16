package com.dermacon.downloader;

import com.dermacon.downloader.hook.GlobalKeyListener;
import com.dermacon.downloader.logic.AudioConverter;
import com.dermacon.downloader.logic.Downloader;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationMain {

	public static void main(String[] args) {
		Logger l = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		l.setLevel(Level.OFF);

		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		AudioConverter audioConverter = new AudioConverter();
		Downloader downloader = new Downloader(audioConverter);
		GlobalScreen.addNativeKeyListener(new GlobalKeyListener(downloader));
	}

}