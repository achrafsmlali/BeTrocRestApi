package com.betroc.repository;

import com.betroc.model.AdDonationRequest;

import javax.transaction.Transactional;

@Transactional
public interface AdDonationRequestRepository extends AdvertisementBaseRepository<AdDonationRequest>{
}
