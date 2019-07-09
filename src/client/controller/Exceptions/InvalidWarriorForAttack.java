package client.controller.Exceptions;

import java.io.Serializable;

public class InvalidWarriorForAttack extends Exception implements Serializable {
    public InvalidWarriorForAttack(){
        super("Invalid Warrior For Attack");
    }
}
