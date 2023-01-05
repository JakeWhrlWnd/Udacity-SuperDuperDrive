package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exception.FileAlreadyExistsException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;

    @PostMapping("/files")
    public String uploadFile(Model model, @RequestParam("fileUpload") MultipartFile file, Authentication authentication,
                             @ModelAttribute("note") NoteForm note, @ModelAttribute("credential") CredentialForm credentialForm) throws FileAlreadyExistsException {
        String username = authentication.getName();
        int userId = usersMapper.getUser(username).getUserId();
        if (file.isEmpty()) {
            model.addAttribute("errorMessage", "File is empty");
        } else {
            File fileObj = new File();
            fileObj.setContentType(file.getContentType());
            fileObj.setFilename(file.getOriginalFilename());
            fileObj.setUserId(userId);
            fileObj.setFileSize(file.getSize() + "");

            try {
                fileObj.setFileDate(file.getBytes());
                fileService.createFile(fileObj);
                model.addAttribute("success", "File saved");
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Unknown error");
            }
        }
        model.addAttribute("tab", "nav-files-tab");
        model.addAttribute("files", fileService.getUserFiles(userId));
        model.addAttribute("notes", noteService.getUserNotes(userId));
        model.addAttribute("credentials", credentialService.getUserCredentials(userId));

        return "home";
    }

    @RequestMapping(value = {"/files/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<byte[]> viewFile(@PathVariable(name = "id") String id, HttpServletResponse response, HttpServletRequest request) {
        File file = fileService.getFileById(Integer.parseInt(id));
        byte[] fileContents = file.getFileDate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(file.getContentType()));
        String filename = file.getFilename();
        httpHeaders.setContentDispositionFormData(filename, filename);
        httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(fileContents, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "files/delete/{id}")
    private String deleteFile(@PathVariable(name = "id") String id, RedirectAttributes redirectAttributes) {
        fileService.deleteFile(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("tab", "nav-files-tab");
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/home";
    }
}
