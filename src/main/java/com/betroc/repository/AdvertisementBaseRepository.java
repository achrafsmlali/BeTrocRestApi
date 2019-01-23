package com.betroc.repository;

import com.betroc.model.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface AdvertisementBaseRepository <T extends Advertisement> extends JpaRepository<T ,Long> {
    Page<T> findAll(Pageable pageable);

    Page<T> findAllByCategory_Title(Pageable pageable, String title);

}
