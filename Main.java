package calculator;
import java.util.*;

public class Main {
  static   Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        // put your code here
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

    public static void cal(String s,Map<String ,Integer> map){

            if (!s.equals("") && !s.matches("/.+")) {
                boolean vld=true;
                String[] str = s.split(" +");
                for(int i=0;i<str.length;i++){
                    if(map.containsKey(str[i])){
                        str[i]=String.valueOf(map.get(str[i]));
                       continue;
                    }
                }

                System.out.println(postfix_eval(postfix(str)));

//                List<Integer> num = new ArrayList();
//                for (int i = 0; i < str.length; i++) {
//                    if (str[i].matches("-+")) {
//                        if (str[i].length() % 2 != 0) {
//                            str[i]=str[i].replaceAll("-+","-");
//                            str[i+1]="-"+str[i+1];
//                            continue;
//                        }
//                        else {
//                            str[i]=str[i].replaceAll("-+","+");
//                            str[i+1]="+"+str[i+1];
//                            continue;
//                        }
//                    }
//                    if (str[i].matches("\\++")){
//                        str[i]=str[i].replaceAll("-+","+");
//                        str[i+1]="+"+str[i+1];
//                        continue;
//                    }
//
//                    try {
//                        num.add(Integer.parseInt(str[i]));
//                    } catch (Exception e) {
//                        System.out.println("Invalid expression");
//                        vld = false;
//                        break;
//                    }
//
//                }
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
//            else if(s.matches("/.+") && !s.equals("/exit")){
//                System.out.println("Unknown command");
                   }
        }
   public static String[] postfix(String[] s){
        Stack<String> stack=new Stack<>();
        String snew="";
        for(String add:s) snew+=add;
        char[] chr=snew.toCharArray();
        String pfix="";
        for(char c:chr){
         if(Character.toString(c).equals("(")) {
             stack.push("(");
         }
        else if(Character.toString(c).matches("[a-zA-Z]+|\\d+")){
             pfix +=c+" ";
         }
       else  if(Character.toString(c).matches("[*/%\\+-]")){

             if(stack.empty()) stack.push(Character.toString(c));
             else if(!stack.empty()) {

                 boolean psh1 = false;
                 while (psh1==false) {


                     if (stack.empty() || (priority(Character.toString(c)) > priority(stack.peek())) ) {

                         stack.push(Character.toString(c));

                         psh1 = true;
                         break;
                     } else if (priority(Character.toString(c)) <= priority(stack.peek())) {
                         pfix += stack.pop()+" ";

                     }
                 }
             }
         }

        else if(Character.toString(c).equals(")") ){
                 while (!stack.peek().equals("(")) {
                     pfix += stack.pop() + " ";

                 }
                 stack.pop();
         }

        }

        while (!stack.empty()){
            pfix +=stack.pop()+" ";
        }
       System.out.println(pfix);
        return pfix.split(" ");
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
   public static int postfix_eval(String[] s){

        Stack<Integer> rslt=new Stack<>();
        for (String c:s){
            if(c.matches("\\d+")){
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
        }
        return rslt.peek();
   }
}



