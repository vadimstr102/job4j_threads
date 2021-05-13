package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * This class is intended for downloading a file from the network with a download speed limitation.
 * Implemented the ability to use the class in a multithreaded environment.
 *
 * @author Vadim Timofeev
 * @version 1.0
 */
public class FileDownload implements Runnable {

    /**
     * Url of the downloaded file.
     */
    private final String url;

    /**
     * File download path.
     */
    private final String path;

    /**
     * Minimum download time of 1024 bytes.
     */
    private final int speed;

    public FileDownload(String url, String path, int speed) {
        this.url = url;
        this.path = path;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long startTime;
            long finishTime;
            long time;

            startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                finishTime = System.currentTimeMillis();
                time = finishTime - startTime;
                if (time < speed) {
                    try {
                        Thread.sleep(speed - time);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                startTime = System.currentTimeMillis();
            }
            System.out.println("File downloaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 3) {
            String url = args[0];
            String path = args[1];
            int speed = Integer.parseInt(args[2]);
            Thread fileDownload = new Thread(new FileDownload(url, path, speed));
            fileDownload.start();
            fileDownload.join();
        } else {
            System.out.println("Enter the correct parameters: [url path speed]");
        }
    }
}
