package com.betroc.repository;

import com.betroc.model.Advertisement;

import javax.transaction.Transactional;

@Transactional
public interface AdvertisementRepository extends AdvertisementBaseRepository<Advertisement> {

    void deleteByUser_Id(long id);
}
