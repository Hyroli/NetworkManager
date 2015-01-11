/*
 * 
 * Partly from BungeeCord by md_5 (https://github.com/SpigotMC/BungeeCord)
 * 
 */
package com.vivi7865.Network.Manager.log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.vivi7865.Network.Manager.NetworkManager;

public class NMLogger extends Logger {

    private final Formatter formatter = new ConciseFormatter();
    private final LogDispatcher dispatcher = new LogDispatcher(this);

    public NMLogger(NetworkManager manager) {
        super("NetworkManager", null);
        setLevel(Level.ALL);

        try {
        	new File("log").mkdir();
            FileHandler fileHandler = new FileHandler("log/manager.log", 1 << 24, 8, true);
            fileHandler.setFormatter(formatter);
            addHandler(fileHandler);

            ColouredWriter consoleHandler = new ColouredWriter(manager.getConsoleReader());
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFormatter(formatter);
            addHandler(consoleHandler);
        } catch (IOException ex) {
            System.err.println("Could not register logger!");
            ex.printStackTrace();
        }
        dispatcher.start();
    }

    @Override
    public void log(LogRecord record) {
        dispatcher.queue(record);
    }

    void doLog(LogRecord record) {
        super.log(record);
    }
}