package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class File {
    private int fileId;
    private String filename;
    private String contentType;
    private String fileSize;
    private int userId;
    private byte[] fileDate;
}
