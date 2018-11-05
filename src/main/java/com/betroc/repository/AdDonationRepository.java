package com.betroc.repository;

import com.betroc.model.AdDonation;

import javax.transaction.Transactional;

@Transactional
public interface AdDonationRepository extends AdvertisementBaseRepository<AdDonation> {
}
