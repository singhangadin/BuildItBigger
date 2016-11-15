package com.example;

import java.util.Random;

public class JokeClass {
    public static String tellAJoke()
    {   String jokes[]={"Can a kangaroo jump higher than a house? Of course, a house doesn’t jump at all.",
                        "My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away.",
                        "Scientists have now discovered how women keep their secrets. They do so within groups of 40.",
                        "Why is it a bad idea for two butt cheeks to get married? Because they part for every little shit.",
                        "I'd like to buy a new boomerang please. Also, can you tell me how to throw the old one away?"};
        Random rand=new Random();
        return jokes[rand.nextInt(5)];
    }
}
