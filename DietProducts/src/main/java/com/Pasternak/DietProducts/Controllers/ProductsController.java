package com.Pasternak.DietProducts.Controllers;

import com.Pasternak.DietProducts.models.ProductDto;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.ui.Model;
import com.Pasternak.DietProducts.models.Product;
import com.Pasternak.DietProducts.services.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping({"", "/"})
    public String showProductList(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }


    @GetMapping("/create")
    public String showCreatePage(Model model){
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/CreateProduct";
    }

    @PostMapping("create")
    public String createProduct(
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result
            ){
        if (result.hasErrors()){
            return "products/CreateProduct";
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        productRepository.save(product);

        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ){
        try {
            Product product = productRepository.findById(id).get();
            model.addAttribute("product", product);

            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());

            model.addAttribute("productDto", productDto);
        }
        catch (Exception e){
            System.out.println("excception" + e.getMessage());
            return "redirect:/products";
        }

        return "products/EditProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result
            ){

        try{
            Product product = productRepository.findById(id).get();
            model.addAttribute("product", product);

            if(result.hasErrors()){
                return "products/EditProduct";
            }
            product.setName(productDto.getName());
            product.setCategory(productDto.getCategory());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());

            productRepository.save(product);
        }
        catch (Exception e){
            System.out.println("Exception" + e.getMessage());
        }

        return "redirect:/products";
    }


    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id){

        try{
            Product product = productRepository.findById(id).get();
            productRepository.delete(product);
        }
        catch (Exception e){
            System.out.println("Exception" + e.getMessage());
        }

        return "redirect:/products";
    }



}
