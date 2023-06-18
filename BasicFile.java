import javax.swing.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;



public class BasicFile {
	File f;
    JFileChooser fileSelect;
    File f2 = new File(".", "File Backup");

    BasicFile() {
        fileSelect = new JFileChooser(".");
    }

//    public void selectFile() {
//        int status = fileSelect.showOpenDialog(null);
//        try {
//            if (status != JFileChooser. APPROVE_OPTION) {
//                throw new IOException();
//            }
//            f = fileSelect.getSelectedFile();
//            if (!f.exists()) {
//                throw new FileNotFoundException();
//            }
//
//        }
//        catch (FileNotFoundException notFound) {
//            JOptionPane.showMessageDialog(null, "No File Found ", "Error", JOptionPane.INFORMATION_MESSAGE);
//        }
//        catch (IOException notFound) {
//            System.exit(0);
//        }
//    }

    void chooseFile()  {
        JFileChooser choose = new JFileChooser(".");
        int status = choose.showOpenDialog(null);

        try {
            if (status != JFileChooser.APPROVE_OPTION)
                throw new IOException();
            f = choose.getSelectedFile();

            if (!f.exists())
                throw new FileNotFoundException();

            display(f.getName(), "File has been chosen",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            display("File not found", e.toString(), JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            display("Approved option was not selected", e.toString(), JOptionPane.ERROR_MESSAGE);
        }
    }

    void fileSelecting() {
        int statusResult = fileSelect.showOpenDialog(null);

        try {
            if (statusResult != JFileChooser.APPROVE_OPTION) {
                throw new IOException();
            }
            f = fileSelect.getSelectedFile();
            if (!f.exists()) {
                throw new FileNotFoundException();
            }
        }

        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No File Found", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (IOException e) {
            System.exit(0);
        }
    }

    void fileBackup() throws FileNotFoundException {
        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            in = new DataInputStream(new FileInputStream(f));
            out = new DataOutputStream(new FileOutputStream(f2));
            try {
                while (true) {
                    byte data = in.readByte();
                    out.writeByte(data);
                }
            }
            catch (EOFException e) {
                JOptionPane.showMessageDialog(null, "File backup completed.",
                        "Complete", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(null, "File Not Found",
                        "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        finally {
            try {
                in.close();
                out.close();
            }

            catch (Exception e) {
                display(e.toString(), "Error");
            }
        }
    }

    void FileAttributes() throws IOException {

        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
            String line = "";

            System.out.println("The absolute path of the file is: " + f.getAbsolutePath()
                    + "\nThe files and directories in the path of the file are:" + f.getPath()
                    + "\nThe file size is: " + f.getTotalSpace() + "kb");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LineNumberReader lnr = null;

        try {
            lnr = new LineNumberReader(new FileReader(f));
            String line = "";
            int lineCount = 0;

            while ((line = lnr.readLine()) != null)
                lineCount++;
            System.out.println("\nThe number of lines in the file are: " + lineCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileReader() throws IOException {
        FileReader fileRead = new FileReader (f);
        char arr[] = new char[ (int) f.length()];

        fileRead.read(arr);

        for (char c : arr) {
            System.out.print (c);
            fileRead.close();
        }
    }

    void display(String msg, String s, int t) {
        JOptionPane.showMessageDialog(null, msg, s, t);
    }

    void display(String msg, String s) {
        JOptionPane.showMessageDialog(null, msg, s, JOptionPane.ERROR_MESSAGE);
    }

}
