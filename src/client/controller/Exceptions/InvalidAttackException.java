package client.controller.Exceptions;

import java.io.Serializable;

public class InvalidAttackException  extends Exception implements Serializable {
    public InvalidAttackException(){
        super("Invalid Attack");
    }

}
