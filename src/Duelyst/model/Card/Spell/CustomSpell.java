package Duelyst.model.Card.Spell;

import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Buff.Buff;
import Duelyst.model.Cell;
import Duelyst.model.ErrorType;

import java.util.ArrayList;

public class CustomSpell extends Spell {
    ArrayList<Buff> buffs = new ArrayList<>();

    public CustomSpell(String name, int costToUse, int costOfBuy, ArrayList<Buff> buffs) {
        super(name, costToUse, costOfBuy);
        this.buffs = buffs;
    }

    @Override
    public ErrorType castSpell(Battle battle, Cell cell, Player player) {
        for (Buff buff : this.buffs) {
            buff.setCasting(cell, cell.getHero(), cell.getMinion());
            buff.castBuff();
        }
        return ErrorType.SUCCESSFUL_INSERT;
    }

    @Override
    public String getDesc() {
        return null;
    }

    @Override
    public String showDetails() {
        return null;
    }
}
