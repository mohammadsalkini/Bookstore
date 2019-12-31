package fhtw.bsa1.projects.bookstore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class BookstoreApp {

    public static void main(String[] args) throws IOException {


        Scanner input = new Scanner(System.in);

        List<GoogleBooksAPI> googleBooksAPIS = new ArrayList<>();

        System.out.print("Enter query or -1 to quit: ");
        String query = input.nextLine();
        String author = "";

        boolean valid = true;
        if (query.equals("-1")) {
            valid = false;
        } else {
            System.out.print("Enter author or -1 to quit: ");
            author = input.nextLine();
        }

        while (valid) {
            if (!query.equals("") && author.equals("")) {
                GoogleBooksAPI booksAPI = new GoogleBooksAPI(query);
                googleBooksAPIS.add(booksAPI);
                printResult(booksAPI);

            } else {
                GoogleBooksAPI booksAPI = new GoogleBooksAPI(query, author);
                googleBooksAPIS.add(booksAPI);
                printResult(booksAPI);
            }

            System.out.println("\n\n==============================================================================================================" +
                    "=========================================================================================================================" +
                    "=========================================================================================================================" +
                    "=========================================================================================================================\n");

            System.out.print("Enter new query or -1 to quit: ");
            query = input.nextLine();

            if (query.equals("-1")) {
                valid = false;
            } else {
                System.out.print("Enter new author or -1 to quit: ");
                author = input.nextLine();
            }

        }


        System.out.print("Do you want to save the result to a file (Y/N)? ");
        String choice = input.nextLine();

        if (choice.startsWith("Y") || choice.startsWith("y")) {
            System.out.print("Enter file name: ");
            String fileName = input.nextLine();

            File file = new File(fileName + ".txt");
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(fileName + ".txt");
                for (GoogleBooksAPI googleBooksAPI: googleBooksAPIS) {
                    Set<Book> books = googleBooksAPI.getBooks();
                    Set<Magazine> magazines = googleBooksAPI.getMagazines();

                    fileWriter.write("\n");

                    fileWriter.write("query: " + googleBooksAPI.getQuery() + "\t\t\t\t\tauthor: " + googleBooksAPI.getAuthor() + "\n\n");
                    if (books.size() != 0) {
                        for (Book book : books
                        ) {
                            fileWriter.write(String.valueOf(book));
                            fileWriter.write("\n");
                        }
                    }

                    if (books.size() != 0 && magazines.size() != 0) {
                        fileWriter.write("\n");
                        fileWriter.write("----------------------------------------------------------------------------------" +
                                "---------------------------------------------------------------------------------------------" +
                                "---------------------------------------------------------------------------------------------" +
                                "---------------------------------------------------------------------------------------------");
                        fileWriter.write("\n");
                    }
                    fileWriter.write("\n");
                    if (magazines.size() != 0) {
                        for (Magazine magazine : magazines
                        ) {
                            fileWriter.write(String.valueOf(magazine));
                            fileWriter.write("\n");
                        }
                    }

                    if (googleBooksAPIS.size() > 1) {
                        fileWriter.write("\n\n==============================================================================================================" +
                                "=========================================================================================================================" +
                                "=========================================================================================================================" +
                                "=========================================================================================================================\n");
                    }
                }
                fileWriter.close();
            }

        }

    }


    public static void printResult(GoogleBooksAPI booksAPI) throws IOException {
        Set<Book> books = booksAPI.getBooks();
        Set<Magazine> magazines = booksAPI.getMagazines();

        System.out.println();

        if (books.size() != 0) {
            for (Book book : books
            ) {
                System.out.println(book);
            }
        }

        if (books.size() != 0 && magazines.size() != 0) {
            System.out.println("----------------------------------------------------------------------------------" +
                    "---------------------------------------------------------------------------------------------" +
                    "---------------------------------------------------------------------------------------------" +
                    "---------------------------------------------------------------------------------------------");
        }
        if (magazines.size() != 0) {
            for (Magazine magazine : magazines
            ) {
                System.out.println(magazine);
            }
        }
    }
}
