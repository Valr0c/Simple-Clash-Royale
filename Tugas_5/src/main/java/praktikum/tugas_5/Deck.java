package praktikum.tugas_5;

public class Deck {
    private int hp,damage,speed,range,elixir;
    private String name,simbol;
    private boolean enemy = false, player = false;

    public Deck() {
    }

    public Deck(String name, int hp, int damage, int speed, int range, int elixir, String simbol) {
        this.name = name;
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        this.elixir = elixir;
        this.simbol = simbol;
    }

    public Deck(String name, int damage, int elixir) {
        this.name = name;
        this.damage = damage;
        this.elixir = elixir;
    }

    public Deck(String name, int elixir) {
        this.name = name;
        this.elixir = elixir;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getElixir() {
        return elixir;
    }

    public void setElixir(int elixir) {
        this.elixir = elixir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }

    public boolean isPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }
    
    public void Attack(Deck [] jalur, int x, Tower t, Tower king){
        if (jalur[x].isPlayer()) {
            for (int i = 1; i <= jalur[x].getRange(); i++) {
                if (x-i >= 0) {
                    if (x-i == 0 && t.getHp() > 0) {
                        t.setHp(t.getHp() - jalur[x].getDamage());
                        if (t.getHp() <= 0) {
                            king.setAktif(true);
                        }
                        break;
                    }else if (jalur[x-i] != null) {
                        if (jalur[x-i].isEnemy()) {
                            jalur[x-i].setHp(jalur[x-i].getHp() - jalur[x].getDamage());
                            if (jalur[x-i].getHp() <= 0) {
                                jalur[x-i] = null;
                            }
                            break;
                        }
                    }
                }else if (x-i == -1) {
                    king.setHp(king.getHp() - jalur[x].getDamage());
                    break;
                }
            }
        }else if (jalur[x].isEnemy()) {
            for (int i = 1; i <= jalur[x].getRange(); i++) {
                if (x+i < 8) {
                    if (x+i == 7 && t.getHp() > 0) {
                        t.setHp(t.getHp() - jalur[x].getDamage());
                        if (t.getHp() <= 0) {
                            king.setAktif(true);
                        }
                        break;
                    }else if (jalur[x+i] != null) {
                        if (jalur[x+i].isPlayer()) {
                            jalur[x+i].setHp(jalur[x+i].getHp() - jalur[x].getDamage());
                            if (jalur[x+i].getHp() <= 0) {
                                jalur[x+i] = null;
                            }
                            break;
                        }
                    }
                }else if (x+i == 8) {
                    king.setHp(king.getHp() - jalur[x].getDamage());
                    break;
                }
            }
        }
    }
    
    public void Move(Deck [] jalur, int x, Tower t){
        if (jalur[x].isPlayer()) {
            if (jalur[x] instanceof Archer || jalur[x] instanceof Goblin || jalur[x] instanceof HogRider || jalur[x] instanceof SpearGoblin) {
                if (x-2 >= 0) {
                    if (jalur[x-2] == null && jalur[x-1] == null) {
                        if (x-2 == 0) {
                            if (t.getHp() <= 0) {
                                jalur[x-2] = jalur[x];
                                jalur[x] = null;
                            }else{
                                jalur[x-1] = jalur[x];
                                jalur[x] = null;
                            }
                        }else{
                            jalur[x-2] = jalur[x];
                            jalur[x] = null;
                        }
                    }else if (jalur[x-1] == null) {
                        jalur[x-1] = jalur[x];
                        jalur[x] = null;
                    }
                }else if (x-1 >= 0) {
                    if (jalur[x-1] == null) {
                        if (x-1 == 0) {
                            if (t.getHp() <= 0) {
                                jalur[x-1] = jalur[x];
                                jalur[x] = null;
                            }
                        }else{
                            jalur[x-1] = jalur[x];
                            jalur[x] = null;
                        }
                    }
                }
            }else if (jalur[x] instanceof Giant || jalur[x] instanceof Knight || jalur[x] instanceof Musketeer || jalur[x] instanceof Skeleton) {
                if (x-1 >= 0) {
                    if (jalur[x-1] == null) {
                        if (x-1 == 0) {
                            if (t.getHp() <= 0) {
                                jalur[x-1] = jalur[x];
                                jalur[x] = null;
                            }
                        }else{
                            jalur[x-1] = jalur[x];
                            jalur[x] = null;
                        }
                    }
                }
            }
        }else if (jalur[x].isEnemy()) {
            if (jalur[x] instanceof Archer || jalur[x] instanceof Goblin || jalur[x] instanceof HogRider || jalur[x] instanceof SpearGoblin) {
                if (x+2 < 8) {
                    if (jalur[x+2] == null && jalur[x+1] == null) {
                        if (x+2 == 7) {
                            if (t.getHp() <= 0) {
                                jalur[x+2] = jalur[x];
                                jalur[x] = null;
                            }else{
                                jalur[x+1] = jalur[x];
                                jalur[x] = null;
                            }
                        }else{
                            jalur[x+2] = jalur[x];
                            jalur[x] = null;
                        }
                    }else if (jalur[x+1] == null) {
                        jalur[x+1] = jalur[x];
                        jalur[x] = null;
                    }
                }else if (x+1 < 8) {
                    if (jalur[x+1] == null) {
                        if (x+1 == 7) {
                            if (t.getHp() <= 0) {
                                jalur[x+1] = jalur[x];
                                jalur[x] = null;
                            }
                        }else{
                            jalur[x+1] = jalur[x];
                            jalur[x] = null;
                        }
                    }
                }
            }else if (jalur[x] instanceof Giant || jalur[x] instanceof Knight || jalur[x] instanceof Musketeer || jalur[x] instanceof Skeleton) {
                if (x+1 < 8) {
                    if (jalur[x+1] == null) {
                        if (x+1 == 7) {
                            if (t.getHp() <= 0) {
                                jalur[x+1] = jalur[x];
                                jalur[x] = null;
                            }
                        }else{
                            jalur[x+1] = jalur[x];
                            jalur[x] = null;
                        }
                    }
                }
            }
        }
    }
}
