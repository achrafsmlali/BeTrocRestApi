package com.betroc.repository;

import com.betroc.model.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

import java.util.List;

@NoRepositoryBean
public interface AdvertisementBaseRepository <T extends Advertisement> extends JpaRepository<T ,Long> , JpaSpecificationExecutor<T> {

    Page<T> findAllByValidated(Pageable pageable, boolean validated);

    Page<T> findAllByCategory_TitleAndValidated(Pageable pageable, String title, boolean validated);

    Page<T> findAll(@Nullable Specification<T> var1, Pageable pageable);

}
