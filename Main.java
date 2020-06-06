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

                List<Integer> num = new ArrayList();
                for (int i = 0; i < str.length; i++) {
                    if (str[i].matches("-+")) {
                        if (str[i].length() % 2 != 0) {
                            str[i]=str[i].replaceAll("-+","-");
                            str[i+1]="-"+str[i+1];
                            continue;
                        }
                        else {
                            str[i]=str[i].replaceAll("-+","+");
                            str[i+1]="+"+str[i+1];
                            continue;
                        }
                    }
                    if (str[i].matches("\\++")){
                        str[i]=str[i].replaceAll("-+","+");
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
        }
}


