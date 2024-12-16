
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mastermindgame;

import java.util.Random;
import java.util.Scanner;



public class MastermindGame {
    public static void main(String[] args) {
        MastermindGame game = new MastermindGame();
        game.play();
    }
    
    private final int[]secretCode = new int[4];
    private final int maxAttempts = 10;
    
    
    public MastermindGame(){
        generateSecretCode();
    }
    
    private void generateSecretCode(){
        Random random = new Random();
        for(int i = 0; i < 4; i++){
            secretCode[i] = random.nextInt(6) + 1;
        }
    }
    public void play(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Mastermind!");
        System.out.println("Guess 4-digit code => Each digit is between 1 and 6.");
        System.out.println("You have "+maxAttempts+" attempts ");
        
        for(int attempt = 1; attempt <= maxAttempts; attempt++){
            System.out.println("Attempt: " +attempt+" Enter your guess(e.g 1234)");
            String input = scanner.nextLine();
            
            if(!isValidInput(input)){
                System.out.println("Invalid input");
                attempt--;
                continue;
            }
            int[]guess = parseGuess(input);
            int exactMatches = countExactMatches(guess);
            int partialMatches = countPartialMatches(guess);
            
            System.out.println("ExactMatches(correct digit and postition)" + exactMatches);
            System.out.println("partailMatches(correct digit, wrong postition)" + partialMatches);
            
            if(exactMatches == 4){
                System.out.println("Congratulation! You have guessed the code");
                scanner.close();
            }
        }
        System.out.println("You have used all attempt. the secret code was " + codeToString()); 
        scanner.close();
    }
    private boolean isValidInput(String input){
        if(input.length()!=4) return false;
        for(char c: input.toCharArray()){
            if(c < '1' || c > '6')return false;
        }
        return true;
    }
    private int[]parseGuess(String input){
        int[]guess = new int[4];
        for(int i = 0; i < 4; i++){ 
            guess[i] = input.charAt(i)-'0'; // '1' = 1
        }
        return guess;
    }
    private int countExactMatches(int[]guess){
        int count = 0;
        for(int i = 0; i < 4; i++){
            if(secretCode[i] == guess[i])
                count++;
        }
        return count;
    }
    private int countPartialMatches(int[]guess){
        boolean[]secretUsed = new boolean[4];
        boolean[]guessUsed = new boolean[4];
        int count = 0;
        
        for(int i = 0; i < 4; i++){
            if(secretCode[i]==guess[i]){
                secretUsed[i] = true;
                guessUsed[i] = true;
            }
        }
        for(int i = 0; i < 4; i++){  
            if(guessUsed[i])continue;  
            for(int j = 0; j < 4; j++){ 
                if(!secretUsed[j] && guess[i] == secretCode[j]){  
                    count++;
                    secretUsed[j]=true;
                    break;
                }
            }
        }
        return count;
    }
    private String codeToString(){
        StringBuilder sb = new StringBuilder();
        for(int num : secretCode){
            sb.append(num);
        }
        return sb.toString();  
    }
    
}
