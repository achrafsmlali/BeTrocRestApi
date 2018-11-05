package com.betroc.repository;

import com.betroc.model.DonationRequestAd;

import javax.transaction.Transactional;

@Transactional
public interface DonationRequestAdRepository extends AdvertisementBaseRepository<DonationRequestAd>{
}
