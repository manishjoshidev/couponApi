package com.example.couponapi.service;

import com.example.couponapi.binding.Coupon;
import java.util.List;

public interface CouponService {
    String upsert(Coupon coupon);
    Coupon getById(Long id);
    List<Coupon> getAll();
    String deleteById(Long id);
    Coupon findByCode(String code);

}
