package pro.hosteur.T_Assessment._K_bougrine;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class TestController {
    @GetMapping("api/v1.0/emails-generator")
    public @ResponseBody ResponseEntity<?> getitem(
                                        @RequestParam Map<String, String> inputs
                                        ){
        String expression = inputs.get("expression").replaceAll("\\s", "");
        inputs.remove("expression");
            System.out.println(inputs);
            System.out.println(expression+expression.length());
            //return editOneInput("input1.eachWordFirstChars(1)",inputs);
        StringBuilder resulta= new StringBuilder();
        while (expression.length()>0 ){
            if(expression.substring(1).indexOf("~")==-1){
                break;
            }
            if(!expression.startsWith("(")){
//                   System.out.println(expression.substring(1));
                  int endstatement =expression.substring(1).indexOf("~");
                  System.out.println("E---"+expression.substring(0,endstatement+1));
                    System.out.println(("-test--" + expression.substring(0, endstatement + 1)));
                    resulta.append(editOneInput(expression.substring(0, endstatement + 1), inputs));
                   expression =expression.substring(endstatement+2);

            }
            if(expression.startsWith("("))
            {
                int endstatement1=findEndIfStatement(expression,0);
               System.out.println("E-----"+expression.substring(1,endstatement1));
                resulta.append(ifStatementEdit(expression.substring(1,endstatement1),inputs));
                expression =expression.substring(endstatement1+2);
            }
      }
        if(expression.startsWith("(")){
            resulta.append(ifStatementEdit(expression,inputs));

        }else {
            resulta.append(editOneInput(expression, inputs));
        }

        System.out.println("E-----"+expression);


        if( ! isValidEmailAddress(resulta.toString())) {
            return new ResponseEntity<>( "No Valid Email : " +resulta.toString(), HttpStatus.BAD_REQUEST);
        }else {
            List<Mail> data = new ArrayList<>(){{
                add(new Mail(resulta.toString()));
            }};

            return new ResponseEntity<Model>(new Model(data), HttpStatus.OK);
        }





    }
    private List<String> spliteWords(String inputItem){
        List<String> resulta = new ArrayList<>();
        String[] splites_Words= inputItem.split("-");
        for (String item : splites_Words) {
            resulta.add( item.toLowerCase().replaceAll("\\s", ""));
        }
        return resulta;
    }

    private String eachWordExtractChars(String input , int index,int longueur ){
        List<String> listOfWords= spliteWords(input);
        StringBuilder resulta = new StringBuilder();
        for(String item : listOfWords ){
            if(index==-1){
                resulta.append(item.substring(item.length(),longueur));
            }else {
                resulta.append(item.substring(index,longueur));

            }
        }
    return resulta.toString();
    }
    private int wordsCount(String input){
        return spliteWords(input).size();
    }
    private  String extractWords(String input,int index,int longueur){
        List<String> listOfWords = spliteWords(input);
        if(index==-1){
            index=listOfWords.size()-1;
        }

        if(longueur<0){
            return  String.join("-", listOfWords.subList(index+longueur,index+1));
         }
        else {
            return  String.join("-", listOfWords.subList(index,index+longueur));
        }
    }
    private String editManyInput(String manyInputs,Map<String, String> inputs){
        List<String> manyInputList = List.of(manyInputs.split("~"));
        StringBuilder resulta =new StringBuilder();
        for (String item : manyInputList) {

            resulta.append(editOneInput(item,inputs));
        }
return  resulta.toString();
    }
    private String editOneInput(String inputEtFunction, Map<String, String> inputs){
        StringBuilder resulta = new StringBuilder();
        if(inputEtFunction.contains("'@'")){
            return  resulta.append("@").toString();
        }
        if(inputEtFunction.contains("'.'")){
            return  resulta.append(".").toString();
        }
        String[] listInputEtFunction = inputEtFunction.split("\\.");
        System.out.println(Arrays.stream(listInputEtFunction).count());
        String input = inputs.get(listInputEtFunction[0]);

        HashMap<String,Integer> indexHashMap=new HashMap<>(){{
            put("first",0);
            put("second",1);
            put("third",2);
            put("fourth",3);
            put("last",-1);
        }};
          if (listInputEtFunction.length==1) {
            resulta.append(inputs.get(listInputEtFunction[0]).toLowerCase().replaceAll("-", ""));
            return resulta.toString();
        }
        for (int i = 1; i < listInputEtFunction.length ; i++) {
            if(listInputEtFunction[i].startsWith("eachWord")){
                int longeur =Integer.parseInt(String.valueOf(listInputEtFunction[i].
                        charAt(listInputEtFunction[i].indexOf("(") +1)));
                int index= indexHashMap.get( listInputEtFunction[i].substring(8,listInputEtFunction[i].indexOf("Chars")).toLowerCase());
                System.out.println(input+"/////////"+longeur+"////////"+index);
                resulta.append(eachWordExtractChars(input,index,longeur));

            } else if (listInputEtFunction[i].contains("Words")) {
                int longeur1 =Integer.parseInt(String.valueOf(listInputEtFunction[i].
                        substring(listInputEtFunction[i].indexOf("(") +1,listInputEtFunction[i].indexOf(")") )));
                int index1= indexHashMap.get( listInputEtFunction[i].substring(0,listInputEtFunction[i].indexOf("Words")).toLowerCase());
                System.out.println(input+"/////////"+longeur1+"////////"+index1);
                if(i==listInputEtFunction.length -1){
                    resulta.append(extractWords(input,index1,longeur1));

                }else {
                    input=extractWords(input,index1,longeur1);

                }

            }


        }

        return  resulta.toString();
    }
     private String ifStatementEdit(String statement,Map<String, String> inputs){
        StringBuilder resulta = new StringBuilder();
        String condition=statement.substring(0,statement.indexOf("?"));
        String editIfTrue=statement.substring(statement.indexOf("?")+1,statement.indexOf(":"));
        String editIfFalse=statement.substring(statement.indexOf(":")+1,statement.length());
        System.out.println((condition+"----"+editIfTrue+"-------"+editIfFalse));
        String[] conditionList = condition.split("<|>|<=|>=|=");

        String input =inputs.get(conditionList[0].substring(conditionList[0].
                indexOf("input"),conditionList[0].indexOf("input")+6));
        String conditionOperation = String.valueOf(condition.charAt(conditionList[0].length()));
        System.out.println(input);
        System.out.println(conditionOperation.equals(">")+conditionList[0].substring(conditionList[0].indexOf("input"),conditionList[0].indexOf("input")+6));

        if(conditionOperation.equals(">") && wordsCount(input) > Integer.parseInt(conditionList[1]) ||
                conditionOperation.equals("<") && wordsCount(input) < Integer.parseInt(conditionList[1]) ||
                conditionOperation.equals("=") && wordsCount(input) ==Integer.parseInt(conditionList[1]) ||
                conditionOperation.equals("<=") && wordsCount(input) <= Integer.parseInt(conditionList[1]) ||
                conditionOperation.equals(">=") && wordsCount(input) >= Integer.parseInt(conditionList[1])
        ) {
            System.out.println("eeee");
            resulta.append(editManyInput(editIfTrue, inputs));
          System.out.println("eeee");
        } else {
            System.out.println("eeee"+editIfFalse);

            resulta.append(editManyInput(editIfFalse,inputs));
        }
         System.out.println("eeee"+ resulta);

         return resulta.toString();
//return  null;

     }
     private  int findEndIfStatement(String string , int start ){
        int resulta=1;
        int i=start;
        while (resulta!=0){
            i=i+1;
            if(string.charAt(i) ==')'){
                resulta=resulta-1;

            }else if(string.charAt(i)=='('){
                resulta=resulta+1;

            }

        }
        return  i;


     }
     private boolean validateMail(String email){
         //Regular Expression
         String regex = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}";
         //Compile regular expression to get the pattern
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(email);
         return matcher.matches();
     }
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }


}
