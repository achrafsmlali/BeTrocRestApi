package com.betroc.repository;

import com.betroc.model.DonationAd;

import javax.transaction.Transactional;

@Transactional
public interface DonationAdRepository extends AdvertisementBaseRepository<DonationAd> {
}
