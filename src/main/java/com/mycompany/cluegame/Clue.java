package com.company;
import java.lang.*;
import java.security.SecureRandom;
public class Clue {
    public String[] character = {"Miss Scarlet(1)", "Rev. Green(2)", "Colonel Mustard(3)", "Professor Plum(4)", "Mrs. Peacock(5)", "Mrs. White(6)"};
    public String[] Ocharacter = {"Miss Scarlet(1)", "Rev. Green(2)", "Colonel Mustard(3)", "Professor Plum(4)", "Mrs. Peacock(5)", "Mrs. White(6)"};
    public String[] weapon = {"Candlestick(1)", "Dagger(2)", "Lead Pipe(3)", "Revolver(4)", "Rope(5)", "Wrench(6)"};
    public String[] Oweapon = {"Candlestick(1)", "Dagger(2)", "Lead Pipe(3)", "Revolver(4)", "Rope(5)", "Wrench(6)"};
    public String[] Oroom = {"Living Room(1)", "Piano Room(2)", "Conservatory(3)", "Study(4)", "Billiard Room(5)", "Bedroom(6)", "Dining Room(7)", "Library(8)", "Kitchen(9)"};
    public String[] Sroom = {"Living Room(1)", "Piano Room(2)", "Conservatory(3)", "Study(4)", "Billiard Room(5)", "Bedroom(6)", "Dining Room(7)", "Library(8)", "Kitchen(9)"};
    public boolean[] broom = new boolean[9];
    public String[] total = new String[18];//except the 3 chosen cards
    private SecureRandom secureRandom = new SecureRandom();
    public void start(int numberOfPlayers) {
        int rn = secureRandom.nextInt(6);
        String temp = character[rn];
        character[rn] = character[0];
        character[0] = temp;
        rn = secureRandom.nextInt(6);
        temp = weapon[rn];
        weapon[rn] = weapon[0];
        weapon[0] = temp;
        rn = secureRandom.nextInt(9);
        temp = Sroom[rn];
        Sroom[rn] = Sroom[0];
        Sroom[0] = temp;
        System.arraycopy(character, 1, total, 0, 5);
        System.arraycopy(weapon, 1, total, 5, 5);
        System.arraycopy(Sroom, 1, total, 10, 8);
    }
    public void YatesShuffle() {
        for (int i = 17; i > 0; i--) {
            int rn = secureRandom.nextInt(i + 1);
            String temp = total[i];
            total[i] = total[rn];
            total[rn] = temp;
        }
    }
    public int[] valid=new int[5];
    public int j;
    public void guess(Player plr) {
        boolean flag = true;
        while (flag) {
            int die=secureRandom.nextInt(6);
            j=0;
            if(die%2==0){
                for(int i=0;i<9;i+=2){
                    if(broom[i])continue;
                    if(plr.room==-1)valid[j++]=(i);
                    else if ((plr.room == 0) | (i == 0) | ((i != 2) &(i!=8) &(plr.room == 1)) |((i != 7) &(i!=1)& (plr.room == 8)) | ((plr.room > 1) & (plr.room < 8) & (i != plr.room + 1) & (i != plr.room - 1))) {
                        valid[j++]=(i);
                    }
                }
            }
            else{
                for(int i=1;i<8;i+=2) {
                    if(broom[i])continue;
                    if(plr.room==-1)valid[j++]=(i);
                    else if ((plr.room == 0) | (i == 0) | ((i != 2) &(i!=8) &(plr.room == 1)) |((i != 7) &(i!=1)& (plr.room == 8)) | ((plr.room > 1) & (plr.room < 8) & (i != plr.room + 1) & (i != plr.room - 1))) {
                        valid[j++]=(i);
                    }
                }
            }
            if(j!=0) flag=false;
        }
        flag=true;
        if(plr.nororoom>0){
            for(int k=0;k<j;k++){
                if(plr.Oroom[valid[k]]==0){
                    if(plr.room!=-1)broom[plr.room] = false;
                    plr.room = valid[k];
                    broom[valid[k]] = true;
                    flag = false;
                    break;
                }
            }
        }
        if(flag){
            for(int k=0;k<j;k++){
                if(plr.Oroom[valid[k]]==-2){
                    if(plr.room!=-1)broom[plr.room] = false;
                    plr.room = valid[k];
                    broom[valid[k]] = true;
                    flag = false;
                    break;
                }
            }
        }
        if(flag){
            int k =secureRandom.nextInt(j);
            if(plr.room!=-1)broom[plr.room] = false;
            plr.room = valid[k];
            broom[valid[k]] = true;
        }
        if (plr.norocharacter > 0) {
            int rn = secureRandom.nextInt(6);
            int i=rn;
            do{
                if (plr.Ocharacter[i] == 0) {
                    plr.character = Ocharacter[i];
                    plr.rnc=i;
                    break;
                }
                i=(i+1)%6;
            }while(i!=rn);
        }
        else {
            boolean f = true;
            int rn = secureRandom.nextInt(6);
            int i=rn;
            do{
                if (plr.Ocharacter[i] == -2) {
                    plr.character = Ocharacter[i];
                    plr.rnc=i;
                    f = false;
                    break;
                }
                i=(i+1)%6;
            }while(i!=rn);
            if (f) {
                plr.character = Ocharacter[rn];
                plr.rnc=rn;
            }
        }
        if (plr.noroweapon > 0) {
            int rn = secureRandom.nextInt(6);
            int i=rn;
            do{
                if (plr.Oweapon[i] == 0) {
                    plr.weapon = Oweapon[i];
                    plr.rnw=i;
                    break;
                }
                i=(i+1)%6;
            }while(i!=rn);
        }
        else {
            boolean f = true;
            int rn = secureRandom.nextInt(6);
            int i=rn;
            do{
                if (plr.Oweapon[i] == -2) {
                    plr.weapon = Oweapon[i];
                    plr.rnw=i;
                    f = false;
                }
                i=(i+1)%6;
            }while(i!=rn);
            if (f) {
                        plr.weapon = Oweapon[rn];
                        plr.rnw=rn;
                    }
                }
            }
    public void guessOfUser(Player plr,int n){
        if(plr.room!=-1)broom[plr.room]=false;
        plr.room = (n/10)%10-1;
        broom[(n/10)%10-1] = true;
        plr.character=Ocharacter[n/100-1];
        plr.weapon=Oweapon[n%10-1];
    }
    public boolean finalGuess(int n){
        if(n==0)return true;
        else{
            if(Ocharacter[n / 100 - 1].equals(character[0]) & Oweapon[n % 10 - 1].equals(weapon[0]) & Oroom[(n / 10) % 10 - 1].equals(Sroom[0])){
                System.out.println((char)27+"[32m"+"\n\nYou won!"+(char)27+"[0m");
            }
            else {
                System.out.println((char)27+"[31m"+"\n\nYou lost!");
                System.out.printf("\nThe 3 chosen cards:                                            %-25s%-23s%s\n\n",character[0],Sroom[0],weapon[0]);
                System.out.print((char)27+"[0m");
            }
        }
        return false;
    }
    public int search(Player plr,String card,int n,boolean b){
        switch (n) {
            case 1: {
                for (int i = 0; i < 6; i++) {
                    if (card.equals(Ocharacter[i])) {
                        if (plr.Ocharacter[i] != (b ? -2 : -1)) {
                            plr.Ocharacter[i] = (b ? -2 : -1);
                            if(plr.norocharacter>0)plr.norocharacter--;
                        }
                        return i;
                    }
                }
                break;
            }
            case 2: {
                for (int i = 0; i < 9; i++) {
                    if (card.equals(Oroom[i])) {
                        if (plr.Oroom[i] != (b ? -2 : -1)) {
                            plr.Oroom[i] = (b ? -2 : -1);
                            if(plr.nororoom>0)plr.nororoom--;
                        }
                        return i;
                    }
                }
                break;
            }
            case 3: {
                for (int i = 0; i < 6; i++) {
                    if (card.equals(Oweapon[i])) {
                        if (plr.Oweapon[i] != (b ? -2 : -1)) {
                            plr.Oweapon[i] = (b ? -2 : -1);
                            if(plr.noroweapon>0)plr.noroweapon--;
                        }
                        return i;
                    }
                }
            }
            break;
        }
        return -1;
        }
    public boolean check(Player plr){
            if(plr.norocharacter==1){
                for(int i=0;i<6;i++){
                    if(plr.Ocharacter[i]==0){
                        plr.Ocharacter[i]=1;
                        plr.sfcharacter=Ocharacter[i];
                        plr.norocharacter=0;
                        plr.nf++;
                    }
                }
            }
            if(plr.noroweapon==1){
                for(int i=0;i<6;i++){
                    if(plr.Oweapon[i]==0){
                        plr.Oweapon[i]=1;
                        plr.sfweapon=Oweapon[i];
                        plr.noroweapon=0;
                        plr.nf++;
                    }
                }
            }
            if(plr.nororoom==1){
                for(int i=0;i<9;i++){
                    if(plr.Oroom[i]==0){
                        plr.Oroom[i]=1;
                        plr.sfroom=Oroom[i];
                        plr.nororoom=0;
                        plr.nf++;
                    }
                }
            }
            if(plr.nf==3)return true;
            return false;
    }
    public void die(Player plr){
        boolean flag=true;
        while(flag){
            int die=secureRandom.nextInt(6);
            j=0;
            if(die%2==0){
                for(int i=0;i<9;i+=2){
                    if(broom[i])continue;
                    if(plr.room==-1)valid[j++]=(i);
                    else if ((plr.room == 0) | (i == 0) | ((i != 2) &(i!=8) &(plr.room == 1)) |((i != 7) &(i!=1)& (plr.room == 8)) | ((plr.room > 1) & (plr.room < 8) & (i != plr.room + 1) & (i != plr.room - 1))) {
                       valid[j++]=(i);
                    }
                }
            }
            else{
                for(int i=1;i<8;i+=2) {
                    if(broom[i])continue;
                    if(plr.room==-1)valid[j++]=(i);
                    else if ((plr.room == 0) | (i == 0) | ((i != 2) &(i!=8) &(plr.room == 1)) |((i != 7) &(i!=1)& (plr.room == 8)) | ((plr.room > 1) & (plr.room < 8) & (i != plr.room + 1) & (i != plr.room - 1))) {
                        valid[j++]=(i);
                    }
                }
            }
            if(j!=0){
                flag=false;
                System.out.print("\nDie: "+(1+die)+"              Your valid choice/choices of room:   ");
                for(int i=0;i<j;i++){
                    System.out.print("      "+(1+valid[i]));
                }
            }
        }
    }
    public boolean exceptionHandlingS(int n){
        if(   (n<111)|(n>696)|((n/10)%10==0)|(n%10==0)|((n%10)>6)){
            System.err.println("invalid input\n");
            return true;
        }
        else{
            for(int i=0;i<j;i++){
                if(((n/10)%10)==(valid[i]+1))return false;
            }
        }
        System.err.println("invalid input\n");
        return true;
    }
    public boolean exceptionHandlingF(int n){
        if(n==0)return false;
        if(   (n<111)|(n>696)|((n/10)%10==0)|(n%10==0)|((n%10)>6)){
            System.err.println("invalid input\n");
            return true;
        }
        return false;
    }
    public void OPQ(Player plr){
        if(plr.o+plr.q!=3)return;
        for(int t=0;t<plr.q;t++){
            switch (plr.qArray[t]/10){
                case 1: {
                    if(plr.Ocharacter[plr.qArray[t] % 10] != -1){
                        plr.Ocharacter[plr.qArray[t] % 10] = -1;
                        if(plr.norocharacter>0)plr.norocharacter--;
                    }
                    break;

                }
                case 2: {
                    if(plr.Oroom[plr.qArray[t] % 10] != -1) {
                        plr.Oroom[plr.qArray[t] % 10] = -1;
                        if(plr.nororoom>0)plr.nororoom--;
                    }
                    break;
                }
                case 3: {
                    if(plr.Oweapon[plr.qArray[t] % 10] != -1) {
                        plr.Oweapon[plr.qArray[t] % 10] = -1;
                        if(plr.noroweapon>0)plr.noroweapon--;
                    }
                    break;
                }
            }
        }
    }
    }