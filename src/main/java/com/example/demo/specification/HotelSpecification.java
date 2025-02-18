package com.example.demo.specification;

import com.example.demo.model.Hotel;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class HotelSpecification {
    public static Specification<Hotel> searchHotels(String name, String brand, String city, String county, String amenity) {
        return (Root<Hotel> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (brand != null && !brand.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%"));
            }
            if (city != null && !city.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("address").get("city")), "%" + city.toLowerCase() + "%"));
            }
            if (county != null && !county.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("address").get("county")), "%" + county.toLowerCase() + "%"));
            }
            if (amenity != null && !amenity.isEmpty()) {
                Join<Object, Object> amenitiesJoin = root.join("amenities", JoinType.INNER);
                predicates.add(cb.like(cb.lower(amenitiesJoin.get("name")), "%" + amenity.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}


