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

    private String price;

    public static void main(String[] args) {
        SpringApplication.run(CrawldatazanadoApplication.class, args);
        try {
//            Document doc = Jsoup.connect("https://www.zanado.com/bo-do-the-thao-nang-dong-colo-cl1703339-dep-gia-tot-giam-30-sid65674.html?color=77").get();
            Document doc = Jsoup.connect("https://www.zanado.com/nuoc-hoa-nu-laurelle-london-sexxy-shoo-silver-eau-de-parfum-100ml-dep-gia-tot-sid66366.html?pth=0f/0f006a5adc8edb95e81d25a4dfdb5c2e").get();

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

//            Elements productPriceView = main.select("div.blockhead div.detail-text div.product-priceview");
//
//            String[] nameId = productPriceView.select("div.product-name h1").text().split(" SID");
//            String name = nameId[0];
//            int id = Integer.parseInt(nameId[1]);
//
//            System.out.println("id: " + id + " - name: " + name);
//
//            String shortDescription = productPriceView.select("div.product-description").text();
//            System.out.println("shortDescription: " + shortDescription);
//
//            String textDiscount = productPriceView.select("div.sprites.discountpercent").text().replace("%", "");
//            int discountPercent = textDiscount.equals("") ? 0 : Math.abs(Integer.parseInt(textDiscount));
//            System.out.println("discountPercent: " + discountPercent);
//
//            int price = Integer.parseInt(productPriceView.select("div.pricespecial").text().replaceAll("[^0-9]", ""));
//            System.out.println("price: " + price);

            Elements allAttributes = main.select("div.blockhead div.detail-text div.add-to-box div.product-attributeconf");

            allAttributes.forEach(e->{
                Elements attributes = e.select("div.attributeconf-text > ul");
                String[] idNameAttr = attributes.attr("id").split("-");
                int idAttr = Integer.parseInt(idNameAttr[2]);
                String nameAttr = idNameAttr[1];
                System.out.println("id: "+ idAttr+" - name: "+nameAttr);

                Elements options = attributes.select("> li");

                if(nameAttr.equals("color")){
                    options.forEach(o->{
                        int idOp = Integer.parseInt(o.select("input").attr("value"));
                        String nameOp = o.select("img").attr("title");
                        System.out.println("id: "+ idOp+" - name: "+nameOp);


                    });
                }else{
                    options.forEach(o->{
                        int idOp = Integer.parseInt(o.select("input").attr("value"));
                        String nameOp = o.select("label").text();
                        System.out.println("id: "+ idOp+" - name: "+nameOp);
                    });
                }
            });




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
