package com.blog.car.service;

import com.blog.car.domain.Car;
import com.blog.car.domain.Comment;
import com.blog.car.repository.CarRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car getCar(long id) {
        return carRepository.findById(id);
    }

    public Car getCarWithoutComment(long id) {
        Car car = carRepository.findById(id);
        car.setComments(null);
        return carRepository.findById(id);
    }

    public List<Car> getCarByMark(String mark) {
        return carRepository.findByMark(mark);
    }

    public List<Car> getCars() {
        return (List<Car>) carRepository.findAll();
    }

    public List<Car> getCarsWithoutComment() {
        List<Car> cars = (List<Car>) carRepository.findAll();
        if (cars != null && cars.size() > 0) {
            for (Car car : cars) {
                car.setComments(null);
            }
        }
        return cars;
    }

    public void addComment(long carId, Comment comment) {
        Car car = carRepository.findById(carId);
        if (car != null) {
            List<Comment> comments = car.getComments();
            if (comments != null && comments.size() > 0) {
                comments.add(comment);
            } else {
                comments = new ArrayList<>();
                comments.add(comment);
            }
            car.setComments(comments);
            carRepository.save(car);
        }
    }
}
