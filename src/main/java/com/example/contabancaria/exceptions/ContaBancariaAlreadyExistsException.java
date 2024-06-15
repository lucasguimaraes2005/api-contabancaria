package com.example.contabancaria.exceptions;

public class ContaBancariaAlreadyExistsException extends Exception {
    public ContaBancariaAlreadyExistsException(String message) {
        super(message);
    }
}