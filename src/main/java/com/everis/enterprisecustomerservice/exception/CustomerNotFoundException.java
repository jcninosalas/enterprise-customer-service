package com.everis.enterprisecustomerservice.exception;

public class CustomerNotFoundException  extends RuntimeException{

    public CustomerNotFoundException(){
        super("Ha ingresado un id no valido");
    }
}
