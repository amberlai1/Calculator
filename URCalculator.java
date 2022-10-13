import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class URCalculator {
    public static boolean flag;
    public static String char1;
   
       
    public static boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
      }

    public static Queue<String> infixToPostfix(String input){
        HashMap<String, Integer> operator = new HashMap<String, Integer>();  // making hashmap with operators in precendence order 
        // source: https://www.java67.com/2016/01/how-to-initialize-hashmap-with-values-in-java.html#ixzz7h4qi5yB5
        operator.put("+", 1);
        operator.put("-", 1); 
        operator.put("*", 2);
        operator.put("/", 2);
        operator.put("%", 2); 
        operator.put("^", 3);   
        operator.put("=", 3);
        operator.put("<", 3); 
        operator.put(">", 3); 
        operator.put("&", 3); 
        operator.put("|", 3); 
        operator.put("!", 3);    
        String input1 = input.replace(" ",""); // gets rid of spaces if there are
        String[] fix = input1.split("(?<=[-+*/^=<>&%|!()])|(?=[-+*/^=<>%&|!()])"); //splits by operators which means no spaces required
        // source: https://stackoverflow.com/questions/13525024/how-to-split-a-mathematical-expression-on-operators-as-delimiters-while-keeping
        
        Queue<String> queue1 = new Queue<>();
        Stack<String> stack1 = new Stack<>();
        for(String a : fix){
            if(isNumeric(a) == true) { // checks if it is a number
                queue1.enqueue(a);
            }
            else if(a.contains(".")){ // checks if it is a decimal
                queue1.enqueue(a);
            }
            else if(a == "("){  // if it is ( then push onto stack 
                stack1.push(a);
            }
               
            else if(a == ")"){ // if a is ")"
                
                for(int j = 0; j < stack1.size(); j++) // checks stack to see if equal to (
                    if(stack1.peek() == "("){
                        stack1.pop(); //pops off stack but does NOT put into queue
                    }
                    else { // if not equal to "(" then pops element
                        queue1.enqueue(stack1.pop());
                    }
                }
            else{ 
                if(operator.containsKey(a)){ // if it doesn't contain operator then it probably is invalid input at this point 
                    if(stack1.isEmpty()){ //nothing in the stack put operator 
                        stack1.push(a);
                    } 
                    else {
                        while(!stack1.isEmpty() && operator.get(a) <= operator.get(stack1.peek())){ // what if it keeps popping til it empty 
                            queue1.enqueue(stack1.pop()); 
                        }
                        stack1.push(a); // push a when there are no longer any operators bigger than it 
                }

                }
            }
            
        }
    
        while(!stack1.isEmpty()){ // keeps going until false and empty and pushes everything into the queue 
            queue1.enqueue(stack1.pop());
        }   
        return queue1;
    }


  public static Double evaluation(Queue<String> q){ // should return the single value in the stack and queue should be empty 
    
        Stack<Double> eval = new Stack<>();

        while(q.size() > 0){  // need to be 0 
            Double bool; 
            if(isNumeric(q.peek()) == true){  
                Double num = Double.parseDouble(q.dequeue());
                eval.push(num);
            }
            else if(q.peek().contains(".")){ // checks if it is a decimal
                Double num = Double.parseDouble(q.dequeue());
                eval.push(num);
            } 
            else if(q.peek().equals("+")){
                q.dequeue();
                Double num1 = eval.pop();
                Double num2 = eval.pop();
                eval.push(num1 + num2);
            }
            else if(q.peek().equals("-")){
                q.dequeue();
                Double num1 = eval.pop();
                Double num2 = eval.pop();
                eval.push(num1 - num2);
            }
            else if(q.peek().equals("*") ){
                q.dequeue();
                Double num1 = eval.pop();
                Double num2 = eval.pop();
                eval.push(num1 * num2);
            }
            else if(q.peek().equals("/")){
                q.dequeue();
                Double num1 = eval.pop();
                Double num2 = eval.pop();
                eval.push(num2 / num1);
            } 
            else if(q.peek().equals("^")){
                q.dequeue();
                Double num1 = eval.pop();
                Double num2 = eval.pop();
                eval.push(Math.pow(num2, num1));
            }
            else if(q.peek().equals("%")){
                q.dequeue();
                Double num1 = eval.pop();
                Double num2 = eval.pop();
                eval.push(num2 % num1);
            }
            else if(q.peek().equals("=") ){
                q.dequeue();
                Double num1 = eval.pop();
                Double num2 = eval.pop();
                if(num1.equals(num2)){
                    bool = 1.00;
                }
                else{
                    bool = 0.00;
                }
                eval.push(bool);
            }
            else if(q.peek().equals("<")){
                q.dequeue();
                Double num1 = eval.pop();
                Double num2 = eval.pop();
                if(num2 < num1 != true){
                    bool = 0.00;
                }
                else{
                    bool = 1.00;
                }
                eval.push(bool);
            }
            else if(q.peek().equals(">") ){
                q.dequeue();
                Double num1 = eval.pop();
                Double num2 = eval.pop();
                if(num2 > num1 != true){
                    bool = 0.00;
                }
                else{
                    bool = 1.00;
                }
                eval.push(bool);
            }
            else if(q.peek().equals("&") ){
                q.dequeue();
                Double num1 = eval.pop();
                Double num2 = eval.pop();
                if(num1 == 1.00 && num2 == 1.00 ){
                    bool = 1.00;
                }
                else{
                    bool = 0.00;
                }
                eval.push(bool);
            }
            else if(q.peek().equals("|") ){
                q.dequeue();
                Double num1 = eval.pop();
                Double num2 = eval.pop();
                if(num1 == 1.00 || num2 == 1.00 ){
                    bool = 1.00;
                }
                else{
                    bool = 0.00;
                }
                eval.push(bool);
            }
            else if (q.peek().equals("!") ){
                q.dequeue();
                Double num1 = eval.pop();
             
                if(num1 == 1.00 ){ // if num is 1 it means not true 
                    bool = 0.00; // return 0 for false statement
                }
                else{
                    bool = 1.00; // return 1 for true statement
                }
                eval.push(bool);
            }
        
        } 
        
        return eval.pop();
    }


    public static void main(String[] args) throws FileNotFoundException{
        In in = new In(args[0]);
        PrintWriter write = new PrintWriter(args[1]); // name of file that it will create 
        String [] input = in.readAllLines();
        for(String i : input){
            if(evaluation(infixToPostfix(i)) == null){
                write.println("This is invalid!");
            } else {
                write.println(evaluation(infixToPostfix(i)));
            }
        }
        write.close();
  
        }
    }
