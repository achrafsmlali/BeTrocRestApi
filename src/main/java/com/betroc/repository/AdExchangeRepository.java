package com.betroc.repository;


import com.betroc.model.AdExchange;

import javax.transaction.Transactional;

@Transactional
public interface AdExchangeRepository extends AdvertisementBaseRepository<AdExchange>{
}
