package com.khangse616.crawldatazanado;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class CrawldatazanadoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawldatazanadoApplication.class, args);
        try {
            Document doc = Jsoup.connect("https://www.zanado.com/bo-do-the-thao-nang-dong-colo-cl1703339-dep-gia-tot-giam-30-sid65674.html?color=77").get();

//            Elements topbar = doc.select("div.top-bar > div.breadcrumbs > ul > li");
//
//            Map<Integer, String> mapCategories = new HashMap<>();
//
//            for(int i = 1; i<topbar.size();i++){
//                Element li = topbar.get(i);
//
//                int idCategory = Integer.parseInt(li.attr("class").split(" ")[0].replace("category", ""));
//                String nameCategory = li.select("span").text();
//
//                mapCategories.put(idCategory, nameCategory);
//            }


            Elements main = doc.select("div.main div#productleft");

            Elements productPriceView = main.select("div.blockhead div.detail-text div.product-priceview");

            String[] nameId = productPriceView.select("div.product-name h1").text().split(" SID");
            String name = nameId[0];
            int id = Integer.parseInt(nameId[1]);

            System.out.println("id: "+id+" - name: "+name);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
