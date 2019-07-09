package client.controller.Exceptions;

import java.io.Serializable;

public class InvalidBattleName extends Exception  implements Serializable

{
    public InvalidBattleName(){
        super("Invalid Battle Name");
    }
}
