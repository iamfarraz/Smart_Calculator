package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // put your code here
        String s = sc.nextLine();
        s = s.trim();


        while (!s.equals("/exit")) {
            if (!s.equals("") && !s.matches("/.+")) {
                 boolean vld=true;
                s=s.replaceAll("\\++","+");
                String[] str = s.split(" +");

                List<Integer> num = new ArrayList();
                for (int i = 0; i < str.length; i++) {
                    if (str[i].matches("-+")) {
                        if (str[i].length() % 2 != 0) {
                            str[i+1]="-"+str[i+1];

                            continue;
                        }
                        else {
                            str[i]=str[i].replaceAll("-+","+");
                            str[i+1]="+"+str[i+1];
//                            System.out.println( str[i+1]+"lol");
                            continue;
                        }
                    }
                    if (str[i].matches("\\++")){
                        str[i+1]="+"+str[i+1];
                        continue;
                    }

                        try {
                            num.add(Integer.parseInt(str[i]));
                        } catch (Exception e) {
                            System.out.println("Invalid expression");
                            vld = false;
                            break;
                        }

                }
                for (int i = 0; i < str.length-1; i++) {
//
                    if (str[i].matches("\\d+") && !str[i+1].matches("\\+|-") ){
                        vld=false;
                        System.out.println("Invalid expression");
                    }
                }

                if(vld==true) {
                    int rslt = 0;

                    for (int i = 0; i < num.size(); i++) {
                        rslt += num.get(i);
                    }
                    System.out.println(rslt);
                }
            }
            else if(s.matches("/.+") && !s.equals("/exit")){
                System.out.println("Unknown command");
            }
            s = sc.nextLine();
            s = s.trim();

        }
        System.out.println("Bye!");

    }

}
