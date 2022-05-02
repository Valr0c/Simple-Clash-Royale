package praktikum.tugas_5;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rd = new Random();
        Deck [] all_deck = {new Knight(), new Archer(), new Giant(), new Skeleton(), new Goblin(), new SpearGoblin(), new HogRider(), new Musketeer(), new Arrow(), new Fireball(), new Freeze()};
        int inp;
        ArrayList<Deck> p_deck = new ArrayList<>();
        ArrayList<Deck> e_deck = new ArrayList<>();
        
        // deck pemain
        for (int i = 0; i < 6; i++) {
            boolean ada = false;
            Deck tmp = all_deck[rd.nextInt(11)];
            for (int j = 0; j < p_deck.size(); j++) {
                if (p_deck.get(j) == tmp) {
                    ada = true;
                }
            }
            if (!ada) {
                p_deck.add(tmp);
            }else{
                i--;
            }
        }
        
        do {
            System.out.println("!!Clash Royal!!");
            System.out.println("1. Play");
            System.out.println("2. Edit Deck");
            System.out.println("0. Exit");
            System.out.println(">> ");
            inp = Integer.parseInt(sc.nextLine());
            
            char [][] papan = new char[10][20];
            
            if (inp == 1) {
                // deck musuh
                for (int i = 0; i < 6; i++) {
                    boolean ada = false;
                    Deck tmp = all_deck[rd.nextInt(11)];
                    for (int j = 0; j < e_deck.size(); j++) {
                        if (e_deck.get(j) == tmp) {
                            ada = true;
                        }
                    }
                    if (!ada) {
                        e_deck.add(tmp);
                    }else{
                        i--;
                    }
                }
                
                // game
                int inp2 = 0;
                String inp3 = "";
                Player p = new Player();
                Enemy e = new Enemy();
                Tower ptr = new Tower();
                Tower ptl = new Tower();
                Tower etr = new Tower();
                Tower etl = new Tower();
                Tower pk = new King();
                Tower ek = new King();
                Deck [] r = new Deck[8];
                for (int i = 0; i < 8; i++) {
                    r[i] = null;
                }
                Deck [] l = new Deck[8];
                for (int i = 0; i < 8; i++) {
                    l[i] = null;
                }
                boolean pfr, pfl, efr = false, efl = false;
                do {
                    boolean next = false, isSpell = false;
                    pfr = false;
                    pfl = false;
                    do {
                        do {
                            System.out.println("+==================+");
                            System.out.println("[ ? ][ ? ][ ? ][ ? ]");
                            System.out.println("Elixir : " + e.getElixir());
                            System.out.println("+==================+");
                            cetakArena(r,l,papan,ptr,ptl,etr,etl);
                            System.out.println("+==================+");
                            for (int i = 0; i < 4; i++) {
                                System.out.print("[" + p_deck.get(i).getName() + "]");
                            }
                            System.out.println("\nElixir : " + p.getElixir());
                            System.out.println("+==================+");
                            System.out.println(">> ");
                            if (r[6] != null && l[6] != null && ptr.getHp() > 0 && ptl.getHp() > 0) {
                                do {
                                    inp3 = sc.nextLine();
                                } while (!inp3.equals("x"));
                            }else if (r[7] != null && l[7] != null && ptr.getHp() <= 0 && ptl.getHp() <= 0) {
                                do {
                                    inp3 = sc.nextLine();
                                } while (!inp3.equals("x"));
                            }else if (r[7] != null && l[6] != null && ptr.getHp() <= 0 && ptl.getHp() > 0) {
                                do {
                                    inp3 = sc.nextLine();
                                } while (!inp3.equals("x"));
                            }else if (r[6] != null && l[7] != null && ptr.getHp() > 0 && ptl.getHp() <= 0) {
                                do {
                                    inp3 = sc.nextLine();
                                } while (!inp3.equals("x"));
                            }else{
                                inp3 = sc.nextLine();
                            }
                            if (!inp3.equals("x")) {
                                inp2 = Integer.parseInt(inp3);
                                if (inp2 == -1) {
                                    p.setElixir(10);
                                }else{
                                    if (p_deck.get(inp2-1) instanceof Spell) {
                                        isSpell = true;
                                    }
                                    do {
                                        System.out.println("[R]ight/[L]left?");
                                        inp3 = sc.nextLine();
                                        if (isSpell) {
                                            break;
                                        }else if (ptr.getHp() <= 0 && inp3.equalsIgnoreCase("R") && r[7] == null) {
                                            break;
                                        }else if (ptl.getHp() <= 0 && inp3.equalsIgnoreCase("L") && l[7] == null) {
                                            break;
                                        }else if (inp3.equalsIgnoreCase("R") && r[6] == null) {
                                            break;
                                        }else if (inp3.equalsIgnoreCase("L") && l[6] == null) {
                                            break;
                                        }
                                    } while (true);
                                }
                            }else if (inp3.equals("x")) {
                                next = true;
                                break;
                            }
                        } while (inp2 == -1);
                        if (next) {
                            break;
                        }else{
                            if (p_deck.get(inp2-1).getElixir() <= p.getElixir()) {
                                break;
                            }else{
                                System.out.println("Elixir is not enough!");
                            }
                        }
                    } while (true);
                    if (next) {
                        if (p.getElixir() < 10) {
                            p.setElixir(p.getElixir() + 1);                            
                        }
                    }else if (isSpell) {
                        if (p_deck.get(inp2-1) instanceof Arrow || p_deck.get(inp2-1) instanceof Fireball) {
                            if (inp3.equalsIgnoreCase("R")) {
                                if (etr.getHp() <= 0) {
                                    for (int i = 0; i < 8; i++) {
                                        if (r[i] != null) {
                                            if (r[i].isEnemy()) {
                                                r[i].setHp(r[i].getHp() - p_deck.get(inp2-1).getDamage());
                                                if (r[i].getHp() <= 0) {
                                                    r[i] = null;
                                                }
                                            }
                                        }
                                    }
                                    ek.setHp(ek.getHp() - p_deck.get(inp2-1).getDamage());
                                }else{
                                    for (int i = 1; i < 8; i++) {
                                        if (r[i] != null) {
                                            if (r[i].isEnemy()) {
                                                r[i].setHp(r[i].getHp() - p_deck.get(inp2-1).getDamage());
                                                if (r[i].getHp() <= 0) {
                                                    r[i] = null;
                                                }
                                            }
                                        }
                                    }
                                    etr.setHp(etr.getHp() - p_deck.get(inp2-1).getDamage());
                                    if (etr.getHp() <= 0) {
                                        ek.setAktif(true);
                                    }
                                }
                            }else if (inp3.equalsIgnoreCase("L")) {
                                if (etl.getHp() <= 0) {
                                    for (int i = 0; i < 8; i++) {
                                        if (l[i] != null) {
                                            if (l[i].isEnemy()) {
                                                l[i].setHp(l[i].getHp() - p_deck.get(inp2-1).getDamage());
                                                if (l[i].getHp() <= 0) {
                                                    l[i] = null;
                                                }
                                            }
                                        }
                                    }
                                    ek.setHp(ek.getHp() - p_deck.get(inp2-1).getDamage());
                                }else{
                                    for (int i = 1; i < 8; i++) {
                                        if (l[i] != null) {
                                            if (l[i].isEnemy()) {
                                                l[i].setHp(l[i].getHp() - p_deck.get(inp2-1).getDamage());
                                                if (l[i].getHp() <= 0) {
                                                    l[i] = null;
                                                }
                                            }
                                        }
                                    }
                                    etl.setHp(etl.getHp() - p_deck.get(inp2-1).getDamage());
                                    if (etl.getHp() <= 0) {
                                        ek.setAktif(true);
                                    }
                                }
                            }
                        }else if (p_deck.get(inp2-1) instanceof Freeze) {
                            if (inp3.equalsIgnoreCase("R")) {
                                pfr = true;
                            }else if (inp3.equalsIgnoreCase("L")) {
                                pfl = true;
                            }
                        }
                        p.setElixir(p.getElixir() - p_deck.get(inp2-1).getElixir());
                        p_deck.add(p_deck.remove(inp2-1));
                    }else{
                        if (inp2 > 0 && inp2 <= 4) {
                            if (inp3.equalsIgnoreCase("R")) {
                                if (ptr.getHp() <= 0) {
                                    r[7] = p_deck.get(inp2-1);
                                    r[7].setPlayer(true);
                                }else{
                                    r[6] = p_deck.get(inp2-1);
                                    r[6].setPlayer(true);
                                }
                            }else if (inp3.equalsIgnoreCase("L")) {
                                if (ptl.getHp() <= 0) {
                                    l[7] = p_deck.get(inp2-1);
                                    l[7].setPlayer(true);
                                }else{
                                    l[6] = p_deck.get(inp2-1);
                                    l[6].setPlayer(true);
                                }
                            }
                            p.setElixir(p.getElixir() - p_deck.get(inp2-1).getElixir());
                            p.Cycle(p_deck, p_deck.remove(inp2-1));
                        }
                    }
                    
                    // player troop move
                    if (!efr) {
                        for (int i = 0; i < 8; i++) {
                            if (r[i] != null) {
                                if (r[i].isPlayer()) {
                                    r[i].Attack(r,i,etr,ek);
                                    r[i].Move(r,i,etr);
                                }
                            }
                        }
                    }
                    if (!efl) {
                        for (int i = 0; i < 8; i++) {
                            if (l[i] != null) {
                                if (l[i].isPlayer()) {
                                    l[i].Attack(l,i,etl,ek);
                                    l[i].Move(l,i,etl);
                                }
                            }
                        }
                    }
                    
                    // enemy's tower attack
                    if (etr.getHp() > 0 && !pfr) {
                        for (int i = 1; i <= etr.getRange(); i++) {
                            if (r[i] != null) {
                                if (r[i].isPlayer()) {
                                    r[i].setHp(r[i].getHp() - etr.getDamage());
                                    if (r[i].getHp() <= 0) {
                                        r[i] = null;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (etl.getHp() > 0 && !pfl) {
                        for (int i = 1; i <= etl.getRange(); i++) {
                            if (l[i] != null) {
                                if (l[i].isPlayer()) {
                                    l[i].setHp(l[i].getHp() - etl.getDamage());
                                    if (l[i].getHp() <= 0) {
                                        l[i] = null;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (ek.isAktif() && !pfr && !pfl) {
                        for (int i = 0; i < ek.getRange(); i++) {
                            if (etr.getHp() <= 0 && etl.getHp() <= 0) {
                                if (r[i] != null && l[i] != null) {
                                    boolean isAtk = false;
                                    switch(rd.nextInt(2)){
                                        case 0:
                                            if (r[i].isPlayer()) {
                                                r[i].setHp(r[i].getHp() - ek.getDamage());
                                                if (r[i].getHp() <= 0) {
                                                    r[i] = null;
                                                }
                                                isAtk = true;
                                            }
                                            break;
                                        case 1:
                                            if (l[i].isPlayer()) {
                                                l[i].setHp(l[i].getHp() - ek.getDamage());
                                                if (l[i].getHp() <= 0) {
                                                    l[i] = null;
                                                }
                                                isAtk = true;
                                            }
                                            break;
                                    }
                                    if (isAtk) {
                                        break;
                                    }
                                }else if (r[i] != null) {
                                    if (r[i].isPlayer()) {
                                        r[i].setHp(r[i].getHp() - ek.getDamage());
                                        if (r[i].getHp() <= 0) {
                                            r[i] = null;
                                        }
                                        break;
                                    }
                                }else if (l[i] != null) {
                                    if (l[i].isPlayer()) {
                                        l[i].setHp(l[i].getHp() - ek.getDamage());
                                        if (l[i].getHp() <= 0) {
                                            l[i] = null;
                                        }
                                        break;
                                    }
                                }
                            }else if (etr.getHp() <= 0) {
                                if (r[i] != null) {
                                    if (r[i].isPlayer()) {
                                        r[i].setHp(r[i].getHp() - ek.getDamage());
                                        if (r[i].getHp() <= 0) {
                                            r[i] = null;
                                        }
                                        break;
                                    }
                                }else if (l[i] != null) {
                                    if (l[i].isPlayer()) {
                                        l[i].setHp(l[i].getHp() - ek.getDamage());
                                        if (l[i].getHp() <= 0) {
                                            l[i] = null;
                                        }
                                        break;
                                    }
                                }
                            }else if (etl.getHp() <= 0) {
                                if (l[i] != null) {
                                    if (l[i].isPlayer()) {
                                        l[i].setHp(l[i].getHp() - ek.getDamage());
                                        if (l[i].getHp() <= 0) {
                                            l[i] = null;
                                        }
                                        break;
                                    }
                                }else if (r[i] != null) {
                                    if (r[i].isPlayer()) {
                                        r[i].setHp(r[i].getHp() - ek.getDamage());
                                        if (r[i].getHp() <= 0) {
                                            r[i] = null;
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    
                    // musuh jalan
                    int rdm;
                    next = false;
                    isSpell = false;
                    efr = false;
                    efl = false;
                    do {
                        if (e.getElixir() == 0) {
                            rdm = 4;
                        }else if (l[1] != null && r[1] != null && etr.getHp() > 0 && etl.getHp() > 0) {
                            rdm = 4;
                        }else if (l[0] != null && r[0] != null && etr.getHp() <= 0 && etl.getHp() <= 0) {
                            rdm = 4;
                        }else if (l[0] != null && r[1] != null && etr.getHp() > 0 && etl.getHp() <= 0) {
                            rdm = 4;
                        }else if (l[1] != null && r[0] != null && etr.getHp() <= 0 && etl.getHp() > 0) {
                            rdm = 4;
                        }else{
                            rdm = rd.nextInt(5);
                        }
                        if (rdm == 4) {
                            next = true;
                            break;
                        }else if (e_deck.get(rdm) instanceof Spell) {
                            if (e_deck.get(rdm).getElixir() <= e.getElixir()) {
                                isSpell = true;
                                break;
                            }
                        }else if (e_deck.get(rdm).getElixir() <= e.getElixir()) {
                            break;
                        }
                    } while (true);
                    if (next) {
                        if (e.getElixir() < 10) {
                            e.setElixir(e.getElixir() + 1);
                        }
                    }else if (isSpell) {
                        int rdmj = rd.nextInt(2);
                        if (e_deck.get(rdm) instanceof Arrow || e_deck.get(rdm) instanceof Fireball) {
                            if (rdmj == 0) {
                                if (ptr.getHp() <= 0) {
                                    for (int i = 0; i < 8; i++) {
                                        if (r[i] != null) {
                                            if (r[i].isPlayer()) {
                                                r[i].setHp(r[i].getHp() - e_deck.get(rdm).getDamage());
                                            }
                                        }
                                    }
                                    pk.setHp(pk.getHp() - e_deck.get(rdm).getDamage());
                                }else{
                                    for (int i = 0; i < 7; i++) {
                                        if (r[i] != null) {
                                            if (r[i].isPlayer()) {
                                                r[i].setHp(r[i].getHp() - e_deck.get(rdm).getDamage());
                                            }
                                        }
                                    }
                                    ptr.setHp(ptr.getHp() - e_deck.get(rdm).getDamage());
                                    if (ptr.getHp() <= 0) {
                                        pk.setAktif(true);
                                    }
                                }
                            }else{
                                if (ptl.getHp() <= 0) {
                                    for (int i = 0; i < 8; i++) {
                                        if (l[i] != null) {
                                            if (l[i].isPlayer()) {
                                                l[i].setHp(l[i].getHp() - e_deck.get(rdm).getDamage());
                                            }
                                        }
                                    }
                                    pk.setHp(pk.getHp() - e_deck.get(rdm).getDamage());
                                }else{
                                    for (int i = 0; i < 7; i++) {
                                        if (l[i] != null) {
                                            if (l[i].isPlayer()) {
                                                l[i].setHp(l[i].getHp() - e_deck.get(rdm).getDamage());
                                            }
                                        }
                                    }
                                    ptl.setHp(ptl.getHp() - e_deck.get(rdm).getDamage());
                                    if (ptl.getHp() <= 0) {
                                        pk.setAktif(true);
                                    }
                                }
                            }
                        }else if (e_deck.get(rdm) instanceof Freeze) {
                            if (rdmj == 0) {
                                efr = true;
                            }else{
                                efl = true;
                            }
                        }
                        e.setElixir(e.getElixir() - e_deck.get(rdm).getElixir());
                        e_deck.add(e_deck.remove(rdm));
                    }else{
                        if (r[1] != null && etr.getHp() > 0) {
                            if (etl.getHp() > 0) {
                                l[1] = e_deck.get(rdm);
                                l[1].setEnemy(true);
                            }else{
                                l[0] = e_deck.get(rdm);
                                l[0].setEnemy(true);
                            }
                        }else if (l[1] != null && etl.getHp() > 0) {
                            if (etr.getHp() > 0) {
                                r[1] = e_deck.get(rdm);
                                r[1].setEnemy(true);
                            }else{
                                r[0] = e_deck.get(rdm);
                                r[0].setEnemy(true);
                            }
                        }else if (r[0] != null && etr.getHp() <= 0) {
                            if (etl.getHp() > 0) {
                                l[1] = e_deck.get(rdm);
                                l[1].setEnemy(true);
                            }else{
                                l[0] = e_deck.get(rdm);
                                l[0].setEnemy(true);
                            }
                        }else if (l[0] != null && etl.getHp() <= 0) {
                            if (etr.getHp() > 0) {
                                r[1] = e_deck.get(rdm);
                                r[1].setEnemy(true);
                            }else{
                                r[0] = e_deck.get(rdm);
                                r[0].setEnemy(true);
                            }
                        }else{
                            switch (rd.nextInt(2)) {
                                case 0:
                                    if (etr.getHp() > 0) {
                                        r[1] = e_deck.get(rdm);
                                        r[1].setEnemy(true);
                                    }else{
                                        r[0] = e_deck.get(rdm);
                                        r[0].setEnemy(true);
                                    }
                                    break;
                                case 1:
                                    if (etl.getHp() > 0) {
                                        l[1] = e_deck.get(rdm);
                                        l[1].setEnemy(true);
                                    }else{
                                        l[0] = e_deck.get(rdm);
                                        l[0].setEnemy(true);
                                    }
                                    break;
                            }
                        }
                        e.setElixir(e.getElixir() - e_deck.get(rdm).getElixir());
                        e.Cycle(e_deck, e_deck.remove(rdm));
                    }
                    
                    // enemy troop move
                    if (!pfr) {
                        for (int i = 7; i >= 0; i--) {
                            if (r[i] != null) {
                                if (r[i].isEnemy()) {
                                    r[i].Attack(r,i,ptr,pk);
                                    r[i].Move(r,i,ptr);
                                }
                            }
                        }
                    }
                    if (!pfl) {
                        for (int i = 7; i >= 0; i--) {
                            if (l[i] != null) {
                                if (l[i].isEnemy()) {
                                    l[i].Attack(l,i,ptl,pk);
                                    l[i].Move(l,i,ptl);
                                }
                            }
                        }
                    }
                    
                    // player's tower attack
                    if (ptr.getHp() > 0 && !efr) {
                        for (int i = 1; i <= ptr.getRange(); i++) {
                            if (r[7-i] != null) {
                                if (r[7-i].isEnemy()) {
                                    r[7-i].setHp(r[7-i].getHp() - ptr.getDamage());
                                    if (r[7-i].getHp() <= 0) {
                                        r[7-i] = null;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (ptl.getHp() > 0 && !efl) {
                        for (int i = 1; i <= ptl.getRange(); i++) {
                            if (l[7-i] != null) {
                                if (l[7-i].isEnemy()) {
                                    l[7-i].setHp(l[7-i].getHp() - ptl.getDamage());
                                    if (l[7-i].getHp() <= 0) {
                                        l[7-i] = null;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (pk.isAktif() && !efr && !efl) {
                        for (int i = 0; i < pk.getRange(); i++) {
                            if (ptr.getHp() <= 0 && ptl.getHp() <= 0) {
                                if (r[7-i] != null && l[7-i] != null) {
                                    boolean isAtk = false;
                                    switch(rd.nextInt(2)){
                                        case 0:
                                            if (r[7-i].isEnemy()) {
                                                r[7-i].setHp(r[7-i].getHp() - pk.getDamage());
                                                if (r[7-i].getHp() <= 0) {
                                                    r[7-i] = null;
                                                }
                                                isAtk = true;
                                            }
                                            break;
                                        case 1:
                                            if (l[7-i].isEnemy()) {
                                                l[7-i].setHp(l[7-i].getHp() - pk.getDamage());
                                                if (l[7-i].getHp() <= 0) {
                                                    l[7-i] = null;
                                                }
                                                isAtk = true;
                                            }
                                            break;
                                    }
                                    if (isAtk) {
                                        break;
                                    }
                                }else if (r[7-i] != null) {
                                    if (r[7-i].isEnemy()) {
                                        r[7-i].setHp(r[7-i].getHp() - pk.getDamage());
                                        if (r[7-i].getHp() <= 0) {
                                            r[7-i] = null;
                                        }
                                        break;
                                    }
                                }else if (l[7-i] != null) {
                                    if (l[7-i].isEnemy()) {
                                        l[7-i].setHp(l[7-i].getHp() - pk.getDamage());
                                        if (l[7-i].getHp() <= 0) {
                                            l[7-i] = null;
                                        }
                                        break;
                                    }
                                }
                            }else if (ptr.getHp() <= 0) {
                                if (r[7-i] != null) {
                                    if (r[7-i].isEnemy()) {
                                        r[7-i].setHp(r[7-i].getHp() - pk.getDamage());
                                        if (r[7-i].getHp() <= 0) {
                                            r[7-i] = null;
                                        }
                                        break;
                                    }
                                }else if (l[7-i] != null) {
                                    if (l[7-i].isEnemy()) {
                                        l[7-i].setHp(l[7-i].getHp() - pk.getDamage());
                                        if (l[7-i].getHp() <= 0) {
                                            l[7-i] = null;
                                        }
                                        break;
                                    }
                                }
                            }else if (ptl.getHp() <= 0) {
                                if (l[7-i] != null) {
                                    if (l[7-i].isEnemy()) {
                                        l[7-i].setHp(l[7-i].getHp() - pk.getDamage());
                                        if (l[7-i].getHp() <= 0) {
                                            l[7-i] = null;
                                        }
                                        break;
                                    }
                                }else if (r[7-i] != null) {
                                    if (r[7-i].isEnemy()) {
                                        r[7-i].setHp(r[7-i].getHp() - pk.getDamage());
                                        if (r[7-i].getHp() <= 0) {
                                            r[7-i] = null;
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    
                    if (ek.getHp() <= 0) {
                        System.out.println("You Win!");
                        break;
                    }else if (pk.getHp() <= 0) {
                        System.out.println("You Lose!");
                        break;
                    }
                    
                } while (true);
                all_deck = Perbaharui();
            }else if (inp == 2) {
                int inp2;
                do {
                    System.out.println("===Deck===");
                    for (int i = 0; i < p_deck.size(); i++) {
                        System.out.println(i+1 + ". " + p_deck.get(i).getName());
                    }
                    System.out.println("0. Exit");
                    System.out.println(">> ");
                    inp2 = Integer.parseInt(sc.nextLine());
                    
                    if (inp2 > 0 && inp2 <= 6) {
                        int c = 0, inp3;
                        int [] simpan = new int[5];
                        for (int i = 0; i < 11; i++) {
                            boolean cek = false;
                            for (int j = 0; j < p_deck.size(); j++) {
                                if (p_deck.get(j) instanceof Archer && all_deck[i] instanceof Archer) {
                                    cek = true;
                                }else if (p_deck.get(j) instanceof Giant && all_deck[i] instanceof Giant) {
                                    cek = true;
                                }else if (p_deck.get(j) instanceof Goblin && all_deck[i] instanceof Goblin) {
                                    cek = true;
                                }else if (p_deck.get(j) instanceof HogRider && all_deck[i] instanceof HogRider) {
                                    cek = true;
                                }else if (p_deck.get(j) instanceof Knight && all_deck[i] instanceof Knight) {
                                    cek = true;
                                }else if (p_deck.get(j) instanceof Musketeer && all_deck[i] instanceof Musketeer) {
                                    cek = true;
                                }else if (p_deck.get(j) instanceof Skeleton && all_deck[i] instanceof Skeleton) {
                                    cek = true;
                                }else if (p_deck.get(j) instanceof SpearGoblin && all_deck[i] instanceof SpearGoblin) {
                                    cek = true;
                                }else if (p_deck.get(j) instanceof Arrow && all_deck[i] instanceof Arrow) {
                                    cek = true;
                                }else if (p_deck.get(j) instanceof Fireball && all_deck[i] instanceof Fireball) {
                                    cek = true;
                                }else if (p_deck.get(j) instanceof Freeze && all_deck[i] instanceof Freeze) {
                                    cek = true;
                                }
                            }
                            
                            if (!cek) {
                                simpan[c] = i;
                                System.out.println(++c + ". " + all_deck[i].getName());
                            }
                        }
                        System.out.println("0. Back");
                        System.out.println("Change With : ");
                        System.out.println(">> ");
                        inp3 = Integer.parseInt(sc.nextLine());
                        
                        if (inp3 > 0 && inp3 <= 5) {
                            p_deck.set(inp2-1, all_deck[simpan[inp3-1]]);
                        }
                    }
                } while (inp2 != 0);
            }
        } while (inp != 0);
    }
    
    static void cetakArena(Deck [] r, Deck [] l, char [][] papan, Tower ptr, Tower ptl, Tower etr, Tower etl){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (j == 0 || j == 19) {
                    papan[i][j] = '|';
                }else if ((i >= 2 && i <= 7) && (j == 3 || j == 6 || j == 13 || j == 16)) {
                    papan[i][j] = '|';
                }else if ((i == 4 || i == 5) && (j != 4 && j != 5 && j != 14 && j != 15)) {
                    papan[i][j] = '_';
                }else if ((i == 0 || i == 9) && (j == 8)) {
                    papan[i][j] = '(';
                }else if ((i == 0 || i == 9) && (j == 11)) {
                    papan[i][j] = ')';
                }else if (i == 1 && j == 3 && etl.getHp() > 0) {
                    papan[i][j] = '(';
                }else if (i == 1 && j == 6 && etl.getHp() > 0) {
                    papan[i][j] = ')';
                }else if ((i == 1 && (j == 3 || j == 6)) && etl.getHp() <= 0) {
                    papan[i][j] = '|';
                }else if (i == 1 && j == 13 && etr.getHp() > 0) {
                    papan[i][j] = '(';
                }else if (i == 1 && j == 16 && etr.getHp() > 0) {
                    papan[i][j] = ')';
                }else if ((i == 1 && (j == 13 || j == 16)) && etr.getHp() <= 0) {
                    papan[i][j] = '|';
                }else if (i == 8 && j == 3 && ptl.getHp() > 0) {
                    papan[i][j] = '(';
                }else if (i == 8 && j == 6 && ptl.getHp() > 0) {
                    papan[i][j] = ')';
                }else if ((i == 8 && (j == 3 || j == 6)) && ptl.getHp() <= 0) {
                    papan[i][j] = '|';
                }else if (i == 8 && j == 13 && ptr.getHp() > 0) {
                    papan[i][j] = '(';
                }else if (i == 8 && j == 16 && ptr.getHp() > 0) {
                    papan[i][j] = ')';
                }else if ((i == 8 && (j == 13 && j == 16)) && ptr.getHp() <= 0) {
                    papan[i][j] = '|';
                }else if ((i == 0 || i == 9) && j == 10) {
                    papan[i][j] = 'K';
                }else if (i == 0 && j == 9) {
                    papan[i][j] = 'E';
                }else if (i == 9 && j == 9) {
                    papan[i][j] = 'P';
                }else{
                    papan[i][j] = ' ';
                }
            }
        }
        
        for (int i = 0; i < 8; i++) {
            if (ptr.getHp() > 0 && i == 7) {
                papan[8][14] = 'P';
                papan[8][15] = 'T';
            }else if (etr.getHp() > 0 && i == 0) {
                papan[1][14] = 'E';
                papan[1][15] = 'T';
            }else{
                if (r[i] == null) {
                    papan[1+i][14] = ' ';
                    papan[1+i][15] = ' ';
                }else{
                    papan[1+i][14] = r[i].getSimbol().charAt(0);
                    papan[1+i][15] = r[i].getSimbol().charAt(1);
                }
            }
            if (ptl.getHp() > 0 && i == 7) {
                papan[8][4] = 'P';
                papan[8][5] = 'T';
            }else if (etl.getHp() > 0 && i == 0) {
                papan[1][4] = 'E';
                papan[1][5] = 'T';
            }else{
                if (l[i] == null) {
                    papan[1+i][4] = ' ';
                    papan[1+i][5] = ' ';
                }else{
                    papan[1+i][4] = l[i].getSimbol().charAt(0);
                    papan[1+i][5] = l[i].getSimbol().charAt(1);
                }
            }
        }
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(papan[i][j]);
            }
            System.out.println();
        }
    }
    
    static Deck [] Perbaharui(){
        Deck [] new_deck = {new Knight(), new Archer(), new Giant(), new Skeleton(), new Goblin(), new SpearGoblin(), new HogRider(), new Musketeer(), new Arrow(), new Fireball(), new Freeze()};
        return new_deck;
    }
}
