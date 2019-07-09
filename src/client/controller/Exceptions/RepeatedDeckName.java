package client.controller.Exceptions;

import java.io.Serializable;

public class RepeatedDeckName extends Exception  implements Serializable {
    public RepeatedDeckName(){
        super("This Name is Exited");
    }
}
