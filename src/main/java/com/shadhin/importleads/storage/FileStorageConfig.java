package com.shadhin.importleads.storage;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig {

    @Bean
    public StorageProperty storageConfig() {
        return new StorageProperty("/import-leads");
    }
}
