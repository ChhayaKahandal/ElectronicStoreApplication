package com.electroni.store.ElectronicStoreApp.services.impl;

import com.electroni.store.ElectronicStoreApp.exception.BadApiRequest;
import com.electroni.store.ElectronicStoreApp.services.FileService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService
{

    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    //this logic is for upload image
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        //we are getting original file name  from MultipartFile.
        String originalFilename = file.getOriginalFilename();
         logger.info("Filename:{}",originalFilename);
         //jar 2 file same name chya generate hou shaktat.tyasathi apan file name la randomly generate kru.
        //ani original file name sobt tyala append krun.jar abc.png asel tr abc.pngxyz.xyz is random filename la extension sobt jodal.
        String filename= UUID.randomUUID().toString();
        //abc.png ya mdhe png extension la fetch kel "extension" mdhe.
        String extension=originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNamewithExtension=filename+extension;
        //ani jo path yeil tyachysaobt apn tya filename with extension la join kel with slash/.(file.separator ne slash milto)
        String fullPathWithFileName=path+File.separator+fileNamewithExtension;

        logger.info("full image path: {} ",fullPathWithFileName);
        //we are checking for extension
        if(extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".jpeg"))
        {
           //file save
            logger.info("file extension is {} ",extension);
            File folder=new File(path);
            if(!folder.exists())//jar folder nasel tr create kr
            {
                //create the folder in working directory.ithe project mdhe images navch folder create hoil
                folder.mkdirs();
            }
            //upload file
            Files.copy(file.getInputStream(),Paths.get(fullPathWithFileName));//first param:multipart file mdhun input stream kadhli second param:ani jithe ti copy krychi ahe to path dila.
             return fileNamewithExtension;
        }
        else
        {
            throw new BadApiRequest("File with this " +extension + "not allowed !!");

        }

    }

    //this method is for get that image
    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath=path+File.separator+name;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
