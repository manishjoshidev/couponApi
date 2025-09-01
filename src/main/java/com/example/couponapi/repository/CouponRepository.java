package com.example.couponapi.repository;

import com.example.couponapi.binding.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    boolean existsByCode(String code);
    Optional<Coupon> findByCode(String code);
}
