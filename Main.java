package calculator;
import java.util.*;

public class Main {
  static   Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        String s = sc.nextLine().trim();
         Map<String ,Integer> keyvalue=new TreeMap<>();
        while (!s.equals("/exit")) {

            String[] str=s.split(" +");
           if(s.contains("=")){
               s= s.replaceAll(" +","");
              String[] kv=s.split("=");
              if(kv[0].matches("[A-Za-z]+") ){
             keyvalue.putAll(varasgn(kv,keyvalue));
              }
              else System.out.println("Invalid identifier");
           }
           else if(str.length==1 && str[0].matches("[A-Za-z]+")){
                  keyvalue.putAll(varasgn(str,keyvalue));
           }
           else{
               cal(s,keyvalue);
           }

            s = sc.nextLine().trim();
        }
        System.out.println("Bye!");
    }
       public static  Map<String ,Integer> varasgn(String[] s, Map<String ,Integer> map){
        if(s.length==2){
            if(map.containsKey(s[1])){
                map.put(s[0],map.get(s[1]));
            }
             else if(!map.containsKey(s[1]) && s[1].matches("[A-Za-z]+")){
                System.out.println("Unknown variable");
            }
            else{
                try {
                    map.put(s[0], Integer.valueOf(s[1]));
                }
                catch (Exception e){
                    System.out.println("Invalid assignment");
                }
            }
        }
        else if(s.length==1){
            if(map.containsKey(s[0])){
                System.out.println(map.get(s[0]));
            }
            else {
                System.out.println("Unknown variable");
            }
        }
        else System.out.println("Invalid assignment");

        return map;
       }

    public static void cal(String s,Map<String ,Integer> map) {

        if (!s.equals("") && !s.matches("/.+")) {
            boolean vld = true;
            String[] str = s.split(" +");
            for (int i = 0; i < str.length; i++) {
                if (map.containsKey(str[i])) {
                    str[i] = String.valueOf(map.get(str[i]));
                    continue;
                }
            }
            for (int i = 0; i < str.length; i++) {
                if (str[i].matches("-+")) {
                    if (str[i].length() % 2 != 0) {
                        str[i] = str[i].replaceAll("-+", "-");

                        continue;
                    } else {
                        str[i] = str[i].replaceAll("-+", "+");

                        continue;
                    }
                }
                if (str[i].matches("\\++")) {
                    str[i] = str[i].replaceAll("\\++", "+");

                    continue;
                }
            }
            postfix_eval(postfix(str));

//                List<Integer> num = new ArrayList();
//                    try {
//                        num.add(Integer.parseInt(str[i]));
//                    } catch (Exception e) {
//                        System.out.println("Invalid expression");
//                        vld = false;
//                        break;
//                    }


//                for (int i = 0; i < str.length-1; i++) {
//                    if (str[i].matches("\\d+") && !str[i+1].matches("\\+|-") ){
//                        vld=false;
//                        System.out.println("Invalid expression");
//                    }
//                }
//
//                if(vld==true) {
//                    int rslt = 0;
//                    for (int i = 0; i < num.size(); i++) {
//                        rslt += num.get(i);
//                       }
//                    System.out.println(rslt);
//                }
//            }

        }
       else if (s.matches("/.+") && !s.equals("/exit")) {
            System.out.println("Unknown command");
        }
    }
   public static String postfix(String[] s){
        Stack<String> stack=new Stack<>();
        String snew="";
       String pfix = "";
        for(String add:s) snew+=add;
        char[] chr=snew.toCharArray();
        if(brckt_blnce(chr)) {
            for (String c : s) {
//                System.out.println(c);
                if (c.contains("(")) {
                    char [] brck=c.toCharArray();
                    stack.push("(");
                    c = c.replace("(", "");
                    if (c.matches("[a-zA-Z]+|\\d+|-\\d+|\\+\\d+")) {
                        pfix += c + " ";
                    }
                }
                else if (c.matches("[a-zA-Z]+|\\d+|-\\d+|\\+\\d+")) {
                    pfix += c + " ";
                }
                else if (c.matches("[*/%\\+-]")) {

                    if (stack.empty()) stack.push(c);
                    else if (!stack.empty()) {

                        boolean psh1 = false;
                        while (psh1 == false) {
                            if (stack.empty() || (priority(c) > priority(stack.peek()))) {
                                stack.push(c);
                                psh1 = true;
                                break;
                            } else if (priority(c) <= priority(stack.peek())) {
                                pfix += stack.pop() + " ";

                            }
                        }
                    }
                }
                else if (c.contains(")")) {
                    c = c.replace(")", "");
                    if (c.matches("[a-zA-Z]+|\\d+")) {
                        pfix += c + " ";
                    }
                    while (!stack.peek().equals("(")) {
                        pfix += stack.pop() + " ";
                    }
                    stack.pop();
                }
                else{
                    pfix="Invalid expression"; //when invalid calculation
                    System.out.println("lol");
                    break;
                }

            }

            while (!stack.empty()) {
                pfix += stack.pop() + " ";
            }
            System.out.println(pfix);
            return pfix;
        }
        else {
            pfix="Invalid expression";//when brckt not blcnd
            System.out.println(pfix);
        }
        return pfix;
   }
   public static   int priority(String s){
        int  n=0;
        switch (s){

            case "%": n=4; break;
            case  "/": n=3; break;
            case "*":n=2; break;
            case "-":n=1; break;
            case "+":n=0; break;
            case "(": n=-1; break;

        }

        return n;
   }
   public static void postfix_eval(String s){
        boolean vld=true;
        if(!s.equals("Invalid expression")){
        String[] s1=s.split(" ");
        Stack<Integer> rslt=new Stack<>();
        for (String c:s1){
            if(c.matches("\\d+|-\\d+|\\+\\d+")){
                rslt.push(Integer.parseInt(c));
//                System.out.println(rslt);
            }
             else if(c.matches("[+-/%*]")){

                 int b=rslt.pop();
                int a=rslt.pop();
                 int cal=0;
                 switch (c){
                     case "*":cal=a*b; break;
                     case "/":cal=a/b; break;
                     case "%":cal=a%b; break;
                     case "+":cal=a+b; break;
                     case "-":cal=a-b; break;
                 }
                 rslt.push(cal);
            }
             else{
                 vld=false;
                System.out.println("Invalid expression");
                break;
            }
        }
      if(vld) System.out.println(rslt.peek());}
   }
   public static boolean brckt_blnce(char[] brckt){
       Stack<Character> chck=new Stack<>();
       boolean p=true;
       for(Character c:brckt){
           if(c.equals('{')) chck.push('{');
           else if(c.equals('(')) chck.push('(');
           else if(c.equals('[')) chck.push('[');
           if (!chck.empty() &&c.equals('}')&& chck.peek().equals('{') ) chck.pop();
           else if (!chck.empty() &&c.equals(']')&& chck.peek().equals('[') ) chck.pop();
           else   if (!chck.empty() &&c.equals(')')&& chck.peek().equals('(') ) chck.pop();
           else if(c.equals('}')||c.equals(']')||c.equals(')') ) p=false;
       }
       if(chck.empty() && p) p=true;
       else p=false;

       return p;
   }
}



