package com.ecommerce.service;

import com.ecommerce.exception.ImagemStorageException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String realizarUploadImagem(Long produtoId, MultipartFile arquivoImagem) {
        System.out.printf("Realizando upload da imagem %s no minio%n", arquivoImagem.getOriginalFilename());

        try (InputStream inputStream = arquivoImagem.getInputStream()) {
            return enviarParaMinio(produtoId, inputStream, arquivoImagem.getOriginalFilename());
        } catch (IOException e) {
            throw new ImagemStorageException("Erro ao obter InputStream da imagem", e);
        }
    }

    public String montarUrlTemporaria(String idObjeto) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket("imagens")
                            .object(idObjeto)
                            .method(Method.GET)
                            .expiry(60 * 60 * 24 * 7)
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idObjeto;
    }

    private String enviarParaMinio(Long produtoId, InputStream inputStream, String nomeArquivoOriginal) {
        try {
            var urlImagemBase = "produtos/" + produtoId + "/" + nomeArquivoOriginal;

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("imagens")
                            .object(urlImagemBase)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType("image/jpeg")
                            .build()
            );

            return urlImagemBase;
        } catch (Exception e) {
            throw new ImagemStorageException("Erro ao enviar imagem para o MinIO", e);
        }
    }
}
