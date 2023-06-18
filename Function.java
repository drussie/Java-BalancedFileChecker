import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;



public class Function {
	private Stack<Object> stk;//to store the left Delimiters
    private String expression;
    private int length;

    public Function(String expression) {
        stk = new Stack<Object>();
        this.expression = expression;
        this.length = expression.length();
    }

    //determine if the parenthesis are balanced - need to figure out multi line comments..
    boolean isBalanced() {
        int index = 0;
        boolean fail = false;
        try {
            while (index < length && !fail){ // read to the end of the list, unless EmptyStackException occurred
                char ch = expression.charAt(index);
//                char chNext = expression.charAt(index + 1);
// Can use either of/else or switch.
//                if (ch == (Delimeters.LEFT_CURLY_BRACES | Delimeters.LEFT_PARENTHESIS | Delimeters.LEFT_SQUARE_BRACKETS)) {
//                    stk.push(new Character(ch));
//                }
//                else if (ch == (Delimeters.RIGHT_CURLY_BRACES | Delimeters.RIGHT_PARENTHESIS | Delimeters.RIGHT_SQUARE_BRACKETS)) {
////                    stk.pop(new Character(ch));
//                    stk.pop(new Character(ch));
//                    stk.pop(new Character(chNext));
//                }
//                else if (ch == '/' && chNext == '*') {
//                    stk.push(new Character(ch));
//                }
//                else if (ch == '*' && chNext == '/') {
//                    stk.pop(new Character(ch));
//                }
                switch (ch) {
                    case Delimiters.LEFT_PARENTHESIS -> stk.push(new Character(ch));
                    case Delimiters.RIGHT_PARENTHESIS -> stk.pop(new Character(ch));
//                    case Delimeters.LEFT_PARENTHESIS:
//                    case Delimeters.LEFT_CURLY_BRACES:
//                    case Delimeters.LEFT_SQUARE_BRACKETS:
//                        stk.push(new Character(ch));
//                    case Delimeters.RIGHT_PARENTHESIS:
//                    case Delimeters.RIGHT_CURLY_BRACES:
//                    case Delimeters.RIGHT_SQUARE_BRACKETS:
//                        stk.pop(new Character(ch));

//                    case (Delimeters.FORWARD_SLASH):
//                        stk.push(new Character(ch));
//                        break;
//                    case (Delimeters.STAR):
//                        stk.pop();
//                        break;
                    default -> {
                    }
                }// end of switch
                index++;
            }//end of while
        }//end of try
        catch (EmptyStackException e) {
            System.out.println(e.toString());
            fail = true;
        }
        return (stk.empty() && !fail);
    }//end is balanced

    String convert2PostFix() {
        String postFix = "";
        Scanner scan = new Scanner(expression);
        char current;
        boolean fail = false;

        while (scan.hasNext() && !fail) {
            String token = scan.next();
            if (isNumber(token)) //bullet # 1
                postFix = postFix +token + Delimiters.A_SPACE;
            else  {
                current = token.charAt(0);
                if (current == Delimiters.LEFT_PARENTHESIS) //bullet # 2
                    stk.push(new Character(current));
                else if (current == Delimiters.RIGHT_PARENTHESIS) {// bullet # 3
                    try {
                        Character topmost = (Character) stk.pop();
                        char top = Character.valueOf(topmost); //Value on top of stack

                        while (top != Delimiters.LEFT_PARENTHESIS) {
                            postFix = postFix + top + Delimiters.A_SPACE;
                            top = (Character) stk.pop();
                        }
                    }
                    catch (EmptyStackException e) {
                        fail = true;
                    }
                }//End bullet # 2 and 3
                else if (isOperator(current)) {//bullet # 4
                    try {
                        char top = (Character) stk.peek();
                        boolean higher = hasHigherPrecedence(top, current);

                        while (top != Delimiters.LEFT_PARENTHESIS && higher) {
                            postFix = postFix + stk.pop() + Delimiters.A_SPACE;
                            top = (Character) stk.peek();
                        }
                        stk.push(new Character(current));
                    }
                    catch (EmptyStackException e) {
                        stk.push(new Character(current));
                    }
                }
            }
        }
        try {
            while (!stk.empty()) // bullet # 5
                postFix = postFix + stk.pop() + Delimiters.A_SPACE;
        }
        catch (EmptyStackException e) {
            e.printStackTrace();
        }
        return postFix;
    }// end convert 2 postfix

    boolean isNumber(String s) {
        boolean number = true;
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            number = false;
        }
        return number;
    }

    boolean isOperator(char ch) {
        boolean operator = switch (ch) {
            case Delimiters.MULTIPLICATION, Delimiters.DIVISION, Delimiters.ADDITION, Delimiters.SUBTRACTION -> true;
            default -> false;
        };
        return operator;
    }

    boolean hasHigherPrecedence(char top, char current) {
        boolean higher;

        switch (top) {
            case Delimiters.MULTIPLICATION:
            case Delimiters.DIVISION:
                switch (current) {
                    case Delimiters.ADDITION:
                    case Delimiters.SUBTRACTION:
                        higher = true;
                        break;
                    default:
                        higher = false;
                        break;
                }
                break;
            default:
                higher = false;
                break;
        }
        return higher;
    }

}
