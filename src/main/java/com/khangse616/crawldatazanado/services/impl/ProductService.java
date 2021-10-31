package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.Utils.ImageUtil;
import com.khangse616.crawldatazanado.Utils.StringUtil;
import com.khangse616.crawldatazanado.models.Attribute;
import com.khangse616.crawldatazanado.models.Brand;
import com.khangse616.crawldatazanado.models.DTO.InputCreateProductDTO;
import com.khangse616.crawldatazanado.models.DataImage;
import com.khangse616.crawldatazanado.models.Product;
import com.khangse616.crawldatazanado.repositories.*;
import com.khangse616.crawldatazanado.services.IBrandService;
import com.khangse616.crawldatazanado.services.IProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ProductService implements IProductService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private DataImageRepository dataImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public String createProduct(InputCreateProductDTO input) {
        try {
            Document doc = Jsoup.connect(input.getUrl()).get();

//            List<Product> products = new ArrayList<>();

            File file = new File("C:\\selenium\\chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            WebDriver webDriver = new ChromeDriver();

            webDriver.get(input.getUrl());
            List<WebElement> list = webDriver.findElements(By.cssSelector("div.css-rtde4j div.css-1xhjahy"));
            list.addAll(webDriver.findElements(By.cssSelector("div.css-rtde4j div.css-ym7o0e")));

//            Elements a = doc.select("div.css-rtde4j div.css-1xhjahy");
//            a.addAll(doc.select("div.css-rtde4j div.css-ym7o0e"));

            list.forEach(v -> {
                String u = v.findElement(By.tagName("a")).getAttribute("href");

                System.out.println(u);
                try {
                    Document dox = Jsoup.connect(u).get();

                    String sku = dox.select("div.css-6b3ezu div div div").get(0).text().split("SKU: ")[1];

                    Product productExist = productRepository.findById(Integer.parseInt(sku)).orElse(null);

                    if (productExist == null) {

                        Product product = new Product();
                        String name = dox.select("h1.css-4kh4rf").text().split(" \\(")[0];
                        BigDecimal price;
                        int percent = 0;

                        try{
                            String priceElement = v.findElement(By.cssSelector("div[type='caption'][color='textSecondary']")).getText();
                            price = new BigDecimal(priceElement.replaceAll("[.₫ ]", ""));
                            percent = (int) Double.parseDouble(v.findElement(By.cssSelector("div[type='caption'][color='primary500']")).getText().replaceAll("[-%]", ""));
                        }catch (NoSuchElementException ex){
                            price = new BigDecimal(v.findElement(By.cssSelector("div[type='subtitle'][color='primary500']")).getText().replaceAll("[.₫ ]", ""));
                        }

//                        String priceElement = v.findElement(By.cssSelector("div[type='caption'][color='textSecondary']")).getText();
//                        if (!priceElement.isEmpty()) {
//                            price = new BigDecimal(priceElement.replaceAll("[.₫ ]", ""));
//                            percent = (int) Double.parseDouble(v.findElement(By.cssSelector("div[type='caption'][color='primary500']")).getText().replaceAll("[-%]", ""));
//                        } else {
//                            price = new BigDecimal(v.findElement(By.cssSelector("div[type='subtitle'][color='primary500']")).getText().replaceAll("[.₫ ]", ""));
//                        }

                        String shortDescription = dox.select("div.css-1i1dodm div.css-17aam1").html();
                        String description = dox.select("div.css-17aam1 div").html();

                        product.setId(Integer.parseInt(sku));
                        product.setSku(sku);
                        product.setName(name);
                        product.setDescription(description);
                        product.setShortDescription(shortDescription);
                        product.setPromotionPercent(percent);
                        product.setPriceOriginal(price);
                        product.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

                        Elements attributeElements = dox.select("div.css-1i3ajxp");
                        attributeElements.forEach(element -> {
                            Elements attrElement = element.select("div.css-pjx32y");
                            if (attrElement.get(0).text().equalsIgnoreCase("thương hiệu")) {
                                Brand brand = brandRepository.findBrandByName(attrElement.get(1).text());
                                if (brand == null) {
                                    brand = new Brand();
                                    brand.setName(attrElement.get(1).text());
                                }
                                product.setBrand(brand);
                            } else {
                                String labelAttr = attrElement.get(0).text();
                                String codeAttr = StringUtil.removeAccent(labelAttr).replaceAll("\\s", "").toUpperCase();

                                Attribute attribute = attributeRepository.findAttributeByCode(codeAttr);
                                if (attribute == null) attribute = new Attribute(codeAttr, labelAttr);

                                product.addAttribute(attribute, attrElement.get(1).text());
                            }
                        });

                        product.setCategory(categoryRepository.findCategoryById(input.getIdCategory()));

                        if (input.getIdDemand() != 0)
                            product.addDemand(demandRepository.findDemandById(input.getIdDemand()));

                        Elements imageElements = dox.select("div.css-4ok7dy");
                        imageElements.addAll(dox.select("div.css-1qorxog"));

                        List<DataImage> dataImages = new ArrayList<>();

                        int idx = 1;
                        for (Element e : imageElements) {
                            Element imgE = e.selectFirst("img");
                            DataImage dataImage = new DataImage();
                            String idImage = idx + "_" + ImageUtil.fileName(dataImageRepository, "jpg");
                            String nameImage = imgE.attr("alt").isEmpty() ? idImage : imgE.attr("alt");

                            dataImage.setId(idImage);
                            dataImage.setName(nameImage);
                            dataImage.setLink(imgE.attr("src"));
                            dataImage.setType("image/jpg");

                            dataImages.add(dataImage);
                            idx++;
                        }

                        product.addImages(dataImages);
                        System.out.println("sku: " + sku);
                        productRepository.save(product);
                    } else if (input.getIdDemand() != 0) {
                        if (productRepository.existsProductHaveDemand(Integer.parseInt(sku), input.getIdDemand())==0) {
                            productExist.addDemand(demandRepository.findDemandById(input.getIdDemand()));
                        }
                        System.out.println("id: "+productExist.getId());
                        productRepository.save(productExist);
                    }else{
                        System.out.println("id: "+productExist.getId());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            webDriver.close();
            webDriver.quit();
//            //  find id and name product
//            Elements detailText = main.select("div.blockhead div.detail-text");

//            Elements productPriceView = detailText.select("div.product-priceview");
//
//            String[] nameId = productPriceView.select("div.product-name h1").text().split(" SID");
//            String nameProductMain = nameId[0];
//
//            int idProduct = Integer.parseInt(nameId[1]);
//
//            if (exitsProduct(idProduct))
//                return "Exist Product";
//
//            // save category
//            Elements topbar = doc.select("div.top-bar > div.breadcrumbs > ul > li");
//
//            int idCategoryLevelLast = -1;
//
//            for (int i = 1; i < topbar.size(); i++) {
//                Element li = topbar.get(i);
//
//                int idCategory = Integer.parseInt(li.attr("class").split(" ")[0].replace("category", ""));
//                String nameCategory = li.select("span").text();
//
//                Category category = new Category();
//                category.setId(idCategory);
//                category.setLevel(i - 1);
//                category.setName(nameCategory);
//                if (i != 1)
//                    category.setParentCategory(categoryService.findCategoryById(idCategoryLevelLast));
//
//                // check exists category
//                if (!categoryService.existCategory(idCategory)) {
//                    categoryService.save(category);
//                }
//
//                idCategoryLevelLast = idCategory;
//            }
//
//            Product productMain = new Product();
//            productMain.setId(idProduct);
//            productMain.setName(nameProductMain);
//            productMain.setSku("SID" + idProduct);
//            productMain.setActive(true);
//            productMain.setVisibility(true);
//            productMain.setShortDescription(productPriceView.select("div.product-description").text());
//            productMain.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//            productMain.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//
//
//            Elements attribute_products = main.select("div.block-description div.product-attributes div.product-attribute");
//            for (Element attr : attribute_products) {
//                String title = attr.select("div.attribute-title").text().toLowerCase();
//                if (title.equals("thương hiệu")) {
//                    int idBrand = Integer.parseInt(attr.select("a").attr("href").replaceAll("\\D", ""));
//                    String nameBrand = attr.select("strong").text();
//                    if (!brandService.existBrandById(idBrand)) {
//                        Brand brand = new Brand();
//                        brand.setId(idBrand);
//                        brand.setName(nameBrand);
//                        brandService.save(brand);
//                    }
//                    productMain.setBrand(brandService.findBrandById(idBrand));
//
//                } else {
//                    if (title.equals("chất liệu")) {
//                        productMain.setMaterial(attr.select("div.attribute-text").text());
//                    } else {
//                        if (title.equals("kiểu dáng")) {
//                            productMain.setStyle(attr.select("div.attribute-text").text());
//                        } else {
//                            if (title.equals("mục đích sd")) {
//                                productMain.setPurpose(attr.select("div.attribute-text").text());
//                            } else {
//                                if (title.equals("mùa phù hợp")) {
//                                    productMain.setSuitableSeason(attr.select("div.attribute-text").text());
//                                } else {
//                                    if (title.equals("xuất xứ")) {
//                                        productMain.setMadeIn(attr.select("div.attribute-text").text());
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            productMain.setHighlight(main.select("div.block-description div.overview").html());
//            productMain.setDescription(main.select("div.block-description > p").outerHtml());
//            String textDiscount = productPriceView.select("div.sprites.discountpercent").text().replace("%", "");
//            productMain.setPromotionPercent(textDiscount.equals("") ? 0 : Math.abs(Integer.parseInt(textDiscount)));
//
//            Elements allAttributes = detailText.select("div.add-to-box div.product-attributeconf");
//
//            int numProduct = 1;
//
//            List<List<OptionProductVarchar>> listOpsss = new ArrayList<>();
//
//            for (Element e : allAttributes) {
//                Elements attributes = e.select("div.attributeconf-text > ul");
//                String[] idNameAttr = attributes.attr("id").split("-");
//                int idAttr = Integer.parseInt(idNameAttr[2]);
//                String nameAttr = idNameAttr[1];
//
//                if (!attributeService.existAttributeById(idAttr)) {
//                    Attribute attribute = new Attribute();
//                    attribute.setId(idAttr);
//                    attribute.setCode(nameAttr.toLowerCase());
//                    attribute.setLabel(StringUtil.capitalize(nameAttr));
//                    attribute.setType("varchar");
//
//                    attributeService.save(attribute);
//                }
//
//                Attribute attrNew = attributeService.findAttributeById(idAttr);
//
//                Elements options = attributes.select("> li");
//
//                List<OptionProductVarchar> list1 = new ArrayList<>();
//                for (Element o : options) {
//                    int idOp = Integer.parseInt(o.select("input").attr("value"));
//                    String nameOp = nameAttr.equals("color") ? o.select("img").attr("title") : o.select("label").text();
//
//                    if (!optionProductVarcharService.existOptionProductVarcharById(idOp)) {
//                        OptionProductVarchar optionProductVarchar = new OptionProductVarchar();
//                        optionProductVarchar.setId(idOp);
//                        optionProductVarchar.setValue(nameOp);
//                        optionProductVarchar.setAttribute(attrNew);
//
//                        optionProductVarcharService.save(optionProductVarchar);
//                    }
//                    OptionProductVarchar optionProductVarcharNew = optionProductVarcharService.findOptionProductVarcharById(idOp);
//                    list1.add(optionProductVarcharNew);
//                }
//
//                listOpsss.add(list1);
//            }
//
//            Random rd = new Random();
//
//            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(productPriceView.select("div.pricespecial").text().replaceAll("[^0-9]", "")));
//
//            Elements liImg = main.select("div.blockhead div.detail-imgproduct ul.thumb-detail > li");
//
//            List<ImageDTO> listImgDTO = new ArrayList<>();
//
//            liImg.forEach(li -> {
//                Elements img = li.select("img");
//                String title = img.attr("title");
//                String link_img = img.attr("src").replaceFirst("\\d{3}+x\\d{3}", "fill_size");
//
//                listImgDTO.add(new ImageDTO(title, link_img));
//            });
//
//            Set<Product> listProSub = new HashSet<>();
//
//            int checkIsColor = -1;
//
//            if (listOpsss.size() == 2) {
//                int index0 = 0;
//                int index1 = 1;
//                if (listOpsss.get(0).get(0).getAttribute().getId() != 80) {
//                    index0 = 1;
//                    index1 = 0;
//                }
//                for (int i = 0; i < listOpsss.get(index0).size(); i++) {
//
//                    for (int j = 0; j < listOpsss.get(index1).size(); j++) {
//                        Product prodSub = new Product();
//                        int idProoo = Integer.parseInt(String.valueOf(idProduct) + (((i + 1) * 20) + (j + 1)));
//                        prodSub.setId(idProoo);
//                        prodSub.setSku("SID" + idProoo);
//                        prodSub.setActive(true);
//                        prodSub.setVisibility(false);
//                        prodSub.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//                        prodSub.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//                        prodSub.setTypeId("Simple");
//
//                        int idPrice;
//                        int idQuantity;
//
//                        do {
//                            idPrice = 1 + rd.nextInt(6000001);
//                        } while (optionProductDecimalService.existOptionProductDecimalById(idPrice));
//
//                        OptionProductDecimal opDec = new OptionProductDecimal();
//                        opDec.setId(idPrice);
//                        opDec.setValue(price);
//                        opDec.setAttribute(240719);
//
//                        Set<OptionProductDecimal> optionProductDecimalSet = new HashSet<>();
//                        optionProductDecimalSet.add(optionProductDecimalService.save(opDec));
//                        prodSub.setOptionProductDecimals(optionProductDecimalSet);
//
//                        do {
//                            idQuantity = 1 + rd.nextInt(6000001);
//                        } while (optionProductIntegerService.existOptionProductIntegerById(idQuantity));
//
//                        OptionProductInteger opInt = new OptionProductInteger();
//                        opInt.setId(idQuantity);
//                        opInt.setValue(5 + rd.nextInt(50));
//                        opInt.setAttribute(240720);
//
//                        Set<OptionProductInteger> optionProductIntegerSet = new HashSet<>();
//                        optionProductIntegerSet.add(optionProductIntegerService.save(opInt));
//                        prodSub.setOptionProductIntegers(optionProductIntegerSet);
//
//                        Set<OptionProductVarchar> optionProductVarcharSet = new HashSet<>();
//                        optionProductVarcharSet.add(listOpsss.get(index0).get(i));
//                        optionProductVarcharSet.add(listOpsss.get(index1).get(j));
//
//                        prodSub.setOptionProductVarchars(optionProductVarcharSet);
//
//                        listProSub.add(productRepository.save(prodSub));
//                    }
//                }
//                productMain.setTypeId("configurable");
//                productMain.setProductLinks(listProSub);
//            } else {
//                if (listOpsss.size() == 1) {
//                    if (listOpsss.get(0).get(0).getAttribute().getId() == 80) {
//                        checkIsColor = 1;
//                    }
//                    for (int i = 0; i < listOpsss.get(0).size(); i++) {
//                        Product prodSub = new Product();
//                        int idProoo = Integer.parseInt(String.valueOf(idProduct) + (((i + 1) * 20) + 1));
//                        prodSub.setId(idProoo);
//                        prodSub.setSku("SID" + idProoo);
//                        prodSub.setActive(true);
//                        prodSub.setVisibility(false);
//                        prodSub.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//                        prodSub.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//                        prodSub.setTypeId("simple");
//
//                        int idPrice;
//                        int idQuantity;
//
//                        do {
//                            idPrice = 1 + rd.nextInt(6000001);
//                        } while (optionProductDecimalService.existOptionProductDecimalById(idPrice));
//
//                        OptionProductDecimal opDec = new OptionProductDecimal();
//                        opDec.setId(idPrice);
//                        opDec.setValue(price);
//                        opDec.setAttribute(240719);
//
//                        Set<OptionProductDecimal> optionProductDecimalSet = new HashSet<>();
//                        optionProductDecimalSet.add(optionProductDecimalService.save(opDec));
//                        prodSub.setOptionProductDecimals(optionProductDecimalSet);
//
//                        do {
//                            idQuantity = 1 + rd.nextInt(6000001);
//                        } while (optionProductIntegerService.existOptionProductIntegerById(idQuantity));
//
//                        OptionProductInteger opInt = new OptionProductInteger();
//                        opInt.setId(idQuantity);
//                        opInt.setValue(5 + rd.nextInt(50));
//                        opInt.setAttribute(240720);
//
//                        Set<OptionProductInteger> optionProductIntegerSet = new HashSet<>();
//                        optionProductIntegerSet.add(optionProductIntegerService.save(opInt));
//                        prodSub.setOptionProductIntegers(optionProductIntegerSet);
//
//                        Set<OptionProductVarchar> optionProductVarcharSet = new HashSet<>();
//                        optionProductVarcharSet.add(listOpsss.get(0).get(i));
//
//                        prodSub.setOptionProductVarchars(optionProductVarcharSet);
//
//                        listProSub.add(productRepository.save(prodSub));
//                    }
//
//                    productMain.setTypeId("configurable");
//                    productMain.setProductLinks(listProSub);
//                } else {
//                    productMain.setTypeId("simple");
//
//                    int idPrice;
//                    int idQuantity;
//
//                    do {
//                        idPrice = 1 + rd.nextInt(6000001);
//                    } while (optionProductDecimalService.existOptionProductDecimalById(idPrice));
//
//                    OptionProductDecimal opDec = new OptionProductDecimal();
//                    opDec.setId(idPrice);
//                    opDec.setValue(price);
//                    opDec.setAttribute(240719);
//
//                    Set<OptionProductDecimal> optionProductDecimalSet = new HashSet<>();
//                    optionProductDecimalSet.add(optionProductDecimalService.save(opDec));
//                    productMain.setOptionProductDecimals(optionProductDecimalSet);
//
//                    do {
//                        idQuantity = 1 + rd.nextInt(6000001);
//                    } while (optionProductIntegerService.existOptionProductIntegerById(idQuantity));
//
//                    OptionProductInteger opInt = new OptionProductInteger();
//                    opInt.setId(idQuantity);
//                    opInt.setValue(5 + rd.nextInt(50));
//                    opInt.setAttribute(240720);
//                }
//            }
//
//            productRepository.save(productMain);
//
//
//            return "done";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exitsProduct(int id) {
//        return productRepository.existsProductByIdAndVisibilityTrue(id);
        return true;
    }
}
