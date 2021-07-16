package com.dermacon.downloader.logic;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;

public class AudioConverter {

    public void convertMP4ToMP3(File source, File output) {
        Encoder forMusic = new Encoder();

        EncodingAttributes specifications = new EncodingAttributes();
        specifications.setFormat("mp3");
        //audioAttribute obj
        AudioAttributes a = new AudioAttributes();
        a.setVolume(256);//default
        a.setCodec("mp2");

        specifications.setAudioAttributes(a);

        try {
            forMusic.encode(source, output, specifications);
        } catch (EncoderException ex) {
            ex.printStackTrace();
        }
    }
}
