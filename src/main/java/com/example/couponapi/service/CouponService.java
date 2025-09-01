package com.example.couponapi.service;

import com.example.couponapi.binding.Coupon;
import java.util.List;

public interface CouponService {
    String upsert(Coupon coupon);
    Coupon getById(Integer id);
    List<Coupon> getAll();
    String deleteById(Integer id);
    Coupon findByCode(String code);

}
