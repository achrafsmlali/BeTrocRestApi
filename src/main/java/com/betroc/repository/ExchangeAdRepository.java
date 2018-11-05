package com.betroc.repository;


import com.betroc.model.ExchangeAd;

import javax.transaction.Transactional;

@Transactional
public interface ExchangeAdRepository extends AdvertisementBaseRepository<ExchangeAd>{
}
