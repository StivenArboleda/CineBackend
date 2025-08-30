package com.fabrica.cine.backend.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class FirebaseStorageService {

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

            Bucket bucket = StorageClient.getInstance().bucket();

            Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

            String imageUrl = String.format(
                    "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                    bucket.getName(),
                    URLEncoder.encode(fileName, StandardCharsets.UTF_8)
            );

            return imageUrl;

        } catch (IOException e) {
            throw new RuntimeException("Error al subir el archivo a Firebase Storage", e);
        }
    }
}
