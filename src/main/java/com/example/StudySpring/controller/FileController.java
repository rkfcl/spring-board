package com.example.StudySpring.controller;

import com.example.StudySpring.entity.BoardFile;
import com.example.StudySpring.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Integer id) throws MalformedURLException {
        // 파일 정보 가져오기
        BoardFile file = fileService.file(id);

        // 파일 경로 설정
        String filePath = "C:/springboot_img/" + file.getStoredFileName();

        // UrlResource를 사용하여 파일 다운로드
        UrlResource urlResource = new UrlResource("file:" + filePath);

        // 파일 이름 인코딩 및 Content-Disposition 설정
        String encodeUploadFileName = UriUtils.encode(file.getOriginalFileName(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }

    @GetMapping("/img/{name}")
    public void loadFile(@PathVariable("name") String name, HttpServletResponse response) throws IOException {
        String filePath = "C:/Users/kwmkr/OneDrive/바탕 화면/갈치커뮤니티/StudySpring/src/main/resources/static/files/";

        // 실제 파일 경로 설정
        File file = new File(filePath + name);

        if (file.exists()) {
            // 파일이 존재하는 경우, 파일 내용을 클라이언트에게 반환
            response.setContentType("image/jpeg"); // 이미지 타입에 맞게 Content-Type 설정
            FileInputStream fis = new FileInputStream(file);
            IOUtils.copy(fis, response.getOutputStream());
            fis.close();
        } else {
            // 파일이 존재하지 않는 경우, 404 오류 반환
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("/upload/{name}")
    public void viewFile(@PathVariable("name") String name, HttpServletResponse response) throws IOException {
        String filePath = "C:/springboot_img/";

        // 실제 파일 경로 설정
        File file = new File(filePath + name);

        if (file.exists()) {
            // 파일이 존재하는 경우, 파일 내용을 클라이언트에게 반환
            response.setContentType("image/jpeg"); // 이미지 타입에 맞게 Content-Type 설정
            FileInputStream fis = new FileInputStream(file);
            IOUtils.copy(fis, response.getOutputStream());
            fis.close();
        } else {
            // 파일이 존재하지 않는 경우, 404 오류 반환
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("/files/{id}")
    public List<BoardFile> findFiles(@PathVariable("id") Integer id) throws IOException {
        List<BoardFile> fileEntities = fileService.findByBoardId(id);
        return fileEntities;
    }
}


