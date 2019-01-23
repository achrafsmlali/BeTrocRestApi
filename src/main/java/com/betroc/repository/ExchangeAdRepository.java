package com.betroc.repository;


import com.betroc.model.ExchangeAd;
import com.betroc.model.User;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ExchangeAdRepository extends AdvertisementBaseRepository<ExchangeAd>{

    List<ExchangeAd> findAllByUser(Optional<User> usr);

    @Query("Select a From ExchangeAd a where a.latitude IS NOT NULL order by (pow((a.latitude - ?2),2) + pow((a.longitude - ?1),2)) ASC")
    Page<ExchangeAd> getAllByDistance(Pageable pageable, Double longitude , Double latitude);
}
