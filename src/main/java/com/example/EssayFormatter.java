package com.example;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
public class EssayFormatter {
    public static void main(String[] args) {
        Scanner userInput2 = new Scanner(System.in);
        String userConfirmation = "";
        List<String> stringlist;

        do {
            stringlist = createHeader();
            String header = String.join("\n", stringlist); 
            System.out.println("Your header will look like this: \n\n\n" + header);
            System.out.println("Does this header look correct? (Yes or No?)");
            userConfirmation = userInput2.nextLine();
        } while (userConfirmation.equalsIgnoreCase("No"));

        wordDoc(stringlist);

        userInput2.close();
    }

    public static List<String> createHeader() {
        Scanner userInput = new Scanner(System.in);
        String name;
        String professor;
        String className;
        String date;
        String title;

        List<String> stringlist = new ArrayList<>();

        System.out.println("Please submit name: ");
        name = userInput.nextLine();
        stringlist.add(name);

        System.out.println("Please submit professor name: ");
        professor = userInput.nextLine();
        stringlist.add(professor);

        System.out.println("Please submit class name: ");
        className = userInput.nextLine();
        stringlist.add(className);

        System.out.println("Would you like to use today's date? (Yes or No)");
        if (userInput.nextLine().equalsIgnoreCase("Yes")) {
            date = LocalDate.now().toString();
        } else {
            System.out.println("Please enter desired date:");
            date = userInput.nextLine();
        }
        stringlist.add(date);

        System.out.println("Please submit a title for your essay:");
        title = userInput.nextLine();
        stringlist.add(title);

        return stringlist;
    }

    public static void wordDoc(List<String> stringlist) {
            Scanner userInput3 = new Scanner(System.in);

            System.out.print("What would you like to name your file?:");
            String fileName = userInput3.nextLine();
            fileName = fileName + ".docx";

            XWPFDocument file = new XWPFDocument();
            XWPFParagraph content = file.createParagraph();
            XWPFRun run = content.createRun();


            for(String line : stringlist){
            run.setFontSize(12);
            run.setFontFamily("Times New Roman");
            content.setSpacingBetween(2);
            run.setText( line);
            run.addBreak();
            }

            System.out.print("Please enter your essay that you would like to be converted into MLA Format:");
            String essay= userInput3.nextLine();
            XWPFParagraph content2= file.createParagraph();
            XWPFRun run2 = content2.createRun();
            content2.setSpacingBetween(2);
            run2.setFontSize(12);
            run2.setFontFamily("Times New Roman");
            run2.setText(essay);
    

            try{
                FileOutputStream output = new FileOutputStream(fileName);
                file.write(output);
                output.close();

                System.out.println("File created successfully: " + fileName);
            }
            catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

}