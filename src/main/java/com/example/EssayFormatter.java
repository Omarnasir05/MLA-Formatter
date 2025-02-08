package com.example;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

        wordDocWithReferences(stringlist, stringlist.get(4));
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

        userInput.close();

        return stringlist;
    }

    public static void wordDocWithReferences(List<String> stringlist, String title) {
        Scanner userInput3 = new Scanner(System.in);

        stringlist.remove(4);

        System.out.print("What would you like to name your file?: ");
        String fileName = userInput3.nextLine();
        fileName = fileName + ".docx";

        try (XWPFDocument file = new XWPFDocument();
             FileOutputStream output = new FileOutputStream(fileName)) {

            // Add header content
            XWPFParagraph content = file.createParagraph();
            content.setSpacingBetween(2);

            for (String line : stringlist) {
                XWPFRun run = content.createRun();
                run.setFontSize(12);
                run.setFontFamily("Times New Roman");
                run.setText(line);
                run.addBreak();
            }

            // Add title
            XWPFParagraph titleParagraph = file.createParagraph();
            titleParagraph.setSpacingBetween(2);
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setFontSize(12);
            titleRun.setFontFamily("Times New Roman");
            titleRun.setText(title);

            System.out.print("Please enter the path of your essay file to copy from: ");
            String sourcePath = userInput3.nextLine();

            // Copy content from an existing file
            try (BufferedReader reader = new BufferedReader(new FileReader(sourcePath))) {
            XWPFParagraph bodyParagraph = file.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
            bodyParagraph.setFirstLineIndent(700);
            bodyParagraph.setSpacingBetween(2);

            String line;
            while ((line = reader.readLine()) != null) {
                XWPFRun bodyRun = bodyParagraph.createRun();
                bodyRun.setFontSize(12);
                bodyRun.setFontFamily("Times New Roman");
                bodyRun.setText(line);
                bodyRun.addBreak();
            // Add references
            List<String> citationlist = new ArrayList<>();
            String answer;
            
            System.out.print("Would you like to add citations?(Yes or No):");
            answer = userInput3.nextLine();

            while((answer.equalsIgnoreCase("Yes"))){
                System.out.println("Please enter the author's name of your reference (Last name, first name.):");
                String author = userInput3.nextLine();
                citationlist.add(author);
        
                System.out.println("Please enter the title of your website:");
                String websiteTitle = userInput3.nextLine();
                citationlist.add(websiteTitle);
        
                System.out.println("Please enter the publisher of your website:");
                String publisher = userInput3.nextLine();
                citationlist.add(publisher);
        
                System.out.println("Please enter the publish date of your reference (mm/dd/yyyy):");
                String publishDate = userInput3.nextLine();
                citationlist.add(publishDate);
        
                System.out.println("Please enter the link to your reference:");
                String link = userInput3.nextLine();
                citationlist.add(link);

                System.out.println("Would you like to add another citation? (Yes or No):");
                answer = userInput3.nextLine();

                if(answer.equals("No") || answer.equals("no")){
                    break;
                }
            }


            if (!citationlist.isEmpty()) {
                XWPFParagraph referencesParagraph = file.createParagraph();
                referencesParagraph.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun referencesRun = referencesParagraph.createRun();
                referencesRun.setFontSize(12);
                referencesRun.setFontFamily("Times New Roman");
                referencesRun.setText("References:");
                referencesRun.addBreak();

                for (String citation : citationlist) {
                    int count = 0;
                    XWPFParagraph paragraph = file.createParagraph();
                    XWPFRun citationRun = referencesParagraph.createRun();
                    paragraph.setSpacingBetween(2);
                    paragraph.setAlignment(ParagraphAlignment.LEFT);

                    citationRun.setFontSize(12);
                    citationRun.setFontFamily("Times New Roman");
                    citationRun.setText(citation);
                    count += 1;
                    if(count != 3){
                        citationRun.setText(",");
                    }
                }

            }
            
                
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the source file.");
                e.printStackTrace();
            }

            file.write(output);
            System.out.println("File created successfully: " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }

        userInput3.close();
    }
}
