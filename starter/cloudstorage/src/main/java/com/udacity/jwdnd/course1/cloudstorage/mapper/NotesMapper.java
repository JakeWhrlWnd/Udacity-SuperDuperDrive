package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT * FROM notes WHERE userid = #{userId}")
    List<Note> getNotes(int userId);

    @Insert("INSERT INTO notes (noteTitle, noteDescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Delete("DELETE FROM notes WHERE noteId = #{noteId}")
    void deleteNote(int noteId);

    @Select("SELECT * FROM notes WHERE noteId = #{noteId}")
    Note getNoteById(int noteId);

    @Update("UPDATE notes SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}")
    void updateNote(Note note);
}
