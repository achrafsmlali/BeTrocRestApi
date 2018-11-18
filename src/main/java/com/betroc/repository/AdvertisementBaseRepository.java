package com.betroc.repository;

import com.betroc.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface AdvertisementBaseRepository <T extends Advertisement> extends JpaRepository<T ,Long> {

//    public T findByEmail(String email);


}
