package com.ugurhalil.delivery.repository;

import com.ugurhalil.common.entity.Delivery;
import com.ugurhalil.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findDeliveryByUser(User user);
}
