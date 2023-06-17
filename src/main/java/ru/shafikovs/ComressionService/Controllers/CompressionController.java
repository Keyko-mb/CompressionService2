package ru.shafikovs.ComressionService.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.shafikovs.ComressionService.Models.FileMetadata;
import ru.shafikovs.ComressionService.Services.CompressionService;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
@RestController
@RequestMapping("/compress")
public class CompressionController {
    private final CompressionService compressionService;

    @Autowired
    public CompressionController(CompressionService compressionService) {
        this.compressionService = compressionService;
    }

    @PostMapping()
    public ResponseEntity<Object> put(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFormat = file.getContentType();
        String originalFileName = file.getOriginalFilename();
        FileMetadata fileMetadata = new FileMetadata(originalFileName, originalFormat);
        log.info("File {}{} is received", originalFileName, originalFormat);

        String compressedFilePath = compressionService.compress(file.getBytes(), fileMetadata);

        Path compressedFile = Path.of(compressedFilePath);
        String compressedFileName = (originalFileName.split("\\.")[0] + "-compressed.bin").replace(" ", "_");
        log.info("Name of compressed file {}", compressedFileName);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + compressedFileName)
                .body(new FileSystemResource(compressedFile));
    }
}
