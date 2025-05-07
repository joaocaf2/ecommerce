package com.ecommerce.exception;

public class ImagemStorageException extends RuntimeException {
    public ImagemStorageException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
