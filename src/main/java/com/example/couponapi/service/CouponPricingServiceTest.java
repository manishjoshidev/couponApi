package com.example.couponapi.service;

import com.example.couponapi.binding.Coupon;
import com.example.couponapi.exception.CouponException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class CouponPricingServiceTest {

    private CouponPricingService pricingService;
    private Coupon coupon;

    @BeforeEach
    void setup() {
        pricingService = new CouponPricingService();

        coupon = new Coupon();
        coupon.setActive(true);
        coupon.setStartDate(LocalDateTime.now().minusDays(1));
        coupon.setEndDate(LocalDateTime.now().plusDays(1));
    }

    @Test
    void testCartWiseDiscount() {
        coupon.setType("cart-wise");
        coupon.setDetails("{\"discountPercent\":50}");
        double result = pricingService.applyCoupon(coupon, 1000);
        Assertions.assertEquals(500, result);
    }

    @Test
    void testProductWiseDiscount() {
        coupon.setType("product-wise");
        coupon.setDetails("{\"discountAmount\":200}");
        double result = pricingService.applyCoupon(coupon, 1000);
        Assertions.assertEquals(800, result);
    }

    @Test
    void testBxGyDiscount() {
        coupon.setType("bxgy");
        coupon.setDetails("{\"buy\":2,\"free\":1,\"productPrice\":100,\"totalProducts\":6}");
        double result = pricingService.applyCoupon(coupon, 600);
        Assertions.assertEquals(400, result);
    }

    @Test
    void testExpiredCoupon() {
        coupon.setEndDate(LocalDateTime.now().minusDays(1));
        assertThrows(CouponException.class, () -> pricingService.applyCoupon(coupon, 1000));
    }
}
