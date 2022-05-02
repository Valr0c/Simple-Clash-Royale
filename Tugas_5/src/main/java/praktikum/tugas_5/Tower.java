package praktikum.tugas_5;

public class Tower {
    private int hp, damage, range;
    boolean aktif;

    public Tower() {
        this.hp = 100;
        this.damage = 20;
        this.range = 2;
    }

    public Tower(int hp, int damage, int range) {
        this.hp = hp;
        this.damage = damage;
        this.range = range;
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

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
    
    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }
    
    public void Attack(){
        
    }
}
