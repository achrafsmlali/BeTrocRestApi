package com.betroc.repository;


import com.betroc.model.ExchangeAd;
import com.betroc.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ExchangeAdRepository extends AdvertisementBaseRepository<ExchangeAd>{

    List<ExchangeAd> findAllByUserAndValidated(Optional<User> usr, boolean validated);

    @Query("Select a From ExchangeAd a where a.validated =true and  a.latitude IS NOT NULL order by (pow((a.latitude - ?2),2) + pow((a.longitude - ?1),2)) ASC")
    Page<ExchangeAd> getAllByDistanceAndValidated(Pageable pageable, Double longitude , Double latitude);
}
