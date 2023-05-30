package challenge18.hotdeal.domain.product.controller;

import challenge18.hotdeal.common.security.UserDetailsImpl;
import challenge18.hotdeal.common.util.Message;
import challenge18.hotdeal.domain.product.dto.AllProductResponseDto;
import challenge18.hotdeal.domain.product.dto.ProductSearchCondition;
import challenge18.hotdeal.domain.product.dto.SelectProductResponseDto;
import challenge18.hotdeal.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // 상품 목록 조회
    @GetMapping("")
    public Page<AllProductResponseDto> allProduct(ProductSearchCondition condition, @PageableDefault Pageable pageable) {
        System.out.println("condition.getMinPrice() = " + condition.getMinPrice());
        System.out.println("condition.getMaxPrice() = " + condition.getMaxPrice());
        System.out.println("condition.getMainCategory() = " + condition.getMainCategory());
        System.out.println("condition.getSubCategory() = " + condition.getSubCategory());
        return productService.allProduct(condition, pageable);
    }

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public SelectProductResponseDto selectProduct(@PathVariable Long productId){
        return productService.selectProduct(productId);
    }

    // 상품 구매
    @PostMapping("/{productId}")
    public ResponseEntity<Message> buyLimitedProduct(@PathVariable Long productId,
                                                     @RequestBody Map<String, Integer> map,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.buyProduct(productId, map.get("quantity"), userDetails.getUser());
    }
}
