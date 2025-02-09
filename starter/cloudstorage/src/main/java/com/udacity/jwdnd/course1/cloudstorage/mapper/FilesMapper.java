package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {
    @Select("SELECT * FROM Files WHERE userid = #{userId}")
    List<File> getFiles(int userId);

    @Insert("INSERT INTO files (filename, contentType, filesize, userid, fileData) VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM files WHERE fileId = #{fileId}")
    void deleteFile(int fileId);

    @Select("SELECT * FROM Files WHERE fileId = #{fileId}")
    File getFileById(int fileId);

    @Select("SELECT * FROM Files WHERE userid = #{userId} AND filename = #{filename}")
    List<File> getFileByFilename(int userId, String filename);
}
