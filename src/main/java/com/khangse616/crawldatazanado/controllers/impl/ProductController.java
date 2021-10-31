package com.khangse616.crawldatazanado.controllers.impl;

import com.khangse616.crawldatazanado.controllers.IProductController;
import com.khangse616.crawldatazanado.models.DTO.InputCreateProductDTO;
import com.khangse616.crawldatazanado.services.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ProductController implements IProductController {

    @Autowired
    private ProductService productService;

    @Override
    @PostMapping("/create-product")
    public String createProduct(@RequestBody InputCreateProductDTO input) {
//        String url = "https://phongvu.vn/search?router=productListing&attributeSets=1&brands=acer,asus,dell,hp,lenovo,msi&attributes.laptop_seriescpu=208,209,6814,10428,10429&attributes.laptop_bonhofilter=671,670,672,673&attributes.laptop_chipdohoaroi=753,754,757,6819,6879,10419,11990,10420,10421,10422,10525,10759,10526,6882,12047,12048&attributes.laptop_kichthuocmanhinh=701,698,699,6817&_sort=bestPrice&_order=asc";
        return productService.createProduct(input);
    }
}
