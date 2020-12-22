package br.com.johnatanbrayan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(
    prefix = "file"
)
public class FileStorageProperties {
    private String uploadDir;

    public FileStorageProperties() {}
    
    public String getUploadDir() {
        if(uploadDir == null) {
            uploadDir = "./pastaParaUploads";
        }
        return uploadDir;
    }
 
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
