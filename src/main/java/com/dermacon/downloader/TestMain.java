package com.dermacon.downloader;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {

    public static void main(String[] args) {
        Encoder forMusic = new Encoder();

        EncodingAttributes specifications = new EncodingAttributes();
        specifications.setFormat("mp3");
        //audioAttribute obj
        AudioAttributes a = new AudioAttributes();
        a.setVolume(256);//default
        a.setCodec("mp2");

        specifications.setAudioAttributes(a);

        try{
            forMusic.encode(new File("test.mp4"), new File("test.mp3"), specifications);
        }
        catch(EncoderException ex){
            ex.printStackTrace();
        }
    }

    public static void convertMp4ToMp3(File source, File output){
        Encoder forMusic = new Encoder();

        EncodingAttributes specifications = new EncodingAttributes();
        specifications.setFormat("mp3");
        //audioAttribute obj
        AudioAttributes a = new AudioAttributes();
        a.setVolume(256);//default
        a.setCodec("mp2");

        specifications.setAudioAttributes(a);

        try{
            forMusic.encode(source, output, specifications);
        }
        catch(EncoderException ex){
            ex.printStackTrace();
        }
    }
}
