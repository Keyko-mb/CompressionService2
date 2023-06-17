package ru.shafikovs.ComressionService.Services;

import org.junit.jupiter.api.Test;
import ru.shafikovs.ComressionService.Models.FileMetadata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CompressionServiceTest {

    private final CompressionService compressionService = new CompressionService();

    @Test
    void shouldCompress() throws IOException {
        File testFile = new File("src\\test\\resources\\The Lady with the Dog.txt");
        FileMetadata fileMetadata = new FileMetadata(testFile.getName(), "text/plain");
        double sizeBeforeCompression = testFile.length();

        String compressedTestFilePath = compressionService.compress(Files.readAllBytes(testFile.toPath()), fileMetadata);
        Path compressedFile = Path.of(compressedTestFilePath);
        double sizeAfterCompression = compressedFile.toFile().length();

        boolean isCompressed = sizeBeforeCompression > sizeAfterCompression;
        assertThat(isCompressed).isTrue();

    }
}