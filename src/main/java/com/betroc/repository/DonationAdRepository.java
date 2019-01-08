package com.betroc.repository;

import com.betroc.model.DonationAd;
import com.betroc.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface DonationAdRepository extends AdvertisementBaseRepository<DonationAd> {

    List<DonationAd> findAllByUser(Optional<User> usr);

}
