package com.blog.car.repository;

import com.blog.car.domain.Car;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findByMark(String mark);
    Car findById(long id);
}
