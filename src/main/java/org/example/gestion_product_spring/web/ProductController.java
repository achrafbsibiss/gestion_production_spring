package org.example.gestion_product_spring.web;

import jakarta.validation.Valid;
import org.example.gestion_product_spring.entities.Product;
import org.example.gestion_product_spring.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String products(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        model.addAttribute("productList", productRepository.findByNameContainsIgnoreCase(keyword));
        model.addAttribute("keyword", keyword);
        return "products";
    }

    @GetMapping("/newProduct")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "newProduct";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult bindingResult,
                              @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        if (bindingResult.hasErrors()) return "newProduct";
        productRepository.save(product);
        return "redirect:/products?keyword=" + keyword;
    }

    @GetMapping("/editProduct")
    public String editProduct(Model model, @RequestParam(name = "id") Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "editProduct";
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam(name = "id") Long id,
                                @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        productRepository.deleteById(id);
        return "redirect:/products?keyword=" + keyword;
    }
}
