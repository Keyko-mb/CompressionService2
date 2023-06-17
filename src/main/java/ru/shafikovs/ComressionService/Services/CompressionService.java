package ru.shafikovs.ComressionService.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shafikovs.ComressionService.Models.FileMetadata;
import ru.shafikovs.ComressionService.Models.HuffmanCode;
import ru.shafikovs.ComressionService.Models.Node;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class CompressionService {
    public String compress(byte[] bytes, FileMetadata metadata) throws IOException {
        Coder coder = new Coder();
        HuffmanCode huffmanCode = coder.compress(bytes);
        log.info("Huffman code is created");

        return writeBinFile(huffmanCode.getHuffmanTree(), huffmanCode.getCode(), metadata);
    }

    public String writeBinFile(Node huffmanTree, String text, FileMetadata metadata) throws IOException {
        Path compressedFile = Files.createTempFile("compressedFile", ".bin");
        String filePath = String.valueOf(compressedFile.toAbsolutePath());

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
        objectOutputStream.writeObject(huffmanTree);
        objectOutputStream.close();
        log.info("Huffman tree was written");

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filePath, true))) {
            dos.writeShort(metadata.getOriginalFileName().length());
            dos.writeChars(metadata.getOriginalFileName());
            dos.writeShort(metadata.getOriginalFormat().length());
            dos.writeChars(metadata.getOriginalFormat());
            log.info("File metadata was written");

            for (int i = 0; i < text.length(); i += 8) {
                if (i + 8 >= text.length()) {
                    int ost = text.length() - i;
                    int intValue = Integer.parseInt((text.substring(i, i + ost)), 2);
                    dos.write(intValue);
                } else {
                    int intValue = Integer.parseInt((text.substring(i, i + 8)), 2);
                    dos.write(intValue);
                }
            }

            log.info("Huffman code was written");

        } catch (IOException e) {
            e.printStackTrace();
            log.error("Writing error {}", e.getMessage());
        }


        return filePath;
    }
}
