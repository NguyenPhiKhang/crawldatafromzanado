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

        String url = "https://www.zanado.com/bo-do-the-thao-nang-dong-colo-cl1703339-dep-gia-tot-giam-30-sid65674.html?color=77";

        try {
            Document doc = Jsoup.connect(url).get();

            Elements topbar = doc.select("div.top-bar > div.breadcrumbs > ul > li");

            Map<Integer, String> mapCategories = new HashMap<>();

            for(int i = 1; i<topbar.size();i++){
                Element li = topbar.get(i);

                int idCategory = Integer.parseInt(li.attr("class").split(" ")[0].replace("category", ""));
                String nameCategory = li.select("span").text();

                mapCategories.put(idCategory, nameCategory);
            }


            Elements main = doc.select("div.main div#productleft");

            Elements detailText = main.select("div.blockhead div.detail-text");

            Elements productPriceView = detailText.select("div.product-priceview");

            String[] nameId = productPriceView.select("div.product-name h1").text().split(" SID");
            String name = nameId[0];
            int id = Integer.parseInt(nameId[1]);

            System.out.println("id: " + id + " - name: " + name);

            String shortDescription = productPriceView.select("div.product-description").text();
            System.out.println("shortDescription: " + shortDescription);

            String textDiscount = productPriceView.select("div.sprites.discountpercent").text().replace("%", "");
            int discountPercent = textDiscount.equals("") ? 0 : Math.abs(Integer.parseInt(textDiscount));
            System.out.println("discountPercent: " + discountPercent);

            int price = Integer.parseInt(productPriceView.select("div.pricespecial").text().replaceAll("[^0-9]", ""));
            System.out.println("price: " + price);

            Elements allAttributes = detailText.select("div.add-to-box div.product-attributeconf");

            allAttributes.forEach(e -> {
                Elements attributes = e.select("div.attributeconf-text > ul");
                String[] idNameAttr = attributes.attr("id").split("-");
                int idAttr = Integer.parseInt(idNameAttr[2]);
                String nameAttr = idNameAttr[1];
                System.out.println("id: " + idAttr + " - name: " + nameAttr);

                Elements options = attributes.select("> li");

                if (nameAttr.equals("color")) {
                    options.forEach(o-> {
                        int idOp = Integer.parseInt(o.select("input").attr("value"));
                        String nameOp = o.select("img").attr("title");
                        System.out.println("id: " + idOp + " - name: " + nameOp);
                    });
                } else {
                    options.forEach(o -> {
                        int idOp = Integer.parseInt(o.select("input").attr("value"));
                        String nameOp = o.select("label").text();
                        System.out.println("id: " + idOp + " - name: " + nameOp);
                    });
                }
            });

            Elements liImg = main.select("div.blockhead div.detail-imgproduct ul.thumb-detail > li");
            liImg.forEach(li->{
                Elements img = li.select("img");
                String title = img.attr("title");
                String link_img = "http:"+img.attr("src").replaceFirst("\\d{3}+x\\d{3}", "fill_size");

                System.out.println(title);
                System.out.println(link_img);
            });

            String highlight = main.select("div.block-description div.overview").html();
            String description = main.select("div.block-description > p").outerHtml();

            Elements attribute_products = main.select("div.block-description div.product-attributes div.product-attribute");
            attribute_products.forEach(attr->{
                String title = attr.select("div.attribute-title").text().toLowerCase();
                if(title.equals("thương hiệu")){
                    int idBrand = Integer.parseInt(attr.select("a").attr("href").replaceAll("\\D", ""));
                    String nameBrand = attr.select("strong").text();
                    System.out.println("id brand: "+ idBrand+" - name: "+ nameBrand);
                }else{
                    if(title.equals("chất liệu")){
                        String material = attr.select("div.attribute-text").text();
                        System.out.println(material);
                    }else{
                        if(title.equals("kiểu dáng")){
                            String style = attr.select("div.attribute-text").text();
                            System.out.println(style);
                        }else{
                            if(title.equals("mục đích sd")){
                                String style = attr.select("div.attribute-text").text();
                                System.out.println(style);
                            }else{
                                if(title.equals("mùa phù hợp")){
                                    String season = attr.select("div.attribute-text").text();
                                    System.out.println(season);
                                }else{
                                    if(title.equals("xuất xứ")){
                                        String madein = attr.select("div.attribute-text").text();
                                        System.out.println(madein);
                                    }
                                }
                            }
                        }
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
