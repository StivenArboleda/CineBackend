package com.fabrica.cine.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {


    @PostConstruct
    public void init() {
        try {
            var resource = new ClassPathResource("firebase/cine-backend-firebase.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .setStorageBucket("cine-backend.firebasestorage.app")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando Firebase", e);
        }
    }
}
