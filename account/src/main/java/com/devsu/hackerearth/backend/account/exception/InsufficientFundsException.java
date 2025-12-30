package com.devsu.hackerearth.backend.account.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(){
        super("Saldo no disponible");
    }
}
