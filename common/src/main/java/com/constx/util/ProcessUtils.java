package com.constx.util;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class ProcessUtils {

    public static ProcessExecuteResult execute(String[] command) {
        ProcessExecuteResult result = new ProcessExecuteResult();
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        try {
            String line;
            Process process = Runtime.getRuntime().exec(command);

            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder outBuilder = new StringBuilder();
            while ((line = stdInput.readLine()) != null) {
                outBuilder.append(line).append("\n");
            }
            if (outBuilder.length() > 0) {
                result.setOut(outBuilder.toString());
            }

            StringBuilder errBuilder = new StringBuilder();
            while ((line = stdError.readLine()) != null) {
                errBuilder.append(line).append("\n");
            }
            if (errBuilder.length() > 0) {
                result.setErr(errBuilder.toString());
            }

            int exitCode = process.waitFor();
            result.setCode(exitCode);

            return result;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            IOUtils.closeQuietly(stdInput);
            IOUtils.closeQuietly(stdError);
        }
    }
}
