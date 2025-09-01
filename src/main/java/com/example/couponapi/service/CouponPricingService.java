package com.example.couponapi.service;

import com.example.couponapi.binding.Coupon;
import com.example.couponapi.exception.CouponException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CouponPricingService {

    private final ObjectMapper mapper = new ObjectMapper();

    public double applyCoupon(Coupon coupon, double cartTotal) {
        validateCoupon(coupon);

        try {
            JsonNode details = mapper.readTree(coupon.getDetails());

            switch (coupon.getType().toLowerCase()) {
                case "cart-wise":
                    return applyCartWiseDiscount(cartTotal, details);

                case "product-wise":
                    return applyProductWiseDiscount(cartTotal, details);

                case "bxgy":
                    return applyBxGyDiscount(cartTotal, details);

                default:
                    throw new CouponException("Unsupported coupon type: " + coupon.getType());
            }
        } catch (CouponException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CouponException("Invalid coupon details format");
        }
    }

    private void validateCoupon(Coupon coupon) {
        if (!coupon.isActive()) {
            throw new CouponException("Coupon is inactive");
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(coupon.getStartDate()) || now.isAfter(coupon.getEndDate())) {
            throw new CouponException("Coupon expired or not yet started");
        }
    }

    private double applyCartWiseDiscount(double cartTotal, JsonNode details) {
        double discountPercent = details.get("discountPercent").asDouble();
        return cartTotal - (cartTotal * discountPercent / 100);
    }

    private double applyProductWiseDiscount(double cartTotal, JsonNode details) {
        double discountAmount = details.get("discountAmount").asDouble();
        return Math.max(cartTotal - discountAmount, 0);
    }

    private double applyBxGyDiscount(double cartTotal, JsonNode details) {
        int buy = details.get("buy").asInt();
        int free = details.get("free").asInt();
        double productPrice = details.get("productPrice").asDouble();
        int totalProducts = details.get("totalProducts").asInt();

        int eligibleFreeItems = (totalProducts / (buy + free)) * free;
        double discountAmount = eligibleFreeItems * productPrice;

        return Math.max(cartTotal - discountAmount, 0);
    }
}
