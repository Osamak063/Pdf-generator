package com.project.pdfgenerator.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResourcesUtils {

    public static String GetPDfTemplate() throws Exception {
        try{
            File file = ResourceUtils.getFile("classpath:templates/pdftemplate.html");
            if(!file.exists())
                throw new IOException();
            String content = new String(Files.readAllBytes(file.toPath()));
            return content;
        }
        catch (IOException e){
            throw new Exception("IO Exception: Error reading pdftemplate file.");
        }
    }

    public static String GetTableTemplate() throws Exception {
        try{
            File file = ResourceUtils.getFile("classpath:templates/tabletemplate.html");
            if(!file.exists())
                throw new IOException();
            String content = new String(Files.readAllBytes(file.toPath()));
            return content;
        }
        catch (IOException e){
            throw new Exception("IO Exception: Error reading tabletemplate file.");
        }
    }
}
