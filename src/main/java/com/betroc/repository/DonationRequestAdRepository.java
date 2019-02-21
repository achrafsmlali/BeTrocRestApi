package com.betroc.repository;

import com.betroc.model.DonationRequestAd;
import com.betroc.model.User;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface DonationRequestAdRepository extends AdvertisementBaseRepository<DonationRequestAd>{

    List<DonationRequestAd> findAllByUserAndValidated(Optional<User> usr, boolean validated);

}
