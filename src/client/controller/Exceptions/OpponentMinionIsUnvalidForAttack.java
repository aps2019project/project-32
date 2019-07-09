package client.controller.Exceptions;

import java.io.Serializable;

public class OpponentMinionIsUnvalidForAttack extends Exception implements Serializable {
    public OpponentMinionIsUnvalidForAttack() {
        super("Opponent Minion Is Unvalid For Attack");
    }
}
