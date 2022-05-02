package praktikum.tugas_5;

public class King extends Tower{
    private boolean aktif;
    
    public King() {
        super(150, 30, 3);
        this.aktif = false;
    }
    
    @Override
    public boolean isAktif() {
        return aktif;
    }

    @Override
    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }
    
}
