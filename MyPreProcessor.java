import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.*;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;



public class MyPreProcessor {
	public static void main(String[] args) throws IOException {
        boolean done = false;
        String menu = "Select a file to process\n1. Select a file\n7. Exit";

        while (!done) {
            BasicFile f = new BasicFile();
            String s = JOptionPane.showInputDialog(menu);
 // try catch block?
                int i = Integer.parseInt(s);
                switch (i) {
                case 1 -> f.chooseFile();
                case 7 -> done = true;
                }

            }
// EO try catch block?

// look to initialize Data structure of some type to store the contents of the file -bufferedReader or Line Number reader are suggested.
        String[] s = {"5 + ) * 4 + ( 2",
                "10 + 30 * 5",
                "( 20 + 30 ) * 50",
                "( 50 + 100 ) / ( ( 15 - 20 ) + 25 )",
                "( 30 * ( 100 - 4 ) + ( 20 / 20 - 5 ) + 65"};


        for (int i = 0; i < s.length; i++) {// need to add BasicFile to choose file and assign instead of 's'
            Basicfile a = new BasicFile(s[i]);
            if (a.isBalanced()) {
                System.out.println(s[i] + " is valid - with respect to parenthesis\n");
                System.out.println("The postfix string is " + a.convert2PostFix() + "\n");
            } else
                System.out.println(s[i] + " is invalid - with respect to parenthesis\n");
        }
    }

    static void displayInfo(String button, String info) {
        JTextArea text = new JTextArea(button, 7, 20);
        JScrollPane pane = new JScrollPane(text);
        JOptionPane.showMessageDialog(null, pane, info, JOptionPane.INFORMATION_MESSAGE);
    }
}
