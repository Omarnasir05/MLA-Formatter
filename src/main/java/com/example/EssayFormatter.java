package com.example;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
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


        wordDoc(stringlist, stringlist.get(4));

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

    public static void wordDoc(List<String> stringlist, String title) {
            PrintWriter printer = null;
            Scanner userInput3 = new Scanner(System.in);

            stringlist.remove(4);

            System.out.print("What would you like to name your file?:");
            String fileName = userInput3.nextLine();
            fileName = fileName + ".docx";

            XWPFDocument file = new XWPFDocument();
            XWPFParagraph content = file.createParagraph();
            XWPFRun run = content.createRun();

            

            System.out.print("Please enter the path of your essay file:");
            try{
                FileOutputStream output = new FileOutputStream(fileName);
                run.setFontSize(12);
                run.setFontFamily("Times New Roman");
                content.setSpacingBetween(2);

                for(String line : stringlist){
                    run.setText(line);
                    if(!stringlist.get(3).equalsIgnoreCase(line))
                        run.addBreak();
                }
                //file.write(output);

                XWPFParagraph titleParagraph = file.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleParagraph.setSpacingBetween(2);
                titleRun.setFontSize(12); 
                titleRun.setFontFamily("Times New Roman");
                titleRun.setText(title);
        
                //content.setAlignment(ParagraphAlignment.CENTER);
                //run.setText(title);
                //run.addBreak();
                //file.write(output);
                //content.setAlignment(ParagraphAlignment.LEFT);


                File usersFile = new File(userInput3.nextLine());
                userInput3.close();
                Scanner fileReader = new Scanner(usersFile);
                FileWriter fw = new FileWriter(fileName);
                printer = new PrintWriter(fw);
                String w = fileReader.nextLine();

              
                XWPFParagraph bodyParagraph = file.createParagraph();
                bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun bodyRun = bodyParagraph.createRun();
                bodyParagraph.setSpacingBetween(2);
                bodyRun.setFontSize(12); 
                bodyRun.setFontFamily("Times New Roman");
                bodyRun.setText(w);

                printer.close();
                fileReader.close();
               

                
                file.write(output);
                output.close();
            }
            catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }


            System.out.println("File created successfully: " + fileName);
    }

}