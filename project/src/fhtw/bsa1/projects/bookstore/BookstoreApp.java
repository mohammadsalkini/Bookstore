package fhtw.bsa1.projects.bookstore;

import java.io.*;
import java.net.URL;
import java.util.*;

public class BookstoreApp {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        /**
         *Create an object from the Class Scanner , named input
         */
        Scanner input = new Scanner(System.in);

        /**
         * make a new list from googleBooksAPIS
         * (new arrayList is implement from List interface)
         */
        List<GoogleBooksAPI> googleBooksAPIS = new ArrayList<>();

        /** to scan the value of query "that we want to write" */
        System.out.print("Enter query or -1 to quit: ");
        String query = input.nextLine();
        String author = "";


        boolean valid = true;
        if (query.equals("-1")) {
            valid = false;
        } else {
            System.out.print("Do you want to enter an author: (Y/N): ");
            String value = input.nextLine();
            if (value.equals("Y") || value.equals("y")) {
                System.out.print("enter an author: ");
                author = input.nextLine();
            }
        }

        /**while the input is available..
         *  1) make a new object (that need just as input query to make the search)  */
        while (valid) {
            if (!query.equals("") && author.equals("")) {
                GoogleBooksAPI booksAPI = new GoogleBooksAPI(query);
                query = "";
                googleBooksAPIS.add(booksAPI);
                printResult(booksAPI);

                /**
                 *while the input is available..
                 *  ) make a new object (that need just as input query & author to make the search)*/
            } else {
                GoogleBooksAPI booksAPI = new GoogleBooksAPI(query, author);
                author = "";
                query = "";
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
                System.out.print("Do you want to enter an author: (Y/N): ");
                String value = input.nextLine();
                if (value.equals("Y") || value.equals("y")) {
                    System.out.print("enter an author: ");
                    author = input.nextLine();
                }

            }

        }

        /**
         * the user have a choice to save the result to a specify a file
         */
        System.out.print("Do you want to save the result to a file (Y/N)? ");
        String choice = input.nextLine();

        if (choice.startsWith("Y") || choice.startsWith("y")) {
            System.out.print("Enter file name: ");
            String fileName = input.nextLine();

            File file = new File(fileName + ".txt");
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(fileName + ".txt");
                for (GoogleBooksAPI googleBooksAPI : googleBooksAPIS) {
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


        System.out.print("Do you want to count the words in the descriptions:(Y/N): ");

        String value = input.nextLine();

        if (value.startsWith("Y") || value.startsWith("y")) {
            HashMap<Magazine, Integer> magazineIntegerHashMap = calculateDescriptionWords(googleBooksAPIS);
            if (magazineIntegerHashMap.size() == 0) {
                System.out.println("The query result contains no magazines ):");

            } else {
                System.out.print("Do you want to save the result in a file:(Y/N): ");
                value = input.nextLine();

                if (value.startsWith("Y") || value.startsWith("y")) {
                    System.out.print("Enter file name: ");
                    String fileName = input.nextLine();

                    File file2 = new File(fileName + ".txt");
                    if (file2.createNewFile()) {

                        FileWriter fileWriter2 = new FileWriter(fileName + ".txt");
                        fileWriter2.write("\n");

                        for (Map.Entry<Magazine, Integer> entry : magazineIntegerHashMap.entrySet()) {

                            fileWriter2.write("Magazine: " + "title: " + entry.getKey().getTitle() + ", publishedDate: " + entry.getKey().getPublishedDate() + ", words count: " + entry.getValue());

                            if (magazineIntegerHashMap.size() > 1) {
                                fileWriter2.write("\n\n==============================================================================================================" +
                                        "=========================================================================================================================" +
                                        "=========================================================================================================================" +
                                        "=========================================================================================================================\n");
                            }
                        }

                        fileWriter2.close();

                    }
                }
            }

        }

        System.out.print("Do you want to save a thumbnail of any of the last result?(Y/N): ");
        value = input.nextLine();

        if (value.startsWith("Y") || value.startsWith("y")) {
            GoogleBooksAPI lastApi = googleBooksAPIS.get(googleBooksAPIS.size() - 1);

            List<String> thumbnails = new ArrayList<>();
            List<String> thumbnailsFiles = new ArrayList<>();
            if (lastApi.getBooks().size() == 0) {
                System.out.println("The query result contains no books ):");
            } else {
                printResult(lastApi);
                System.out.println();
                String id = "";
                do {
                    System.out.print("Enter the id of the book that you want to get its thumbnail or -1 to quit: ");
                    id = input.nextLine();
                    if (!id.equals("-1")) {
                        String thumbnail = "";

                        for (Book book : lastApi.getBooks()) {
                            if (book.getId().equals(id)) {
                                thumbnail = book.getLink();
                            }
                        }

                        if (thumbnail.equals("")) {
                            System.out.println("ID not found");
                        } else {
                            thumbnails.add(thumbnail);
                            System.out.print("Enter the file name: ");
                            String thumbnailFile = input.nextLine();
                            thumbnailsFiles.add(thumbnailFile);

                        }
                    }
                } while (!id.equals("-1"));

            }

            if (thumbnails != null && thumbnails.size() != 0) {

                for (int index = 0; index < thumbnails.size(); index++) {
                    URL url = new URL(thumbnails.get(index));

                    InputStream in = new BufferedInputStream(url.openStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    byte[] response = out.toByteArray();
                    FileOutputStream fos = new FileOutputStream("src/fhtw/bsa1/projects/thumbnails/" + thumbnailsFiles.get(index) + ".jpg");
                    fos.write(response);
                    fos.close();

                }
            }


            System.out.println("Thank you! Bye Bye  :)");
        } else {
            System.out.println("Thank you! Bye Bye  :)");
        }


    }

    private static HashMap<Magazine, Integer> calculateDescriptionWords(List<GoogleBooksAPI> googleBooksAPIS) throws IOException {

        HashMap<Magazine, Integer> descriptionCount = new HashMap<Magazine, Integer>();

        for (GoogleBooksAPI api : googleBooksAPIS) {

            Set<Magazine> magazines = api.getMagazines();
            int wordCount = 0;
            if (magazines.size() != 0) {
                for (Magazine magazine : magazines) {
                    String description = magazine.getDescription();
                    if (!description.equals("")) {


                        boolean word = false;
                        int endOfLine = description.length() - 1;

                        for (int i = 0; i < description.length(); i++) {
                            // if the char is a letter, word = true.
                            if (Character.isLetter(description.charAt(i)) && i != endOfLine) {
                                word = true;
                                // if char isn't a letter and there have been letters before,
                                // counter goes up.
                            } else if (!Character.isLetter(description.charAt(i)) && word) {
                                wordCount++;
                                word = false;
                                // last word of String; if it doesn't end with a non letter, it
                                // wouldn't count without this.
                            } else if (Character.isLetter(description.charAt(i)) && i == endOfLine) {
                                wordCount++;
                            }
                        }

                        descriptionCount.put(magazine, wordCount);

                    }

                    System.out.println();

                    System.out.println("Magazine: " + "title: " + magazine.getTitle() + ", publishedDate: " + magazine.getPublishedDate() + ", words count: " + wordCount);
                    if (magazines.size() != 0) {
                        System.out.println("----------------------------------------------------------------------------------" +
                                "---------------------------------------------------------------------------------------------" +
                                "---------------------------------------------------------------------------------------------" +
                                "---------------------------------------------------------------------------------------------");
                    }
                }
            }
        }
        return descriptionCount;
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