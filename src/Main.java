import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Main {

    private static String password_gen(Set<Integer> options, int pw_length){
        StringBuilder password = new StringBuilder();
        String[] symbols = {"!", "@", "#", "$", "%", "^", "&", "*", "-"};
        String[] similar_char = {"i", "l", "1", "0", "O"};
        String[] lower_case = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z"};
        String[] upper_case = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"};
        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[][] pw_options = {symbols, numbers, lower_case, upper_case, similar_char};
        String[][] choice = new String[options.size()][];
        boolean exclude_sim_char_flag = false;
        int idx = 0;

        for (int option: options){
            // User chose "Exclude Similar Characters"
            if (option == 4)
                exclude_sim_char_flag = true;
            else {
                choice[idx] = pw_options[option];
                idx++;
            }
        }

        while (password.length() < pw_length){
            int random_option_digit = ThreadLocalRandom.current().nextInt(choice.length - 1);
            int random_value = ThreadLocalRandom.current().nextInt(choice[random_option_digit].length - 1);
            if (exclude_sim_char_flag)
                if (Arrays.stream(similar_char).anyMatch(choice[random_option_digit][random_value]::matches))
                    continue;
            password.append(choice[random_option_digit][random_value]);
        }

        return password.toString();
    }

    private static void main_process(){
        int option = 0, pw_length = 0;
        String password;
        Set<Integer> options = new HashSet<Integer>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Greeting!");

        // Ask for entering password length
        while (true){
            try{
                System.out.print("Please enter your prefer password length: ");
                pw_length = scanner.nextInt();
                break;
            }
            catch (Exception InputMismatchException) {
                System.out.println("Please enter integer!!");
            }
        }

        String optionList = """

                Do you password have specify requirement?

                Option 0: Symbols (@#$%)
                Option 1: Include Numbers (123456)
                Option 2: Include Lowercase Characters (abcdefgh)
                Option 3: Include Uppercase Characters (ABCDEFGH)
                Option 4: Exclude Similar Characters (i, l, 1, 0, O)
                Please enter the option number (-1 to end):
                """;
        System.out.print(optionList);

        // Ask for entering password generate option(s)
        do {
            try {
                option = scanner.nextInt();
                // Check if option is between -1 ~ 4
                assertTrue(-1 <= option && option <= 4);
                options.add(option);
            }
            catch (AssertionError error){
                System.out.println("Please enter an integer between -1 and 4");
            }
            catch (InputMismatchException inputMismatchException){
                System.out.println("Please enter valid input");
            }
        } while (option != -1);

        // Remove -1 from set
        options.remove(-1);

        if (options.size() == 0){
            System.out.println("No options were inputted. Process exit.");
            System.exit(0);
        }
        else {
            password = password_gen(options, pw_length);
            System.out.print("Your newly generated password: ");
            System.out.println(password);
        }

    }

    public static void main(String[] args){
        main_process();
    }
}
