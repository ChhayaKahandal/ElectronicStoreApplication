package com.electroni.store.ElectronicStoreApp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService
{
     //public String uploadFile(MultipartFile file,String path);//first parameter is file,second parameter is path where we have to upload that file.
     String uploadFile(MultipartFile file,String path) throws IOException;
     //public InputStream getResource(String path);
     InputStream getResource(String path,String name) throws FileNotFoundException;
}
