package com.ecommerce.integration.service;

import com.ecommerce.config.MinioTestConfig;
import com.ecommerce.service.MinioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Import(MinioTestConfig.class)
public class MinioServiceIntegrationTest {

    @Autowired
    private MinioService minioService;

    @Test
    @DisplayName("Deve enviar a imagem para o minio com sucesso")
    void deveEnviarAImagemParaOminioComSucesso() throws Exception {
        var arquivoEmDisco = new File("src/test/resources/imagens/img-teste.jpg");

        try (var inputStream = new FileInputStream(arquivoEmDisco)) {
            var imagemTeste = new MockMultipartFile(
                    "arquivoImagem",
                    arquivoEmDisco.getName(),
                    "image/jpeg",
                    inputStream
            );

            minioService.realizarUploadImagem(4L, imagemTeste);
        }

        try (InputStream objImgNoMinio = minioService.buscarObjetoNoMinio("imagens", "produtos/4/img-teste.jpg")) {
            var conteudoArquivoImgOriginal = Files.readAllBytes(arquivoEmDisco.toPath());
            var conteudoImgBuscadaNoMinio = objImgNoMinio.readAllBytes();

            assertArrayEquals(conteudoArquivoImgOriginal, conteudoImgBuscadaNoMinio, "O conteúdo da imagem não é igual após o upload");
        }
    }

    @Test
    @DisplayName("Url temporária deve ser montada corretamente")
    public void urlTemporariaDeveSerMontadaCorretamente() {
        var urlTemporaria = minioService.montarUrlTemporaria("produtos/4/img-teste.jpg");

        assertTrue(urlTemporaria.contains("/imagens/produtos/4/img-teste."));
        assertTrue(urlTemporaria.contains("http://"));
        assertTrue(urlTemporaria.contains("?"));
    }

}
