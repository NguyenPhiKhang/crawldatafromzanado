package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.models.Category;
import com.khangse616.crawldatazanado.repositories.ProductRepository;
import com.khangse616.crawldatazanado.services.ICategoryService;
import com.khangse616.crawldatazanado.services.IProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ICategoryService categoryService;


    @Override
    public String createProduct(String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            Elements main = doc.select("div.main div#productleft");

            //  find id and name product
            Elements detailText = main.select("div.blockhead div.detail-text");

            Elements productPriceView = detailText.select("div.product-priceview");

            String[] nameId = productPriceView.select("div.product-name h1").text().split(" SID");
            String name = nameId[0];

            int id = Integer.parseInt(nameId[1]);

            if (exitsProduct(id))
                return "Exist Product";

            // save category
            Elements topbar = doc.select("div.top-bar > div.breadcrumbs > ul > li");

            Map<Integer, String> mapCategories = new HashMap<>();

            int idCategoryParent = -1;

            for (int i = 1; i < topbar.size(); i++) {
                Element li = topbar.get(i);

                int idCategory = Integer.parseInt(li.attr("class").split(" ")[0].replace("category", ""));
                String nameCategory = li.select("span").text();

                Category category = new Category();
                category.setId(idCategory);
                category.setLevel(i-1);
                category.setName(nameCategory);
                if(i!=1)
                    category.setParentCategory(categoryService.findCategoryById(idCategoryParent));
                // check exists category
                if(!categoryService.existCategory(idCategory)){
                    categoryService.save(category);
                }

                idCategoryParent = idCategory;

                mapCategories.put(idCategory, nameCategory);
            }

//            System.out.println("id: " + id + " - name: " + name);

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
