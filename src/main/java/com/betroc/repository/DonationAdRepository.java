package com.betroc.repository;

import com.betroc.model.DonationAd;
import com.betroc.model.User;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface DonationAdRepository extends AdvertisementBaseRepository<DonationAd> {

    List<DonationAd> findAllByUser(Optional<User> usr);

    @Query("Select a From DonationAd a where a.latitude IS NOT NULL order by (pow((a.latitude - ?2),2) + pow((a.longitude - ?1),2)) ASC")
    List<DonationAd> getAllByDistance(Double longitude ,Double latitude);
}
