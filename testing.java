package calculator;

import java.util.Scanner;

public class testing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        s = s.trim();

        String[] str = s.split("\\s+");
        for (int i = 0; i < str.length; i++) {
            System.out.println(i+ " "+str[i]);
        }
    }
}
