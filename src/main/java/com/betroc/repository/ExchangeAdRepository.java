package com.betroc.repository;


import com.betroc.model.ExchangeAd;
import com.betroc.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface ExchangeAdRepository extends AdvertisementBaseRepository<ExchangeAd>{
    List<ExchangeAd> findAllByUser(Optional<User> usr);
}
