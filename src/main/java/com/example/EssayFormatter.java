package com.example;

import java.io.FileOutputStream;
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
        Scanner userInput = new Scanner(System.in);
        String userConfirmation;
        List<String> header;

        // Loop to create the header
        do {
            header = createHeader(userInput);
            System.out.println("Your header will look like this:\n\n" + String.join("\n", header));
            System.out.println("Does this header look correct? (Yes or No?)");
            userConfirmation = userInput.nextLine();
        } while (userConfirmation.equalsIgnoreCase("No"));

        // Create Word Document
        wordDoc(header, header.get(4), userInput);

        // Add References
        references(userInput);

        // Do not close userInput (System.in scanner)
    }

    public static List<String> createHeader(Scanner userInput) {
        List<String> header = new ArrayList<>();

        System.out.println("Please submit name:");
        header.add(userInput.nextLine());

        System.out.println("Please submit professor name:");
        header.add(userInput.nextLine());

        System.out.println("Please submit class name:");
        header.add(userInput.nextLine());

        System.out.println("Would you like to use today's date? (Yes or No)");
        if (userInput.nextLine().equalsIgnoreCase("Yes")) {
            header.add(LocalDate.now().toString());
        } else {
            System.out.println("Please enter desired date:");
            header.add(userInput.nextLine());
        }

        System.out.println("Please submit a title for your essay:");
        header.add(userInput.nextLine());

        return header;
    }

    public static void wordDoc(List<String> header, String title, Scanner userInput) {
        System.out.println("What would you like to name your file?");
        String fileName = userInput.nextLine() + ".docx";

        try (XWPFDocument doc = new XWPFDocument();
             FileOutputStream output = new FileOutputStream(fileName)) {

            // Create Header
            for (String line : header) {
                XWPFParagraph paragraph = doc.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun run = paragraph.createRun();
                run.setFontSize(12);
                run.setFontFamily("Times New Roman");
                run.setText(line);
            }

            // Create Title
            XWPFParagraph titleParagraph = doc.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setFontSize(12);
            titleRun.setFontFamily("Times New Roman");
            titleRun.setText(title);

            // Save Document
            doc.write(output);
            System.out.println("File created successfully: " + fileName);

        } catch (IOException e) {
            System.out.println("An error occurred while writing the file.");
            e.printStackTrace();
        }
    }

    public static void references(Scanner userInput) {
        List<String> citations = new ArrayList<>();
        String answer;

        do {
            System.out.println("Would you like to add citations? (Yes or No?):");
            answer = userInput.nextLine();

            if (answer.equalsIgnoreCase("Yes")) {
                System.out.println("Please enter the author's name (Last, First):");
                String author = userInput.nextLine();

                System.out.println("Please enter the website title:");
                String websiteTitle = userInput.nextLine();

                System.out.println("Please enter the publisher:");
                String publisher = userInput.nextLine();

                System.out.println("Please enter the publish date (MM/DD/YYYY):");
                String date = userInput.nextLine();

                System.out.println("Please enter the URL:");
                String url = userInput.nextLine();

                // Format and add citation
                String citation = author + ". " + websiteTitle + ". " + publisher + ", " + date + ". " + url;
                citations.add(citation);
            }
        } while (answer.equalsIgnoreCase("Yes"));

        System.out.println("\nCitations:");
        for (String citation : citations) {
            System.out.println(citation);
        }
    }
}