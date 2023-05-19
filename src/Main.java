import java.util.Scanner;

public class Main 
{
    public static int char_num = 128;
    public static int comparisons_num;
    public static int SIZE = 500;
    public static int table[]=new int[SIZE];

    public static void main(String[] args) 
    {
        Scanner input_scan = new Scanner(System.in);
        System.out.println("------------------- DSA-Project 3 -----------------------");
        System.out.println("--------------------- Group-15 -------------------------\n");
        System.out.println("PRACHI MANOJKUMAR SHENDE - ");
        System.out.println("YASH AGRAWAL - 801316596\n");
        System.out.print("Please enter input text_field_value: ");

        String text_field_value = input_scan.nextLine();

        System.out.print("Please enter the text_pattern_value to be detected: ");
        String text_pattern_value = input_scan.nextLine();
        System.out.print("\n Entered Text: "+ text_field_value + "\n");
        System.out.print("\n Entered Pattern: "+ text_pattern_value + "\n");
        System.out.println("\n------ Select your preferred Algorithm ------\n");
        System.out.println("1. Brute Force Algorithm \n");
        System.out.println("2. Boyer-Moore-Horspool Algorithm \n");
        System.out.println("3. Knuth-Morris-Pratt Algorithm \n");
        System.out.println("4. Finite Automation for Pattern Matching");
        System.out.println("\n-------------------------------------------------------");
        System.out.print("\n Kindly input the number corresponding to the preferred algorithm: ");
        try 
        {
            int preferred_alogithm = input_scan.nextInt(); 
            if (preferred_alogithm == 1) 
            {
                long begin_time = System.nanoTime();
                int index_val = BruteForceAlgorithm(text_pattern_value, text_field_value);
                if (index_val == -1) 
                {
                    System.out.println("\n No pattern can be found for the given input!! ");
                }
                else 
                {
                    System.out.println("\n Required Pattern found at: " + index_val + " index");
                }
                System.out.println("\n Total Number of comparisons: " + comparisons_num);
                long end_time = System.nanoTime();
                long total_time = end_time - begin_time;
                System.out.println("\n Overall Time Taken: " + (total_time) + "nano-seconds");   
                
            } 
            else if (preferred_alogithm == 2) 
            {
                long begin_time = System.nanoTime();
                ShiftTable(text_pattern_value);
                int index_val = BoyerMooreHorspoolMatching(text_pattern_value, text_field_value);
                if (index_val == -1) 
                {
                    System.out.println("\n No pattern can be found for the given input!! ");
                } 
                else 
                {
                    System.out.println("\n Required Pattern found at: " + (index_val -1)+ " index");
                }
                System.out.println("\n Total Number of comparisons: " + comparisons_num);
                long end_time = System.nanoTime();
                long total_time = end_time - begin_time;
                System.out.println("\n Overall Time Taken: " + (total_time) + "nano-seconds");
            } 
            else if (preferred_alogithm == 3) 
            {
                long begin_time = System.nanoTime();
                int index_val = KnuthMorrisPrattAlgorithm(text_pattern_value, text_field_value);
                if (index_val == -1) 
                {
                    System.out.println("\n No pattern can be found for the given input!! ");
                } 
                else 
                {
                    System.out.println("\n Required Pattern found at: " + index_val + " index");
                }
                System.out.println("\n Total Number of comparisons: " + comparisons_num);
                long end_time = System.nanoTime();
                long total_time = end_time - begin_time;
                System.out.println("\n Overall Time Taken: " + (total_time) + "nano-seconds");
            
            }
            else if (preferred_alogithm == 4) 
            {
                char[] text_field_value_array = text_field_value.toCharArray();
                char[] text_pattern_value_array = text_pattern_value.toCharArray();
                long begin_time = System.nanoTime();
                int index_val = FiniteAutomatationSearchAlgorithm(text_pattern_value_array,text_field_value_array);
                if (index_val == -1) 
                {
                    System.out.println("\n No pattern can be found for the given input!! ");
                } 
                else 
                {
                    System.out.println("\n Required Pattern found at: " + index_val + " index");
                }
                System.out.println("\n Total Number of comparisons: " + comparisons_num);
                long end_time = System.nanoTime();
                long total_time = end_time - begin_time;
                System.out.println("\n Overall Time Taken: " + (total_time)+ "nano-seconds");   
            
            }
        } 
        catch (Exception ex) 
        {
            System.out.println("\n An error has occurred! Please try again with a valid input value. \n");
            System.out.println("\n Error: \n" + ex);
        }
    }

