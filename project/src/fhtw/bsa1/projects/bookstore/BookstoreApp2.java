package fhtw.bsa1.projects.bookstore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fhtw.bsa1.projects.bookstore.BookstoreApp.printResult;

public class BookstoreApp2 {
    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
/**
 *
 */

        List<String> lines = Files.readAllLines(Paths.get("src/fhtw/bsa1/projects/res/text"));
        HashMap<String, String> queries = new HashMap<>();
        String query;
        String author;

        if (lines.size() != 0) {
            for (String line : lines) {
                String[] split = line.split(",");
                if (split.length == 2) {
                    queries.put(split[0], split[1]);
                } else {
                    queries.put(split[0], "");
                }
            }
        }

        for (Map.Entry<String, String> entry : queries.entrySet()) {

            query = String.valueOf(entry.getKey());
            author = String.valueOf(entry.getValue());

            if (!query.equals("") && author.equals("")) {
                GoogleBooksAPI booksAPI = new GoogleBooksAPI(query);

                printResult(booksAPI);

            } else {
                GoogleBooksAPI booksAPI = new GoogleBooksAPI(query, author);

                printResult(booksAPI);
            }

            System.out.println("\n\n==============================================================================================================" +
                    "=========================================================================================================================" +
                    "=========================================================================================================================" +
                    "=========================================================================================================================\n");

        }

    }
}
