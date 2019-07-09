package client.controller.Exceptions;

import java.io.Serializable;

public class InvalidTargetException extends Exception  implements Serializable {
    public InvalidTargetException(){
        super("Invalid Target");
    }
}
