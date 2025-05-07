package com.ecommerce.service;

import com.ecommerce.exception.ImagemStorageException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
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
            throw new ImagemStorageException("Erro ao obter InputStream da imagem", e);
        }
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

}
