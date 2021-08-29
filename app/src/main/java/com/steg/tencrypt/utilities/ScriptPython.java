package com.steg.tencrypt.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ScriptPython {
    private Process mProcess;

    void runScript(String script,String arg){
        Process process;
        try{
            process = Runtime.getRuntime().exec(new String[]{script,arg});
            mProcess = process;
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream stream = mProcess.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

        String line;

        try {
            while ((line = reader.readLine()) != null){
                System.out.println("stream: "+line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
