import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

//Ashley
//28/11/2024
//Collaborators:
//Hangman
public class Hangman {

    public static void main(String[] args) throws FileNotFoundException
    {
        menu();
    }

    public static void flush()
    {
        //As the intelliJ console is not a proper terminal, there is no command to actually clear the console.
        //Therefore, this method prints out a lot of new lines to flush the previous text away
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    public static void menu() throws FileNotFoundException {
        System.out.println("Welcome to Hangman!\nEnter 1 to begin a game\nEnter 2 to read the rules");
        Scanner scanner = new Scanner(System.in);
        String input1 = "";
        input1 += scanner.nextLine();

        while (!(input1.equals("1")||input1.equals("2")))
        {
            System.out.print("Please enter a valid option:");
            input1 = scanner.nextLine();
        }
        if(input1.equals("1"))
        {
            startGame();
        }
        if(input1.equals("2"))
        {
            flush();
            rules();
        }

    }
    public static void rules() throws FileNotFoundException {
        System.out.println("HANGMAN RULES");
        System.out.println("A random word will be selected and hidden. However, you will know the length of the word.\nYour aim is to guess all the letters in the word one by one before 7 incorrect guesses.\nYour incorrect guesses will be displayed on the side, and correct guesses will be revealed in the word.");
        System.out.println("Enter 1 to return to menu.");
        Scanner scanner = new Scanner(System.in);
        String input1 = "";
        input1 += scanner.nextLine();
        while (!input1.equals("1"))//repeatedly prompts user
        {
            System.out.print("Please enter a valid option: ");
            input1 = scanner.nextLine();
        }
        menu();

    }
    public static void startGame() throws FileNotFoundException
    {
        flush();
        String word = getRandomWord();
        System.out.println(word);

        int lives = 7;
        String dashes = "_".repeat(word.length()) + " (" + word.length() + " letters)"; //turns the hidden word into dashes
        String incorrect = "Incorrect letters: ";

        while(lives>0) //repeatedly prompts user
        {
            System.out.println(dashes);
            System.out.println("\nGuess a letter!");
            System.out.println(incorrect);
            String guessResult = guess(word, dashes);
            if(guessResult.length() == 1)
            {
                flush();
                lives--;
                if(lives == 6)
                {
                    one();
                }
                if(lives == 5)
                {
                    two();
                }
                if(lives == 4)
                {
                    three();
                }
                if(lives == 3)
                {
                    four();
                }
                if(lives == 2)
                {
                    five();
                }
                if(lives == 1)
                {
                    six();
                }

                System.out.println("Your guess was incorrect!"+"You have "+lives+" lives left!\n");
                incorrect = incorrect + " " + guessResult;
            }
            else
            {
                flush();
                if(lives == 6)
                {
                    one();
                }
                if(lives == 5)
                {
                    two();
                }
                if(lives == 4)
                {
                    three();
                }
                if(lives == 3)
                {
                    four();
                }
                if(lives == 2)
                {
                    five();
                }
                if(lives == 1)
                {
                    six();
                }
                System.out.println("Your guess was correct!"+"You have "+lives+" lives left!\n");
                dashes = guessResult;

                int wordLength = word.length();
                if(dashes.substring(0,wordLength).equals(word))
                {
                    win();
                }
            }
        }
       seven();
        System.out.println("You lost all your lives! The word was \""+word+"\"");
        System.out.println("Enter 1 to play again\nEnter 2 to exit");
        Scanner scanner = new Scanner(System.in);
        String input1 = "";
        input1 += scanner.nextLine();
        while (!(input1.equals("1")||input1.equals("2")))
        {
            System.out.print("Please enter a valid option: ");
            input1 = scanner.nextLine();
        }
        if(input1.equals("1"))
        {
            startGame();
        }
        if(input1.equals("2"))
        {
            System.out.println("Thank you for playing!");
            System.exit(0); //stops code
        }


    }

    public static void win() throws FileNotFoundException {
        System.out.println("Congratulations! You guessed the word.");
        System.out.println("Enter 1 to play again\nEnter 2 to exit");
        Scanner scanner = new Scanner(System.in);
        String input1 = "";
        input1 += scanner.nextLine();
        while (!(input1.equals("1")||input1.equals("2")))
        {
            System.out.print("Please enter a valid option: ");
            input1 = scanner.nextLine();
        }
        if(input1.equals("1"))
        {
            startGame();
        }
        if(input1.equals("2"))
        {
            System.out.println("Thank you for playing!");
            System.exit(0); //stops code
        }
    }
    public static boolean isSingleCharacter(String input) {
        return input.length() == 1;
    }
    public static boolean isCharacter(String input)
    {
        return isSingleCharacter(input) && Character.isLetter(input.charAt(0));
    }
    public static String guess(String word, String dashes)
    {
        Scanner scanner = new Scanner(System.in);
        String guess = scanner.next();
        StringBuilder sb = new StringBuilder(dashes);
        while(!isCharacter(guess))
        {
            System.out.println("Input invalid! Please enter a single letter.");
            guess = scanner.next();
        }
        if(isCharacter(guess))
        {
            guess = guess.toUpperCase();
        }
        if(word.contains(guess))
        {
            int guessedIndex;
            for(int i = 0; i<word.length(); i++) //this finds the position of the correct letter and reveals it
            {
                if(word.substring(i,i+1).equals(guess))
                {
                    guessedIndex = i;
                    sb.replace(guessedIndex,guessedIndex+1,guess);
                }
            }
            return ""+sb;

        }
        else
        {
            return guess;
        }



    }
    public static String getRandomWord() throws FileNotFoundException {


        Random random = new Random();
        String[] array = storeArrayString("words");
        int randomIndex = random.nextInt(array.length);
        return array[randomIndex];

    }
    public static int countLines (String fileName) throws FileNotFoundException {
        int count = 0;
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
        {
            count++;
            scanner.nextLine();
        }
        return count;
    }
    public static String[] storeArrayString (String fileName) throws FileNotFoundException {
        int lines = countLines(fileName);
        String[] array = new String[lines];

        int index = 0;

        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {

            array[index++] = scanner.nextLine();

        }

        return array;

    }

    public static void one() {
        System.out.println("  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========");
    }
    public static void two() {
        System.out.println("  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========");
    }
    public static void three()
    {
        System.out.println("  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========");
    }
    public static void four() {
        System.out.println("  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========");
    }
    public static void five() {
        System.out.println("  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========");
    }
    public static void six() {
        System.out.println("  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========");
    }
    public static void seven() {
        System.out.println("  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n=========");
    }

}




