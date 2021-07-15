package com.dermacon.downloader;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Downloader {

    private final static int TITLE_DOWNLOAD_PREV_LEN = 10;
    private final static String URL_REGEX = ".*youtube.*?v=(.*)&list.*|.*youtube.*?v=(.*)";;

    public void downloadClipboardLink() {
        try {
            String link = readClipboardContent();
            downloadAudio(extractId(link));
        } catch (InvalidClipboardException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String readClipboardContent() throws InvalidClipboardException {
        try {
            return (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            throw new InvalidClipboardException("Clipboard cannot be read");
        }
    }

    private static String extractId(String url) throws InvalidClipboardException {
//        System.out.println("url from which id will be extracted: " + url);
        if (!url.matches(URL_REGEX)) {
            throw new InvalidClipboardException("invalid url: " + url);
        }
        Pattern r = Pattern.compile(URL_REGEX);
        Matcher m = r.matcher(url);

        m.find();
        String out = m.group(1);
        if (out == null) {
            out = m.group(2);
        }
        return out;
    }

    private static void downloadAudio(String link) {
        YoutubeDownloader downloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo(link);
        Response<VideoInfo> response = downloader.getVideoInfo(request);

        VideoInfo video = response.data();
        String title = video.details().title();

        System.out.println("\n -> download: " + createTitleLog(title));

        RequestVideoFileDownload audioReq = new RequestVideoFileDownload(video.bestVideoWithAudioFormat())
                .callback(new YoutubeProgressCallback<File>() {
                    @Override
                    public void onDownloading(int progress) {
                        System.out.printf("  => Downloaded %d%%\n", progress);
                    }

                    @Override
                    public void onFinished(File videoInfo) {
                        System.out.println(" -> Finished file: " + videoInfo);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println(" -> Error: " + throwable.getLocalizedMessage());
                    }
                })
                .renameTo(video.details().title()) // by default file name will be same as video title on youtube
                .overwriteIfExists(true)
                .async();
        downloader.downloadVideoFile(audioReq);
    }

    private static String createTitleLog(String title) {
        return title.length() < TITLE_DOWNLOAD_PREV_LEN
                ? title
                : title.substring(0, TITLE_DOWNLOAD_PREV_LEN) + "...";
    }

}
