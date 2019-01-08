package com.betroc.repository;

import com.betroc.model.DonationRequestAd;
import com.betroc.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface DonationRequestAdRepository extends AdvertisementBaseRepository<DonationRequestAd>{

    List<DonationRequestAd> findAllByUser(Optional<User> usr);

}
