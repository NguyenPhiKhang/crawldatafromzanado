package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.repositories.ProductRepository;
import com.khangse616.crawldatazanado.services.IProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;


    @Override
    public String createProduct(String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            Elements main = doc.select("div.main div#productleft");

            Elements detailText = main.select("div.blockhead div.detail-text");

            Elements productPriceView = detailText.select("div.product-priceview");

            String[] nameId = productPriceView.select("div.product-name h1").text().split(" SID");
            String name = nameId[0];

            int id = Integer.parseInt(nameId[1]);

            System.out.println("id: " + id + " - name: " + name);

            return name;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exitsProduct(int id) {
        return productRepository.existsProductByIdAndVisibilityTrue(id);
    }
}
