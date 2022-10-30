package univ.tuit.dictionarybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class DictionaryBotApplication {
    private static String filePath = "/Users/akbarhasanov/IdeaProjects/DictionaryBot/src/main/resources/test.txt";

    public static void main(String[] args) {
        SpringApplication.run(DictionaryBotApplication.class, args);

       /* Scanner sc = new Scanner(System.in);
        boolean result = true;
        Random random = new Random();
        arraySize();

        while (result) {
            int size = random.nextInt(arraySize().size());
            int innerSize = arraySize().get(size).size();
            String s = arraySize().get(size).get(random.nextInt(innerSize));
            System.out.print(s);
            System.out.print("\nYour answer: ");
            String b = sc.nextLine();
            int temp = 0;
            if (!s.equals(b.trim())) {
                for (int i = 0; i < innerSize; i++) {
                    if (b.equals(arraySize().get(size).get(i).trim())) {
                        result = true;
                        System.out.print("Correct \nNext: ");
                        break;
                    } else temp++;
                }
                if (temp == innerSize) {
                    for (int i = 0; i < innerSize; i++) {
                        System.out.print(arraySize().get(size).get(i));
                        if (i != innerSize-1)
                            System.out.print(" = ");
                    }
                    System.out.println();
                    result = false;
                    System.out.println("Game over");
                }
            }
        }*/
//		word(arraySize());
    }

   /* private static List<List<String>> arraySize() {
        List<List<String>> listOfLists = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(
                new FileReader(filePath))) {
            String str;
            while ((str = buffer.readLine()) != null) {
                String[] split = str.split("=");
                List<String> word = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    if (!split[i].equals("=")) {
                        word.add(split[i].trim());
                    }
                }
                listOfLists.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfLists;
    }*/

    private static void word(int size) {

        int[] wordArray = new int[size];


    }
}
