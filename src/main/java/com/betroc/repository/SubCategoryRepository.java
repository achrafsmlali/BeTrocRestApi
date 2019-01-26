package com.betroc.repository;

import com.betroc.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    SubCategory findByImgNameStartingWith(String startName);

    SubCategory findAllById(long category);
}
