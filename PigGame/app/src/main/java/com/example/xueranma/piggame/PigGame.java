package com.example.xueranma.piggame;

/**
 * Created by xueranma on 7/5/17.
 */

import java.util.HashMap;
import java.util.Random;

public class PigGame {
    //The socre needed to win
    private int win_Score = 100;

    //Store scores for two players in the enum
    private HashMap<Player,Integer> scores;

    //A random object to produce dice roll
    private Random rand;


    //The point for current turn
    private int currentPoint;

    //Reference to current player
    private Player currentPlayer = Player.First;

    //set the max die number
    private int maxDieSides = 6;

   //set the dead side
    private int deadSide = 1;


    //constructer
    public PigGame(){
        scores = new HashMap<Player,Integer>();
        scores.put(Player.First,0);
        scores.put(Player.Second,0);

        rand = new Random();

    }

    //Next turn's actions and return a Winner (First, Second, Tie, No)
    //used in end turn button
    public Winner nextTurn(){
        //add the current Point to the current player
        scores.put(currentPlayer, scores.get(currentPlayer)+currentPoint);
        //then clear the current point to 0
        currentPoint = 0;

        int firstScore = scores.get(Player.First);
        int secondScore = scores.get(Player.Second);
        //judge if there is a winner

        if (currentPlayer==Player.Second){
            if (firstScore>=win_Score && secondScore>=win_Score){
                if (firstScore>secondScore){
                    return Winner.First;
                }else if(secondScore>firstScore){
                    return Winner.Second;
                }else if(secondScore==firstScore){
                    return Winner.Tie;
                }
            }else if(firstScore>=win_Score){
                return Winner.First;
            }else if(secondScore>=win_Score){
                return  Winner.Second;
            }
        }

        switch (currentPlayer){
            case First:
                currentPlayer = Player.Second;
                break;
            case Second:
                currentPlayer = Player.First;
                break;
        }


        return Winner.No;
    }


    //Roll the dice actions and return a random number representing the dice number
    //used in roll die button
    public int randomDie(){
        int roll = rand.nextInt(maxDieSides) +1;
        //when roll a one, clear the currentPoint to 0 and
        // implement the nextTurn
        if (roll==deadSide){
            currentPoint=0;
            return deadSide;
        }
        //if roll a non-one number, add it to currentPoint
        currentPoint += roll;
        return roll;
    }

    //Starts a new game and clear alll the data
    //used in newGame button
    public void newGame(){
        scores.put(Player.First,0);
        scores.put(Player.Second,0);
        currentPoint = 0;
        currentPlayer = Player.First;
    }

    //get the current player to display in main activity
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    //get the current point for this turn to display in main activity
    public int getCurrentPoint(){
        return currentPoint;
    }

    //get the player's score to display in main activity
    public int getScore(Player player){
        if(!scores.containsKey(player)){
            return 0;
        }
        return scores.get(player);
    }

    //set the score for the player
    public void setScore(Player player,int score){
        scores.put(player,score);
    }

    //set current point
    public void setCurrentPoint(int point){
        currentPoint = point;
    }

    //set the current player
    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

    //set the win_Score
    public void setWin_Score(int score){win_Score=score;}

    //set the max_die_numbber
    public void setMaxDieSides(int number){maxDieSides=number;}

    //set the deadSide
    public void setDeadSide(int side){
        deadSide = side;
    }

    //get the deadSide
    public int getDeadSide(){
        return deadSide;
    }



}
