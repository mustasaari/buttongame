package fi.tampere.mustasaari.mikko.buttongame;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@RestController
public class GameController {

    final int startingCredits = 20;

    @Autowired
    UserRepository gameRepository;

    @Autowired
    NumberRepository numberRepository;

    /*
    * Create new user.
    * Users are identified based on unique identifier (name) generated here.
    * Frontend holds cookie with unique identifier (name), and backend return UserModels (user data) based on that identifier.
    *
    * This method creates new identifier (name) for user and stores UserModel to database with that name.
    * Frontend receives UserModel and saves unique identifier to cookies.
    * UserModel contains (name, score, message and how many clicks to win).
    * This method is called if no user identifier is found from cookies when frontend browser opens.
    */

    @GetMapping("/create-user")
    public UserModel returnNewUser() {
        UUID uuid = UUID.randomUUID();
        System.out.println(" ID : " +uuid.toString());
        UserModel user = new UserModel(uuid.toString(), startingCredits);
        gameRepository.save(user);
        System.out.println("User Created : " +uuid.toString());
        user.setClicksToPrize(0);
        return user;
    }

    /*
    * When user clicks button on frontend.
    * Receives user unique identifier (name) as parameter.
    * Gets userdata from database and then check user score (credits).
    * If credits is 0, this is call to reset players score.
    * If credits is more, player plays the game and current "Lucky Number" is fetched from database.
    * Then winnings are calculated.
    */

    @RequestMapping(value = "/roll/{id}", method= RequestMethod.POST)
        public UserModel roll(@PathVariable String id) {

        UserModel user = gameRepository.findByName(id);

        if (user.getCredits() == 0) {                               //reset call
            user.setCredits(startingCredits);
            user.setMessage("Credits Reset");
            user.setClicksToPrize(0);
        } else {                                                    //play game
            int numberFromDatabase = getLuckyNumber(true);
            user.setCredits(user.getCredits() -1);                  //subtract credit
            if (numberFromDatabase % 500 == 0) {                    //Calculate if win and add credits & set message
                user.addCredits(250);
                user.setMessage("You Won 250 Credits!");
            } else if (numberFromDatabase % 100 == 0) {
                user.addCredits(40);
                user.setMessage("You Won 40 Credits!");
            } else if (numberFromDatabase % 10 == 0) {
                user.addCredits(5);
                user.setMessage("You Won 5 Credits!");
            } else {
                user.setMessage("You Did Not Win");
            }
            user.setClicksToPrize(10 - numberFromDatabase%10);      //Remaining clicks to next prize.
        }
        gameRepository.save(user);                                  //Save data
        return user;

    }

    /*
    * Return UserModel from database based on UserId.
    * This is used when browser is opened in frontend. 
    * Requests users score based on id found from cookie.
    * @param UserId
    * @return UserModel of UserId
    */

    @RequestMapping(value ="credits/{id}", method= RequestMethod.POST)
    public UserModel getScore(@PathVariable String id) {
        UserModel user = gameRepository.findByName(id);
        return user;
    }

    /*
    * Get current Lucky Number from database and increment it.
    * Also creates new entry if table is empty.
    * @param increase If true lucky number by one.
    * @return current number in database.
    */

    private int getLuckyNumber(boolean increase) {

        List<NumberModel> number = numberRepository.findAll();  //Get lucky number

        if (number.size() > 0) {                                //If it exists does some math
            NumberModel currentNumber = number.get(0);          //Get first from list
            if (currentNumber.getNumber() < 500) {              //If within range increase it      
                if (increase) {
                    currentNumber.increaseNumber();
                }
            } else {                                            //If over 500 reset back to 1
                currentNumber.setNumber(1);
            }
            numberRepository.save(currentNumber);               //Save number and return it
            System.out.println("Current Lucky Number : " +currentNumber.getNumber());
            return currentNumber.getNumber();
        } else {                                                //If this is first time run, create new Lucky Number to database
            numberRepository.save(new NumberModel(1));
            System.out.println("Saving to database");
            return 1;
        }
    }
}