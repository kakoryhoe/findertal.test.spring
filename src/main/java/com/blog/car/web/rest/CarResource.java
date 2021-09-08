package com.blog.car.web.rest;

import com.blog.car.domain.Car;
import com.blog.car.domain.Comment;
import com.blog.car.service.CarService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CarResource {

    private final Logger log = LoggerFactory.getLogger(CarResource.class);

    @Autowired
    CarService carService;

    @GetMapping("/cars")
    public List<Car> getCars() {
        List<Car> cars = carService.getCarsWithoutComment();
        return cars;
    }

    @GetMapping("/car/{carId}")
    public Car getCar(@PathVariable long carId) {
        Car car = carService.getCarWithoutComment(carId);
        return car;
    }

    @GetMapping("/car/{carId}/comments")
    public Car getCars(@PathVariable long carId) {
        Car car = carService.getCar(carId);
        return car;
    }

    @PostMapping("/car/{carId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCarComment(@PathVariable long carId, @RequestBody Comment comment) {
        carService.addComment(carId, comment);
    }
}
