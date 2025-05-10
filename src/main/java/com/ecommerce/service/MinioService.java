package com.ecommerce.service;

import com.ecommerce.exception.ImagemStorageException;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @PostConstruct
    public void criarBucketsAindaNaoExistentes() {
        try {
            if (!existeBucketCriado("imagens")) {
                this.minioClient.makeBucket(MakeBucketArgs.builder().bucket("imagens").build());
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao criar o bucket");

            e.printStackTrace();
        }
    }

    public void realizarUploadImagem(Long produtoId, MultipartFile arquivoImagem) {
        System.out.printf("Realizando upload da imagem %s no minio", arquivoImagem.getOriginalFilename());

        try (var inputStream = arquivoImagem.getInputStream()) {
            var urlImagemBase = "produtos/" + produtoId + "/" + arquivoImagem.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("imagens")
                            .object(urlImagemBase)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType("image/jpeg")
                            .build()
            );
        } catch (Exception e) {
            throw new ImagemStorageException("Erro ao realizar upload da imagem para o minio", e);
        }
    }

    private boolean existeBucketCriado(String nomeBucket) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(nomeBucket).build());
    }

    public String montarUrlTemporaria(String idObjeto) {
        try {
            var seteDiasEmSegundos = 60 * 60 * 24 * 7;

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket("imagens")
                            .object(idObjeto)
                            .method(Method.GET)
                            .expiry(seteDiasEmSegundos)
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idObjeto;
    }

    public InputStream buscarObjetoNoMinio(String nomeBucket, String objetoId) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(nomeBucket)
                            .object(objetoId)
                            .build()
            );
        } catch (Exception e) {
            throw new ImagemStorageException("Erro ao buscar objeto no MinIO", e);
        }
    }

}
