package com.electroni.store.ElectronicStoreApp.dtoclasses;

import lombok.*;
import org.springframework.http.HttpStatus;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
//this class is to send message in json format.used in delete mapping in controllers
    public class ImageResponse
    {
        private String imageName;
        private String message;
        private boolean success;//if request get success then it holds true otherwise false.
        private HttpStatus status;
    }

