package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * FROM credentials WHERE userid = #{userId}")
    List<Credential> getCredentials(int userId);

    @Insert("INSERT INTO credentials (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Update("UPDATE credentials SET url = #{url}, username = #{username}, key = #{key}, password = #{password}, userid = #{userId} WHERE credentialId = #{credentialId}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialId = #{credentialId}")
    void deleteCredential(int credentialId);

    @Select("SELECT * FROM credentials WHERE credentialId = #{credentialId}")
    Credential getCredentialById(int credentialId);
}
