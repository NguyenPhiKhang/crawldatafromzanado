package com.khangse616.crawldatazanado.services.impl;

import com.khangse616.crawldatazanado.Utils.StringUtil;
import com.khangse616.crawldatazanado.models.*;
import com.khangse616.crawldatazanado.models.DTO.ImageDTO;
import com.khangse616.crawldatazanado.repositories.ProductRepository;
import com.khangse616.crawldatazanado.services.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IBrandService brandService;

    @Autowired
    private IAttributeService attributeService;

    @Autowired
    private IOptionProductVarcharService optionProductVarcharService;

    @Autowired
    private IOptionProductDecimalService optionProductDecimalService;

    @Autowired
    private IOptionProductIntegerService optionProductIntegerService;

    @Override
    public String createProduct(String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            Elements main = doc.select("div.main div#productleft");

            //  find id and name product
            Elements detailText = main.select("div.blockhead div.detail-text");

            Elements productPriceView = detailText.select("div.product-priceview");

            String[] nameId = productPriceView.select("div.product-name h1").text().split(" SID");
            String nameProductMain = nameId[0];

            int idProduct = Integer.parseInt(nameId[1]);

            if (exitsProduct(idProduct))
                return "Exist Product";

            // save category
            Elements topbar = doc.select("div.top-bar > div.breadcrumbs > ul > li");

            int idCategoryLevelLast = -1;

            for (int i = 1; i < topbar.size(); i++) {
                Element li = topbar.get(i);

                int idCategory = Integer.parseInt(li.attr("class").split(" ")[0].replace("category", ""));
                String nameCategory = li.select("span").text();

                Category category = new Category();
                category.setId(idCategory);
                category.setLevel(i - 1);
                category.setName(nameCategory);
                if (i != 1)
                    category.setParentCategory(categoryService.findCategoryById(idCategoryLevelLast));

                // check exists category
                if (!categoryService.existCategory(idCategory)) {
                    categoryService.save(category);
                }

                idCategoryLevelLast = idCategory;
            }

            Product productMain = new Product();
            productMain.setId(idProduct);
            productMain.setName(nameProductMain);
            productMain.setSku("SID" + idProduct);
            productMain.setActive(true);
            productMain.setVisibility(true);
            productMain.setShortDescription(productPriceView.select("div.product-description").text());
            productMain.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            productMain.setUpdatedAt(new Timestamp(System.currentTimeMillis()));



            Elements attribute_products = main.select("div.block-description div.product-attributes div.product-attribute");
            for (Element attr : attribute_products) {
                String title = attr.select("div.attribute-title").text().toLowerCase();
                if (title.equals("thương hiệu")) {
                    int idBrand = Integer.parseInt(attr.select("a").attr("href").replaceAll("\\D", ""));
                    String nameBrand = attr.select("strong").text();
                    if (!brandService.existBrandById(idBrand)) {
                        Brand brand = new Brand();
                        brand.setId(idBrand);
                        brand.setName(nameBrand);
                        brandService.save(brand);
                    }
                    productMain.setBrand(brandService.findBrandById(idBrand));

                } else {
                    if (title.equals("chất liệu")) {
                        productMain.setMaterial(attr.select("div.attribute-text").text());
                    } else {
                        if (title.equals("kiểu dáng")) {
                            productMain.setStyle(attr.select("div.attribute-text").text());
                        } else {
                            if (title.equals("mục đích sd")) {
                                productMain.setPurpose(attr.select("div.attribute-text").text());
                            } else {
                                if (title.equals("mùa phù hợp")) {
                                    productMain.setSuitableSeason(attr.select("div.attribute-text").text());
                                } else {
                                    if (title.equals("xuất xứ")) {
                                        productMain.setMadeIn(attr.select("div.attribute-text").text());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            productMain.setHighlight(main.select("div.block-description div.overview").html());
            productMain.setDescription(main.select("div.block-description > p").outerHtml());
            String textDiscount = productPriceView.select("div.sprites.discountpercent").text().replace("%", "");
            productMain.setPromotionPercent(textDiscount.equals("") ? 0 : Math.abs(Integer.parseInt(textDiscount)));

            Elements allAttributes = detailText.select("div.add-to-box div.product-attributeconf");

            int numProduct = 1;

            List<List<OptionProductVarchar>> listOpsss = new ArrayList<>();

            for (Element e : allAttributes) {
                Elements attributes = e.select("div.attributeconf-text > ul");
                String[] idNameAttr = attributes.attr("id").split("-");
                int idAttr = Integer.parseInt(idNameAttr[2]);
                String nameAttr = idNameAttr[1];

                if (!attributeService.existAttributeById(idAttr)) {
                    Attribute attribute = new Attribute();
                    attribute.setId(idAttr);
                    attribute.setCode(nameAttr.toLowerCase());
                    attribute.setLabel(StringUtil.capitalize(nameAttr));
                    attribute.setType("varchar");

                    attributeService.save(attribute);
                }

                Attribute attrNew = attributeService.findAttributeById(idAttr);

                Elements options = attributes.select("> li");

                List<OptionProductVarchar> list1 = new ArrayList<>();
                for (Element o : options) {
                    int idOp = Integer.parseInt(o.select("input").attr("value"));
                    String nameOp = nameAttr.equals("color") ? o.select("img").attr("title") : o.select("label").text();

                    if (!optionProductVarcharService.existOptionProductVarcharById(idOp)) {
                        OptionProductVarchar optionProductVarchar = new OptionProductVarchar();
                        optionProductVarchar.setId(idOp);
                        optionProductVarchar.setValue(nameOp);
                        optionProductVarchar.setAttribute(attrNew);

                        optionProductVarcharService.save(optionProductVarchar);
                    }
                    OptionProductVarchar optionProductVarcharNew = optionProductVarcharService.findOptionProductVarcharById(idOp);
                    list1.add(optionProductVarcharNew);
                }

                listOpsss.add(list1);
            }

            Random rd = new Random();

            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(productPriceView.select("div.pricespecial").text().replaceAll("[^0-9]", "")));

            Elements liImg = main.select("div.blockhead div.detail-imgproduct ul.thumb-detail > li");

            List<ImageDTO> listImgDTO = new ArrayList<>();

            liImg.forEach(li->{
                Elements img = li.select("img");
                String title = img.attr("title");
                String link_img = img.attr("src").replaceFirst("\\d{3}+x\\d{3}", "fill_size");

                listImgDTO.add(new ImageDTO(title, link_img));
            });

            List<Product> listProSub = new ArrayList<>();

            int checkIsColor = -1;

            if (listOpsss.size() == 2) {
                checkIsColor = 1;
                for(int i=0;i<listOpsss.get(0).size();i++){
                    for(int j=0;j<listOpsss.get(1).size();j++){
                        Product prodSub = new Product();
                        int idProoo = Integer.parseInt(String.valueOf(idProduct)+ (((i+1)*20) + (j+1)));
                        prodSub.setId(idProoo);
                        prodSub.setSku("SID"+idProoo);
                        prodSub.setActive(true);
                        prodSub.setVisibility(false);
                        prodSub.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                        prodSub.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        prodSub.setTypeId("Simple");

                        int idPrice;
                        int idQuantity;

                        do {
                            idPrice = 1 + rd.nextInt(6000001);
                        } while (optionProductDecimalService.existOptionProductDecimalById(idPrice));

                        OptionProductDecimal opDec = new OptionProductDecimal();
                        opDec.setId(idPrice);
                        opDec.setValue(price);
                        opDec.setAttribute(240719);

                        Set<OptionProductDecimal> optionProductDecimalSet = new HashSet<>();
                        optionProductDecimalSet.add(optionProductDecimalService.save(opDec));
                        prodSub.setOptionProductDecimals(optionProductDecimalSet);

                        do {
                            idQuantity  = 1 + rd.nextInt(6000001);
                        } while (optionProductIntegerService.existOptionProductIntegerById(idQuantity));

                        OptionProductInteger opInt = new OptionProductInteger();
                        opInt.setId(idQuantity);
                        opInt.setValue(5+rd.nextInt(50));
                        opInt.setAttribute(240720);

                        Set<OptionProductInteger> optionProductIntegerSet = new HashSet<>();
                        optionProductIntegerSet.add(optionProductIntegerService.save(opInt));
                        prodSub.setOptionProductIntegers(optionProductIntegerSet);

                        Set<OptionProductVarchar> optionProductVarcharSet = new HashSet<>();
                        optionProductVarcharSet.add(listOpsss.get(0).get(i));
                        optionProductVarcharSet.add(listOpsss.get(1).get(j));

                        prodSub.setOptionProductVarchars(optionProductVarcharSet);


                        listProSub.add(productRepository.save(prodSub));
                    }
                }
                productMain.setTypeId("configurable");
            } else {
                if (listOpsss.size() == 1) {
                    for(int i=0;i<listOpsss.get(0).size();i++){
                        Product prodSub = new Product();
                        int idProoo = Integer.parseInt(String.valueOf(idProduct)+ (((i+1)*20) + 1));
                        prodSub.setId(idProoo);
                        prodSub.setSku("SID"+idProoo);
                        prodSub.setActive(true);
                        prodSub.setVisibility(false);
                        prodSub.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                        prodSub.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        prodSub.setTypeId("simple");

                        int idPrice;
                        int idQuantity;

                        do {
                            idPrice = 1 + rd.nextInt(6000001);
                        } while (optionProductDecimalService.existOptionProductDecimalById(idPrice));

                        OptionProductDecimal opDec = new OptionProductDecimal();
                        opDec.setId(idPrice);
                        opDec.setValue(price);
                        opDec.setAttribute(240719);

                        Set<OptionProductDecimal> optionProductDecimalSet = new HashSet<>();
                        optionProductDecimalSet.add(optionProductDecimalService.save(opDec));
                        prodSub.setOptionProductDecimals(optionProductDecimalSet);

                        do {
                            idQuantity  = 1 + rd.nextInt(6000001);
                        } while (optionProductIntegerService.existOptionProductIntegerById(idQuantity));

                        OptionProductInteger opInt = new OptionProductInteger();
                        opInt.setId(idQuantity);
                        opInt.setValue(5+rd.nextInt(50));
                        opInt.setAttribute(240720);

                        Set<OptionProductInteger> optionProductIntegerSet = new HashSet<>();
                        optionProductIntegerSet.add(optionProductIntegerService.save(opInt));
                        prodSub.setOptionProductIntegers(optionProductIntegerSet);

                        Set<OptionProductVarchar> optionProductVarcharSet = new HashSet<>();
                        optionProductVarcharSet.add(listOpsss.get(0).get(i));

                        prodSub.setOptionProductVarchars(optionProductVarcharSet);


                        listProSub.add(productRepository.save(prodSub));
                    }
                    productMain.setTypeId("configurable");
                } else {

                    productMain.setTypeId("simple");

                    int idPrice;
                    int idQuantity;

                    do {
                        idPrice = 1 + rd.nextInt(6000001);
                    } while (optionProductDecimalService.existOptionProductDecimalById(idPrice));

                    OptionProductDecimal opDec = new OptionProductDecimal();
                    opDec.setId(idPrice);
                    opDec.setValue(price);
                    opDec.setAttribute(240719);

                    Set<OptionProductDecimal> optionProductDecimalSet = new HashSet<>();
                    optionProductDecimalSet.add(optionProductDecimalService.save(opDec));
                    productMain.setOptionProductDecimals(optionProductDecimalSet);

                    do {
                        idQuantity  = 1 + rd.nextInt(6000001);
                    } while (optionProductIntegerService.existOptionProductIntegerById(idQuantity));

                    OptionProductInteger opInt = new OptionProductInteger();
                    opInt.setId(idQuantity);
                    opInt.setValue(5+rd.nextInt(50));
                    opInt.setAttribute(240720);

//                    listProSub.add(productRepository.save(prodSub));
                }
            }

            if(checkIsColor==1){

            }



            return "done";
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
