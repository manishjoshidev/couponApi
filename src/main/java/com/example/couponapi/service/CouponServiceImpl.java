package com.example.couponapi.service;

import com.example.couponapi.binding.Coupon;
import com.example.couponapi.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public String upsert(Coupon coupon) {
        if (couponRepository.existsByCode(coupon.getCode()) && coupon.getId() == null) {
            return "Coupon code already exists";
        }
        couponRepository.save(coupon);
        return "Coupon saved successfully";
    }

    @Override
    public Coupon getById(Long id) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        return optionalCoupon.orElse(null);
    }

    @Override
    public List<Coupon> getAll() {
        return couponRepository.findAll();
    }

    @Override
    public String deleteById(Long id) {
        if (couponRepository.existsById(id)) {
            couponRepository.deleteById(id);
            return "Coupon deleted successfully";
        }
        return "Coupon not found";
    }
    @Override
    public Coupon findByCode(String code) {
        return couponRepository.findByCode(code).orElse(null);
    }
}
