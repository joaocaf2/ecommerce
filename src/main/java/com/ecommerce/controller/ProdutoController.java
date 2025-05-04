package com.ecommerce.controller;

import com.ecommerce.model.Produto;
import com.ecommerce.repository.ProdutoRepository;
import com.ecommerce.service.MinioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MinioService minioService;

    @RequestMapping(value = "/formulario")
    public String formulario(Produto produto, Model model) {
        model.addAttribute("produto", produto);

        return "produtos/formulario-produto";
    }

    @PostMapping(value = "/cadastrar")
    public String cadastrar(@Valid @ModelAttribute Produto produto, BindingResult bindingResult, @RequestParam("arquivoImagem") MultipartFile arquivoImagem) {
        if (arquivoImagem == null || arquivoImagem.isEmpty()) {
            bindingResult.rejectValue("urlImagem", "Pattern", "A imagem é obrigatória.");

            return "produtos/formulario-produto";
        }

        if (bindingResult.hasErrors()) {
            return "produtos/formulario-produto";
        }

        produtoRepository.salvar(produto);

        var urlImagemBase = minioService.realizarUploadImagem(produto.getId(), arquivoImagem);
        produto.setUrlImagem(urlImagemBase);

        produtoRepository.atualizar(produto);
        return "redirect:/";
    }

}
