package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    private String symbol = "/";

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
            System.out.print("\rLoading... " + process());
        }
    }

    private String process() {
        if (symbol.equals("/")) {
            symbol = "\\";
        } else if (symbol.equals("\\")) {
            symbol = "|";
        } else {
            symbol = "/";
        }
        return symbol;
    }
}
