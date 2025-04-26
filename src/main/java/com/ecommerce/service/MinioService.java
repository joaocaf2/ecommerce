package com.ecommerce.service;

import com.ecommerce.exception.ImagemStorageException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void gravarImagem(MultipartFile arquivoImagem) {
        System.out.println("Gravando imagem no bucket...");

        try (InputStream inputStream = arquivoImagem.getInputStream()) {
            enviarParaMinio(inputStream);
        } catch (IOException e) {
            throw new ImagemStorageException("Erro ao obter InputStream da imagem", e);
        }
    }

    private void enviarParaMinio(InputStream inputStream) {
        try {
            var idObjeto = UUID.randomUUID().toString();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("imagens")
                            .object(idObjeto)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType("image/jpeg")
                            .build()
            );
        } catch (Exception e) {
            throw new ImagemStorageException("Erro ao enviar imagem para o MinIO", e);
        }
    }
}
