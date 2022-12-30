package com.company;
import java.util.Scanner;
import java.security.SecureRandom;
import java.util.InputMismatchException;
public class Main {
    public static void main(String[] args) {
        SecureRandom secureRandom=new SecureRandom();
        Scanner input=new Scanner(System.in);
        int numberOfPlayers=3+secureRandom.nextInt(4);
        Player[] player=new Player[6];
        Clue game=new Clue();
        System.out.print("\nList of characters:\n "+(char)27+"[36m");
        for(int i=0;i<6;i++)System.out.printf("  %-18s",game.Ocharacter[i]);
        System.out.print("\n"+(char)27+"[0m"+"List of rooms:\n "+(char)27+"[36m");
        for(int i=0;i<9;i++)System.out.printf("  %-18s",game.Oroom[i]);
        System.out.print("\n"+(char)27+"[0m"+"List of weapons:\n "+(char)27+"[36m");
        for(int i=0;i<6;i++)System.out.printf("  %-18s",game.Oweapon[i]);
        System.out.printf("\n\n"+(char)27+"[0m"+"The game started with %d players. You are the last player.",numberOfPlayers);//user is the last player.
        game.start(numberOfPlayers);//character[0], Sroom[0], and weapon[0] are the 3 chosen cards.
        game.YatesShuffle();
        int rest=18;
        int f=secureRandom.nextInt(numberOfPlayers);
        for(int i=numberOfPlayers;i>0;i--){
            int j=rest/i;
            player[f]=new Player();
            player[f].numberOfCards=j;
            for(int c=0;c<j;c++){
                player[f].card[c]=game.total[c+18-rest];
                int n=game.search(player[f],player[f].card[c],1,true);
                n=game.search(player[f],player[f].card[c],2,true);
                n=game.search(player[f],player[f].card[c],3,true);
            }
            rest-=j;
            f=(f+1)%numberOfPlayers;
        }
        System.out.print("\nYour cards:     { "+(char)27+"[33m");
        for(int i=0;i<player[numberOfPlayers-1].numberOfCards-1;i++)System.out.print(player[numberOfPlayers-1].card[i]+"  -  ");
        System.out.println(player[numberOfPlayers-1].card[player[numberOfPlayers-1].numberOfCards-1]+(char)27+"[0m"+" }\n");
        boolean flag=true;
        int shift=secureRandom.nextInt(numberOfPlayers-1);
        while(flag){
            for(int i=shift;i<numberOfPlayers-1;i++) {
                for(int b=0;b<numberOfPlayers;b++){
                    player[b].q = 0;
                    player[b].o = 0;
                }
                game.guess(player[i]);
                System.out.print("Suggestion of player number " + (i + 1) + ":        ");
                System.out.printf("                         %-25s%-23s%s%n", player[i].character, game.Oroom[player[i].room], player[i].weapon);
                boolean l = true;
                for (int j = (i + 1) % (numberOfPlayers); j != i; j=(j+1)%(numberOfPlayers)) {
                    if(player[j].sfcharacter.equals(player[i].character))player[j].o++;
                    if(player[j].sfroom.equals(game.Oroom[player[i].room]))player[j].o++;
                    if(player[j].sfweapon.equals(player[i].weapon))player[j].o++;
                    for (int v = 0; v < player[j].numberOfCards; v++) {
                        if (player[j].card[v].equals(player[i].character)) {
                            player[j].o++;
                            if (l) {
                                int a = game.search(player[i], player[j].card[v], 1, false);
                                for (int h = 0; h < numberOfPlayers - 1; h++) {
                                    if (h == i) continue;
                                    if (h == j) continue;
                                    player[h].qArray[player[h].q] = 10 + a;
                                    player[h].q+=1;
                                }
                                System.out.println((char)27+"[35m"+"     Player number " + (j + 1) + " has at least one card of this suggestion."+(char)27+"[0m");
                                l = false;
                            }
                        }
                        else if (player[j].card[v].equals(game.Oroom[player[i].room])) {
                            player[j].o++;
                            if (l) {
                                int a = game.search(player[i], player[j].card[v], 2, false);
                                for (int h = 0; h < numberOfPlayers - 1; h++) {
                                    if (h == i) continue;
                                    if (h == j) continue;
                                    player[h].qArray[player[h].q] = 20 + a;
                                    player[h].q+=1;
                                }
                                System.out.println((char)27+"[35m"+"     Player number " + (j + 1) + " has at least one card of this suggestion."+(char)27+"[0m");
                                l = false;
                            }
                        }
                        else if (player[j].card[v].equals(player[i].weapon)) {
                            player[j].o++;
                            if (l) {
                                int a = game.search(player[i], player[j].card[v], 3, false);
                                for (int h = 0; h < numberOfPlayers - 1; h++) {
                                    if (h == i) continue;
                                    if (h == j) continue;
                                    player[h].qArray[player[h].q] = 30 + a;
                                    player[h].q+=1;
                                }
                                System.out.println((char)27+"[35m"+"     Player number " + (j + 1) + " has at least one card of this suggestion."+(char)27+"[0m");
                                l = false;
                            }
                        }
                    }
                }
                for (int j = 0; j < numberOfPlayers - 1; j++) {
                    if (j == i) continue;
                    game.OPQ(player[j]);
                }
                if (l) {
                    if (player[i].Oroom[player[i].room] ==0) {
                        player[i].sfroom = game.Oroom[player[i].room];
                        player[i].Oroom[player[i].room] = 1;
                        player[i].nororoom = 0;
                        player[i].nf++;
                    }
                    if( player[i].Ocharacter[player[i].rnc] ==0){
                        player[i].sfcharacter = game.Ocharacter[player[i].rnc];
                        player[i].Ocharacter[player[i].rnc] = 1;
                        player[i].norocharacter = 0;
                        player[i].nf++;
                    }
                    if(player[i].Oweapon[player[i].rnw] ==0) {
                        player[i].sfweapon = game.Oweapon[player[i].rnw];
                        player[i].Oweapon[player[i].rnw] = 1;
                        player[i].noroweapon = 0;
                        player[i].nf++;
                    }
                }
                if(game.check(player[i])){
                    System.out.print((char)27+"[31m");
                    System.out.printf("\n\nPlayer number %d won by final suggestion:                       %-25s%-23s%s\n\n",i+1,game.character[0],game.Sroom[0],game.weapon[0]);
                    System.out.print((char)27+"[0m");
                    return;
                }
            }
            game.die(player[numberOfPlayers-1]);
            boolean r=true;
            int g=0;
            while(r){
                try{
                    System.out.print("\nEnter your suggestion as a 3-digit number based on the lists: ");
                    g=input.nextInt();
                    r=game.exceptionHandlingS(g);
                }
                catch (InputMismatchException inputMismatchException){
                    System.err.printf("Exception:  %s\n\n",inputMismatchException);
                    input.nextLine();
                }
            }
            game.guessOfUser(player[numberOfPlayers-1],g);
            System.out.print("Your suggestion:                      ");
            System.out.printf("                         %-25s%-23s%s%n",player[numberOfPlayers-1].character,game.Oroom[player[numberOfPlayers-1].room],player[numberOfPlayers-1].weapon);
            int i=numberOfPlayers-1;
            for(int b=0;b<numberOfPlayers-1;b++){
                player[b].q = 0;
                player[b].o = 0;
            }
            boolean l = true;
            i=numberOfPlayers-1;
            for (int j =0; j<i; j++) {
                if(player[j].sfcharacter.equals(player[i].character))player[j].o++;
                if(player[j].sfroom.equals(game.Oroom[player[i].room]))player[j].o++;
                if(player[j].sfweapon.equals(player[i].weapon))player[j].o++;
                for (int v = 0; v < player[j].numberOfCards; v++) {
                    if (player[j].card[v].equals(player[i].character)) {
                        player[j].o++;
                        if (l) {
                            int a = game.search(player[i], player[j].card[v], 1, false);
                            for (int h = 0; h < numberOfPlayers - 1; h++) {
                                if (h == i) continue;
                                if (h == j) continue;
                                player[h].qArray[player[h].q] = 10 + a;
                                player[h].q+=1;
                            }
                            System.out.println((char)27+"[35m"+"     Player number " + (j + 1) + " has "+player[j].card[v]+"."+(char)27+"[0m");
                            l = false;
                        }
                    }
                    else if (player[j].card[v].equals(game.Oroom[player[i].room])) {
                        player[j].o++;
                        if (l) {
                            int a = game.search(player[i], player[j].card[v], 2, false);
                            for (int h = 0; h < numberOfPlayers - 1; h++) {
                                if (h == i) continue;
                                if (h == j) continue;
                                player[h].qArray[player[h].q] = 20 + a;
                                player[h].q+=1;
                            }
                            System.out.println((char)27+"[35m"+"     Player number " + (j + 1) + " has "+player[j].card[v]+"."+(char)27+"[0m");
                            l = false;
                        }
                    }
                    else if (player[j].card[v].equals(player[i].weapon)) {
                        player[j].o++;
                        if (l) {
                            int a = game.search(player[i], player[j].card[v], 3, false);
                            for (int h = 0; h < numberOfPlayers - 1; h++) {
                                if (h == i) continue;
                                if (h == j) continue;
                                player[h].qArray[player[h].q] = 30 + a;
                                player[h].q+=1;
                            }
                            System.out.println((char)27+"[35m"+"     Player number " + (j + 1) + " has "+player[j].card[v]+"."+(char)27+"[0m");
                            l = false;
                        }
                    }
                }
            }
            for (int j = 0; j < numberOfPlayers - 1; j++) {
                if (j == i) continue;
                game.OPQ(player[j]);
            }
            boolean t=true;
            while(t){
                try {
                    System.out.print("\nEnter your final suggestion as a 3-digit number based on the lists or 0 to continue: ");
                    g = input.nextInt();
                    t = game.exceptionHandlingF(g);
                }
                catch (InputMismatchException inputMismatchException){
                    System.err.printf("Exception:  %s\n\n",inputMismatchException);
                    input.nextLine();
                }
            }
            flag=game.finalGuess(g);
            if(!(flag))return;
            System.out.println();
            for(i=0;i<shift;i++) {
                for(int b=0;b<numberOfPlayers;b++){
                    player[b].q = 0;
                    player[b].o = 0;
                }
                game.guess(player[i]);
                System.out.print("Suggestion of player number " + (i + 1) + ":        ");
                System.out.printf("                         %-25s%-23s%s%n", player[i].character, game.Oroom[player[i].room], player[i].weapon);
                l = true;
                for (int j = (i + 1) % (numberOfPlayers); j != i; j=(j+1)%(numberOfPlayers)) {
                    if(player[j].sfcharacter.equals(player[i].character))player[j].o++;
                    if(player[j].sfroom.equals(game.Oroom[player[i].room]))player[j].o++;
                    if(player[j].sfweapon.equals(player[i].weapon))player[j].o++;
                    for (int v = 0; v < player[j].numberOfCards; v++) {
                        if (player[j].card[v].equals(player[i].character)) {
                            player[j].o++;
                            if (l) {
                                int a = game.search(player[i], player[j].card[v], 1, false);
                                for (int h = 0; h < numberOfPlayers - 1; h++) {
                                    if (h == i) continue;
                                    if (h == j) continue;
                                    player[h].qArray[player[h].q] = 10 + a;
                                    player[h].q+=1;
                                }
                                System.out.println((char)27+"[35m"+"     Player number " + (j + 1) + " has at least one card of this suggestion."+(char)27+"[0m");
                                l = false;
                            }
                        }
                        else if (player[j].card[v].equals(game.Oroom[player[i].room])) {
                            player[j].o++;
                            if (l) {
                                int a = game.search(player[i], player[j].card[v], 2, false);
                                for (int h = 0; h < numberOfPlayers - 1; h++) {
                                    if (h == i) continue;
                                    if (h == j) continue;
                                    player[h].qArray[player[h].q] = 20 + a;
                                    player[h].q+=1;
                                }
                                System.out.println((char)27+"[35m"+"     Player number " + (j + 1) + " has at least one card of this suggestion."+(char)27+"[0m");
                                l = false;
                            }
                        }
                        else if (player[j].card[v].equals(player[i].weapon)) {
                            player[j].o++;
                            if (l) {
                                int a = game.search(player[i], player[j].card[v], 3, false);
                                for (int h = 0; h < numberOfPlayers - 1; h++) {
                                    if (h == i) continue;
                                    if (h == j) continue;
                                    player[h].qArray[player[h].q] = 30 + a;
                                    player[h].q+=1;
                                }
                                System.out.println((char)27+"[35m"+"     Player number " + (j + 1) + " has at least one card of this suggestion."+(char)27+"[0m");
                                l = false;
                            }
                        }
                    }
                }
                for (int j = 0; j < numberOfPlayers - 1; j++) {
                    if (j == i) continue;
                    game.OPQ(player[j]);
                }
                if (l) {
                    if (player[i].Oroom[player[i].room] ==0) {
                        player[i].sfroom = game.Oroom[player[i].room];
                        player[i].Oroom[player[i].room] = 1;
                        player[i].nororoom = 0;
                        player[i].nf++;
                    }
                    if( player[i].Ocharacter[player[i].rnc] ==0){
                        player[i].sfcharacter = game.Ocharacter[player[i].rnc];
                        player[i].Ocharacter[player[i].rnc] = 1;
                        player[i].norocharacter = 0;
                        player[i].nf++;
                    }
                    if(player[i].Oweapon[player[i].rnw] ==0) {
                        player[i].sfweapon = game.Oweapon[player[i].rnw];
                        player[i].Oweapon[player[i].rnw] = 1;
                        player[i].noroweapon = 0;
                        player[i].nf++;
                    }
                }
                if(game.check(player[i])){
                    System.out.print((char)27+"[31m");
                    System.out.printf("\n\nPlayer number %d won by final suggestion:                       %-25s%-23s%s\n\n",i+1,game.character[0],game.Sroom[0],game.weapon[0]);
                    System.out.print((char)27+"[0m");
                    return;
                }
            }
        }
    }
}