import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Microwave {
    String html;

    public Microwave(String html_ovens) throws IOException {
        html = html_ovens;
    }

    public Document document(String pg) throws IOException {
        Document doc = Jsoup.connect(pg).get();
        return doc;
    }

    public int parseCategory() throws IOException {
        int ct = 0;
        Microwave html_d = new Microwave(html);
        Document html_doc = html_d.document(html);
        Elements nums = html_doc.select("a.paginator-catalog-l-link");
        Element n = nums.last();
        ArrayList<Integer> pages = new ArrayList<Integer>();
        for (Element num : nums) {
            try {
                Integer text = Integer.parseInt(num.ownText());
                pages.add(text);
            } catch (NumberFormatException nfe) {
                ct++;
            }
        }
        int numOfPages = pages.get(pages.size() - 1);
        // ArrayList <String> result = new ArrayList<String>();
        for (int pageNum = 0; pageNum < numOfPages; pageNum++) {
            String pg = html + "page=" + Integer.toString(pageNum + 1);
            this.parseCategoryPage(pg);
        }
        return 0;
    }


    public int parseCategoryPage(String pg) throws IOException {
        //pg = url = https://bt.rozetka.com.ua/microwaves/c80162/filter/page=1
        Document doc = Jsoup.connect(pg).get();
        Elements tiles = doc.select("div.g-i-tile-i-title");

        for (Element tile : tiles) {
            Elements links = tile.select("a");
            String link = links.attr("href") + "comments/";
            this.parseReviews(link);

        }

        return 0;
    }

    public int parseReviews(String link) throws IOException {
        //https://bt.rozetka.com.ua/bosch_bfr_634_gs1/p3249011/comments/
        int ct = 0;
        ArrayList<Integer> arr = new ArrayList<Integer>();
        Document doc = Jsoup.connect(link).get();
        Elements nums = doc.select("a.paginator-catalog-l-link");
        for (Element num : nums) {
            try {
                String n = num.ownText();
                Integer nn = Integer.parseInt(n);
                arr.add(nn);
            } catch (NumberFormatException e) {
                ct++;
            }
        }
        int numReviews;
        ArrayList<Integer> sentiments = new ArrayList<Integer>();
        if (arr.size() != 0) {
            numReviews = arr.get(arr.size() - 1);
            for (int i = 0; i < numReviews; i++) {
                String pg = link + "page=" + Integer.toString(i + 1);
                sentiments.add(this.parseReviewsPage(pg));
               /* Document docc = Jsoup.connect(pg).get();
                Elements tiles = docc.select("div.g-i-tile-i-title");

                for (Element tile : tiles) {
                    Elements links = tile.select("a");
                    for (Element linkk : links) {
                        String name = linkk.ownText();
                        System.out.println(name);
                    }*/
                // String filename = "data/" + link.split("/", 4) + ".csv";


            }

        }


        return 0;
    }


    public int parseReviewsPage(String pg) throws IOException {
        Document docc = Jsoup.connect(pg).get();
        Elements reviews = docc.select("article.pp-review-i");
        ArrayList<String> sentiments = new ArrayList<String>();
        for (Element review : reviews) {
            Elements star = review.select("span.g-rating-stars-i");
            Elements text = review.select("div.pp-review-text");
            Elements texts = text.select("div.pp-review-text-i");
            ArrayList<>
            sentiments.add()
            System.out.println(texts);
        }
        return  0;
    }











    public static void main(String[] args) throws IOException {
        File folder = new File("C:/Users/Martoshka/lab1/src/Microwave/java/data");
        boolean folderExists = folder.exists();
        if (!folderExists) {
            folderExists = folder.mkdirs();
        }
        Microwave ex = new Microwave("https://bt.rozetka.com.ua/microwaves/c80162/filter/");
        System.out.println(ex.parseCategory());
    }
}
