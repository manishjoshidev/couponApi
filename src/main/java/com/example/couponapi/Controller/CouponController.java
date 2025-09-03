package com.example.couponapi.Controller;

import com.example.couponapi.binding.Coupon;
import com.example.couponapi.service.CouponPricingService;
import com.example.couponapi.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponPricingService pricingService;


    // Create or Update
    @PostMapping
    public ResponseEntity<String> createCoupon(@RequestBody Coupon coupon) {
        String status = couponService.upsert(coupon);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable Long id) {
        Coupon coupon = couponService.getById(id);
        if (coupon != null) {
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get all
    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.getAll();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    // Update existing
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        coupon.setId(id);
        String status = couponService.upsert(coupon);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/apply")
    public ResponseEntity<Double> applyCoupon(
            @RequestParam String code,
            @RequestParam double cartTotal) {

        Coupon coupon = couponService.findByCode(code);
        if (coupon == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        double finalAmount = pricingService.applyCoupon(coupon, cartTotal);
        return new ResponseEntity<>(finalAmount, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable Long id) {
        String status = couponService.deleteById(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
