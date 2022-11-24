package com.github.PastaLaPate.FPL_IDE.Tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Cmd {
    public static void main(String[] args) throws IOException {
        String[] command =
                {
                        "cmd",
                };
        Process p = Runtime.getRuntime().exec(command);
        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
        PrintWriter stdin = new PrintWriter(p.getOutputStream());
        stdin.println("French_Programming_Language C:\\Users\\alexa\\AppData\\Roaming\\.fpl_ide\\main.fpl");
        // write any other commands you want here
        stdin.flush();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String l = scanner.nextLine();
            stdin.println(l);
            stdin.flush();
        }
    }
}

class SyncPipe implements Runnable
{
    public SyncPipe(InputStream istrm, OutputStream ostrm) {
        istrm_ = istrm;
        ostrm_ = ostrm;
    }
    public void run() {
        try
        {
            final byte[] buffer = new byte[1024];
            for (int length; (length = istrm_.read(buffer)) != -1; )
            {
                ostrm_.write(buffer, 0, length);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private final OutputStream ostrm_;
    private final InputStream istrm_;
}