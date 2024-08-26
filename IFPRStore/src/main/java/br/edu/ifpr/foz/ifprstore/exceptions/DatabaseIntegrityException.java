package br.edu.ifpr.foz.ifprstore.exceptions;

public class DatabaseIntegrityException extends RuntimeException {

    public DatabaseIntegrityException(String msg){
        super(msg);
    }

}
