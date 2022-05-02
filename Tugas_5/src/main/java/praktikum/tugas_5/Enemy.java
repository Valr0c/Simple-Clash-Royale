package praktikum.tugas_5;

import java.util.ArrayList;

public class Enemy {
    private int elixir;
    
    public Enemy() {
        this.elixir = 2;
    }

    public int getElixir() {
        return elixir;
    }

    public void setElixir(int elixir) {
        this.elixir = elixir;
    }
    
    public void Cycle(ArrayList<Deck> deck, Deck tmp){
        Deck kirim = new Deck();
        if (tmp instanceof Archer) {
            kirim = new Archer();
        }else if (tmp instanceof Giant) {
            kirim = new Giant();
        }else if (tmp instanceof Goblin) {
            kirim = new Goblin();
        }else if (tmp instanceof HogRider) {
            kirim = new HogRider();
        }else if (tmp instanceof Knight) {
            kirim = new Knight();
        }else if (tmp instanceof Musketeer) {
            kirim = new Musketeer();
        }else if (tmp instanceof Skeleton) {
            kirim = new Skeleton();
        }else if (tmp instanceof SpearGoblin) {
            kirim = new SpearGoblin();
        }
        deck.add(kirim);
    }
}