    public static int BruteForceAlgorithm(String text_pattern_value, String text_field_value) 
    {
        comparisons_num = 0;
        int length_of_text = text_field_value.length();
        int length_of_pattern = text_pattern_value.length();
        for (int x = 0; x <= (length_of_text - length_of_pattern); x++) 
        {
            int y = 0;
            while(y < length_of_pattern && text_field_value.charAt(x + y) == text_pattern_value.charAt(y)) 
            {
                y++;
                comparisons_num++;
            }
            if (y == length_of_pattern) 
            {
                return x;
            }
            comparisons_num++;
        }
        return -1;
    }

    
    public static int NextStepFetch(char[] text_pattern_value_array, int length_of_pattern, int argument_value, int x) 
    {
        if(argument_value < length_of_pattern && x == text_pattern_value_array[argument_value]) 
        {
            return argument_value + 1;
        }

        int next_step; 
        int i;
        for (next_step = argument_value; next_step > 0; next_step--) 
        {
            if (text_pattern_value_array[next_step-1] == x) 
            {
                for (i = 0; i < next_step-1; i++) 
                {
                    if (text_pattern_value_array[i] != text_pattern_value_array[argument_value - next_step + 1 + i]) 
                    {
                        break;
                    }
                }
                if (i == next_step-1) 
                {
                    return next_step;
                }
            }
        }
        return 0;
    }
    
    public static void Transition_Function(char[] text_pattern_value_array, int length_of_pattern, int[][] transaction_function) 
    {
        for (int i = 0; i <= length_of_pattern; ++i) 
        {
            for (int j = 0; j < char_num; ++j) 
            {
                transaction_function[i][j] = NextStepFetch(text_pattern_value_array, length_of_pattern, i, j);
            }
        }
    }

    public static int FiniteAutomatationSearchAlgorithm(char[] text_pattern_value_array, char[] text_field_value_array) 
    {
        int length_of_pattern = text_pattern_value_array.length;
        int length_of_text = text_field_value_array.length;
        int[][] transaction_function = new int[length_of_pattern+1][char_num];
        comparisons_num = 0;

        Transition_Function(text_pattern_value_array, length_of_pattern, transaction_function);

        int x; 
        int argument_value = 0;
        for (x = 0; x < length_of_text; x++) 
        {
            comparisons_num++;
            argument_value = transaction_function[argument_value][text_field_value_array[x]];
            if (argument_value == length_of_pattern) 
            {
                return (x - length_of_pattern + 1);
            }
        }
        return -1;
    }

    
    public static void Failure(String text_pattern_value, int length_of_pattern, int[] suffix_array) 
    {

        int x = 1;
        int y = 0;

        suffix_array[0] = 0;

        while (x < length_of_pattern) {
            if (text_pattern_value.charAt(x) == text_pattern_value.charAt(y)) 
            {
                suffix_array[x] = y + 1;
                y++;
                x++;
            } 
            else if (y > 0) 
            {
                y = suffix_array[y - 1];
            } 
            else 
            {
                suffix_array[x] = 0;
                x++;
            }
        }
    }

    public static void ShiftTable(String pattern){
        int i, j;
        char P[] = pattern.toCharArray();
        int length_of_pattern = pattern.length();

        for (i = 0; i < SIZE; i++) {
            table[i] = length_of_pattern;
        } 
        for (j = 0; j < length_of_pattern-1; j++) {
            table[P[j]] = length_of_pattern - 1 - j;
        } 

    }

    public static int KnuthMorrisPrattAlgorithm(String text_pattern_value, String text_field_value) 
    {
        int length_of_pattern = text_pattern_value.length();
        int length_of_text = text_field_value.length();
        int[] suffix_array = new int[length_of_pattern];
        int x = 0; 
        int y = 0;
        comparisons_num = 0;

        Failure(text_pattern_value, length_of_pattern, suffix_array);

        while(x < length_of_text) 
        {
            comparisons_num++;
            if(text_field_value.charAt(x) == text_pattern_value.charAt(y)) 
            {
                if(y == length_of_pattern - 1) 
                {
                    return (x - y);
                }
                else 
                {
                    y++;
                    x++;
                  
                }
            } 
            else if(y > 0) 
            {
                y = suffix_array[y - 1];
            } 
            else 
            {
                x++;
            }
        }
        return -1;
    }

    public static int BoyerMooreHorspoolMatching(String pattern, String text){
        int i, k, pos;
        char T[] = text.toCharArray();
        char P[] = pattern.toCharArray();
        int length_of_pattern = pattern.length();
        int length_of_text = text.length();
        comparisons_num = 0;

        for(i=length_of_pattern-1;i<length_of_text;){
            k=0;
            while( (k<length_of_pattern) && P[length_of_pattern-1-k] == T[i-k] ) {
                k++; 
                comparisons_num++;
             } 
            if( k == length_of_pattern ){
                    pos = i - length_of_pattern + 2;
                    return pos;
            } else {
                i += table[T[i]];
            }
            comparisons_num++;
        } 
        return -1;
    } 
}