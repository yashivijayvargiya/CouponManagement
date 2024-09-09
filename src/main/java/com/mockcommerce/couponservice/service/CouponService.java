package com.mockcommerce.couponservice.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mockcommerce.couponservice.dao.BxGyCouponBuyProductsRepository;
import com.mockcommerce.couponservice.dao.BxGyCouponDetailsRepository;
import com.mockcommerce.couponservice.dao.BxGyCouponGetProductsRepository;
import com.mockcommerce.couponservice.dao.CartWiseCouponDetailsRepository;
import com.mockcommerce.couponservice.dao.CouponRepository;
import com.mockcommerce.couponservice.dao.ProductWiseCouponDetailsRepository;
import com.mockcommerce.couponservice.dto.CouponRequest.CouponDetails.BuyProduct;
import com.mockcommerce.couponservice.dto.CouponRequest.CouponDetails.GetProduct;
import com.mockcommerce.couponservice.dto.UpdatedCartResponse;
import com.mockcommerce.couponservice.entity.BxGyCouponBuyProducts;
import com.mockcommerce.couponservice.entity.BxGyCouponDetails;
import com.mockcommerce.couponservice.entity.BxGyCouponGetProducts;
import com.mockcommerce.couponservice.entity.CartWiseCouponDetails;
import com.mockcommerce.couponservice.entity.Coupon;
import com.mockcommerce.couponservice.entity.ProductWiseCouponDetails;
import com.mockcommerce.couponservice.dto.ApplicableCouponResponse;
import com.mockcommerce.couponservice.dto.Cart;
import com.mockcommerce.couponservice.dto.CartItem;
import com.mockcommerce.couponservice.dto.CouponRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CouponService {
    private static final Logger log = LoggerFactory.getLogger(CouponService.class);
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CartWiseCouponDetailsRepository cartWiseCouponDetailsRepository;
    @Autowired
    private ProductWiseCouponDetailsRepository productWiseCouponDetailsRepository;
    @Autowired
    private BxGyCouponDetailsRepository bxGyCouponDetailsRepository;
    @Autowired
    private BxGyCouponBuyProductsRepository bxGyCouponBuyProductsRepository;
    @Autowired
    private BxGyCouponGetProductsRepository bxGyCouponGetProductsRepository;

    public void createCoupon(CouponRequest couponRequest) {
        Coupon coupon = new Coupon();
        coupon.setType(couponRequest.getType());
        coupon = couponRepository.save(coupon);

        switch (couponRequest.getType()) {
            case "cart-wise":
                CartWiseCouponDetails cartWiseDetails = new CartWiseCouponDetails();
                cartWiseDetails.setCoupon(coupon);
                cartWiseDetails.setThreshold(couponRequest.getDetails().getThreshold());
                cartWiseDetails.setDiscount(couponRequest.getDetails().getDiscount());
                cartWiseCouponDetailsRepository.save(cartWiseDetails);
                break;

            case "product-wise":
                ProductWiseCouponDetails productWiseDetails = new ProductWiseCouponDetails();
                productWiseDetails.setCoupon(coupon);
                productWiseDetails.setProductId(couponRequest.getDetails().getProduct_id());
                productWiseDetails.setDiscount(couponRequest.getDetails().getDiscount());
                productWiseCouponDetailsRepository.save(productWiseDetails);
                break;

            case "bxgy":
                BxGyCouponDetails bxGyDetails = new BxGyCouponDetails();
                bxGyDetails.setCoupon(coupon);
                bxGyDetails.setRepetitionLimit(couponRequest.getDetails().getRepition_limit());
                bxGyCouponDetailsRepository.save(bxGyDetails);

                for (BuyProduct buyProduct : couponRequest.getDetails().getBuy_products()) {
                    BxGyCouponBuyProducts bxGyBuyProduct = new BxGyCouponBuyProducts();
                    bxGyBuyProduct.setBxGyCouponDetails(bxGyDetails);
                    bxGyBuyProduct.setProductId(buyProduct.getProduct_id());
                    bxGyBuyProduct.setQuantity(buyProduct.getQuantity());
                    bxGyCouponBuyProductsRepository.save(bxGyBuyProduct);
                }

                for (GetProduct getProduct : couponRequest.getDetails().getGet_products()) {
                    BxGyCouponGetProducts bxGyGetProduct = new BxGyCouponGetProducts();
                    bxGyGetProduct.setBxGyCouponDetails(bxGyDetails);
                    bxGyGetProduct.setProductId(getProduct.getProduct_id());
                    bxGyGetProduct.setQuantity(getProduct.getQuantity());
                    bxGyCouponGetProductsRepository.save(bxGyGetProduct);
                }
                break;
        }
    }

    public List<Coupon> getAllCoupon() {
        return couponRepository.findAll();
    }

    public Coupon getCouponById(Integer id) {
        return couponRepository.findById(id).orElse(null);
    }

    public Coupon updateCouponById(Integer id, Coupon updatedCoupon) {
        return couponRepository.findById(id).map(coupon -> {
            coupon.setType(updatedCoupon.getType());

            return couponRepository.save(coupon);
        }).orElse(null);
    }

    public boolean deleteCouponById(Integer id) {
        // Check if the coupon is assigned to any product or other entities
        boolean isAssignedToProduct = productWiseCouponDetailsRepository.existsByCouponId(id);
        boolean isAssignedToCart = cartWiseCouponDetailsRepository.existsByCouponId(id);
        boolean isAssignedToBxGy = bxGyCouponDetailsRepository.existsByCouponId(id);
        if (isAssignedToProduct || isAssignedToCart || isAssignedToBxGy) {
            // Log a warning and prevent deletion
            log.warn("Cannot delete coupon with ID {} as it is assigned to a product.", id);
            return false;
        }

        // Proceed with deletion if the coupon is not assigned
        return couponRepository.findById(id).map(coupon -> {
            couponRepository.delete(coupon);
            log.info("Coupon with ID {} deleted successfully.", id);
            return true;
        }).orElse(false);
    }

    private BigDecimal calculateCartValue(Cart cart) {
        if (cart == null) {
            log.error("Cart is null");
            return BigDecimal.ZERO;
        }

        if (cart.getItems() == null) {
            log.error("Cart items are null");
            return BigDecimal.ZERO;
        }

        log.info("Cart items: {}", cart.getItems());

        return cart.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Helper to convert the cart list to a map (product_id -> CartItem)
    private Map<Integer, CartItem> cartItemsToMap(List<CartItem> items) {
        return items.stream().collect(Collectors.toMap(CartItem::getProductId, item -> item));
    }

    public List<ApplicableCouponResponse.CouponDetails> getApplicableCoupons(Cart cart) {
        List<ApplicableCouponResponse.CouponDetails> applicableCoupons = new ArrayList<>();

        // Calculate the total cart value
        BigDecimal totalCartValue = calculateCartValue(cart);
        Map<Integer, CartItem> cartItemsMap = cartItemsToMap(cart.getItems());

        // 1. Apply Cart-wise Coupons (e.g., 10% off for carts over Rs. 100)
        List<CartWiseCouponDetails> cartWiseCoupons = cartWiseCouponDetailsRepository.findAll();
        for (CartWiseCouponDetails coupon : cartWiseCoupons) {
            // Check if the total cart value exceeds the coupon threshold
            if (totalCartValue.compareTo(coupon.getThreshold()) > 0) {
                // Calculate the discount based on the percentage of the threshold
                BigDecimal threshold = coupon.getThreshold();
                BigDecimal discount = threshold.multiply(coupon.getDiscount()).divide(BigDecimal.valueOf(100));

                // Add the coupon details to the list of applicable coupons
                applicableCoupons.add(
                        new ApplicableCouponResponse.CouponDetails(coupon.getCoupon().getId(), "cart-wise", discount));
            }
        }

        // // 2. Apply Product-wise Coupons (e.g., 20% off for specific products)
        // List<ProductWiseCouponDetails> productWiseCoupons =
        // productWiseCouponDetailsRepository.findAll();
        // for (ProductWiseCouponDetails coupon : productWiseCoupons) {
        // if (cartItemsMap.containsKey(coupon.getProductId())) {
        // CartItem item = cartItemsMap.get(coupon.getProductId());
        // BigDecimal productDiscount =
        // item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
        // .multiply(coupon.getDiscount()).divide(BigDecimal.valueOf(100));
        // applicableCoupons.add(new
        // ApplicableCouponResponse.CouponDetails(coupon.getCoupon().getId(),
        // "product-wise", productDiscount));
        // }
        // }

        // 3. Apply BxGy Coupons (Buy X, Get Y Free)
        List<BxGyCouponDetails> bxGyCoupons = bxGyCouponDetailsRepository.findAll();
        for (BxGyCouponDetails bxGyCoupon : bxGyCoupons) {
            BigDecimal bxGyDiscount = applyBxGyCoupon(bxGyCoupon, cartItemsMap);
            if (bxGyDiscount.compareTo(BigDecimal.ZERO) > 0) {
                applicableCoupons.add(new ApplicableCouponResponse.CouponDetails(bxGyCoupon.getCoupon().getId(), "bxgy",
                        bxGyDiscount));
            }
        }

        return applicableCoupons;
    }

    private BigDecimal applyBxGyCoupon(BxGyCouponDetails bxGyCoupon, Map<Integer, CartItem> cartItemsMap) {
        int buyCount = 0;

        // Assuming buyQuantity is provided directly or is predefined
        int buyQuantity = 1; // Example value, you should determine how to get this value

        for (BxGyCouponBuyProducts buyProduct : bxGyCoupon.getBuyProducts()) {
            if (cartItemsMap.containsKey(buyProduct.getProductId())) {
                buyCount += cartItemsMap.get(buyProduct.getProductId()).getQuantity();
            }
        }

        // Calculate free products based on buy count and repetition limit
        int repetitionLimit = bxGyCoupon.getRepetitionLimit();
        int freeProductsCount = Math.min(buyCount / buyQuantity, repetitionLimit);

        BigDecimal discount = BigDecimal.ZERO;
        for (BxGyCouponGetProducts getProduct : bxGyCoupon.getGetProducts()) {
            if (cartItemsMap.containsKey(getProduct.getProductId())) {
                CartItem item = cartItemsMap.get(getProduct.getProductId());
                BigDecimal freeAmount = item.getPrice()
                        .multiply(BigDecimal.valueOf(Math.min(freeProductsCount, item.getQuantity())));
                discount = discount.add(freeAmount);
            }
        }

        return discount;
    }

    public UpdatedCartResponse applyCoupon(Long couponId, Cart cart) {
        UpdatedCartResponse response = new UpdatedCartResponse();
        UpdatedCartResponse.UpdatedCart updatedCart = new UpdatedCartResponse.UpdatedCart();

        BigDecimal totalDiscount = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<UpdatedCartResponse.UpdatedCartItem> updatedItems = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            UpdatedCartResponse.UpdatedCartItem updatedItem = new UpdatedCartResponse.UpdatedCartItem();
            updatedItem.setProductId(item.getProductId());
            updatedItem.setQuantity(item.getQuantity());
            updatedItem.setPrice(item.getPrice());

            // Apply product-wise or cart-wise coupon discounts
            BigDecimal itemDiscount = calculateDiscount(item, couponId, cart);
            updatedItem.setTotalDiscount(itemDiscount);

            updatedItems.add(updatedItem);

            BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);
            totalDiscount = totalDiscount.add(itemDiscount);
        }

        updatedCart.setItems(updatedItems);
        updatedCart.setTotalPrice(totalPrice);
        updatedCart.setTotalDiscount(totalDiscount);
        updatedCart.setFinalPrice(totalPrice.subtract(totalDiscount));

        response.setUpdatedCart(updatedCart);
        return response;
    }

    private BigDecimal calculateDiscount(CartItem item, Long couponId, Cart cart) {
        BigDecimal discount = BigDecimal.ZERO;

        if (isCartWiseDiscount(couponId)) {
            BigDecimal totalCartPrice = cart.getItems().stream()
                    .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (totalCartPrice.compareTo(BigDecimal.valueOf(100)) > 0) {
                discount = totalCartPrice.multiply(BigDecimal.valueOf(0.1)); // 10% off total
            }
        }

        if (isProductWiseDiscount(couponId, item)) {
            discount = item.getPrice().multiply(BigDecimal.valueOf(0.2)); // 20% off product
        }

        if (isBxGyDiscount(couponId, cart)) {
            discount = calculateBxGyDiscount(item, cart); // BxGy discount logic
        }

        return discount;
    }

    private BigDecimal calculateBxGyDiscount(CartItem item, Cart cart) {
        BigDecimal discount = BigDecimal.ZERO;

        List<Integer> buyArray = Arrays.asList(1, 2, 3); // Buy products: X, Y, Z
        int eligibleBuyCount = getEligibleBuyCount(cart, buyArray); // Calculate total buyable products
        int freeCount = eligibleBuyCount / 2; // Buy 2, Get 1 Free logic

        if (freeCount > 0 && isEligibleForFreeItem(item)) {
            // Calculate free discount for the eligible "get" product (e.g., Product Z)
            discount = item.getPrice().multiply(BigDecimal.valueOf(Math.min(freeCount, item.getQuantity())));
        }

        return discount;
    }

    private int getEligibleBuyCount(Cart cart, List<Integer> buyArray) {
        return cart.getItems().stream()
                .filter(item -> buyArray.contains(item.getProductId()))
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    private boolean isEligibleForFreeItem(CartItem item) {
        List<Integer> freeArray = Arrays.asList(4, 5, 6); // Example "get" products
        return freeArray.contains(item.getProductId());
    }

    public boolean isCartWiseDiscount(Long couponId) {
        return couponId == 1; // Placeholder for cart-wide discount
    }

    public boolean isProductWiseDiscount(Long couponId, CartItem item) {
        return couponId == 2 && item.getProductId() == 1; // 20% discount on Product ID 1
    }

    public boolean isBxGyDiscount(Long couponId, Cart cart) {
        return couponId == 3; // Placeholder for BxGy coupon
    }

}

