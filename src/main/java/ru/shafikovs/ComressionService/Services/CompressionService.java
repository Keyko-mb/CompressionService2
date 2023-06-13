package ru.shafikovs.ComressionService.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
public class CompressionService {
    public String compress(MultipartFile file, FileMetadata metadata) throws IOException {
        byte[] bytes = file.getBytes();
        Coder coder = new Coder();
        HuffmanCode huffmanCode = coder.compress(bytes);
//        System.out.println("huffmanTree " + huffmanCode.getHuffmanTree());
//        System.out.println("codedLine " + huffmanCode.getCode());

        return writeBinFile(huffmanCode.getHuffmanTree(), huffmanCode.getCode(), metadata);
    }

    public String writeBinFile(Node huffmanTree, String text, FileMetadata metadata) throws IOException {
        Path compressedFile = Files.createTempFile("compressedFile", ".bin");
        String filePath = String.valueOf(compressedFile.toAbsolutePath());

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
        objectOutputStream.writeObject(huffmanTree);
        objectOutputStream.close();

        System.out.println("huffmanTree: " + huffmanTree);
        System.out.println("text: " + text);

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filePath, true))) {
            dos.writeShort(metadata.getOriginalFileName().length());
            dos.writeChars(metadata.getOriginalFileName());

            System.out.println("metadata.getOriginalFileName().length(): " + metadata.getOriginalFileName().length());
            System.out.println("metadata.getOriginalFileName(): " + metadata.getOriginalFileName());

            dos.writeShort(metadata.getOriginalFormat().length());
            dos.writeChars(metadata.getOriginalFormat());

            System.out.println("metadata.getOriginalFormat().length(): " + metadata.getOriginalFormat().length());
            System.out.println("metadata.getOriginalFormat(): " + metadata.getOriginalFormat());

            for (int i = 0; i < text.length(); i += 8) {
                if (i + 8 >= text.length()) {
                    int ost = text.length() - i;
                    int intValue = Integer.parseInt((text.substring(i, i + ost)), 2);
                    dos.write(intValue);
                    System.out.println("text.substring(i, i + ost): " + text.substring(i, i + ost));
                    System.out.println("intValue: " + intValue);
                } else {
                    int intValue = Integer.parseInt((text.substring(i, i + 8)), 2);
                    System.out.println("text.substring(i, i + 8)): " + text.substring(i, i + 8));
                    System.out.println("intValue " + intValue);
                    dos.write(intValue);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
