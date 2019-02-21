package com.betroc.controller;

import com.betroc.model.DonationAd;
import com.betroc.model.State;
import com.betroc.repository.AdvertisementSpecification;
import com.betroc.repository.DonationAdRepository;
import com.betroc.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/donationAds")
public class DonationAdController extends AdBaseController<DonationAd,DonationAdRepository>{

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @GetMapping("/closet/lat/{lat}/long/{lng}")
    public Page getEchangeAdsByDistance(@PathVariable("lat") Double lat, @PathVariable("lng") Double lng ,@PageableDefault(size = 10, sort = "id") Pageable pageable){
        return this.repository.getAllByDistance(pageable ,lng,lat);
    }

    @GetMapping("/search")
    public Page search(@RequestParam(required=false) String key, @RequestParam(required=false) Long category, @RequestParam(required=false) State state,@PageableDefault(size = 10, sort = "id") Pageable pageable){

        DonationAd adFilter = new DonationAd();

        if (category != null)
            adFilter.setSubCategory(subCategoryRepository.findAllById(category));

        adFilter.setState(state);
        adFilter.setTitle(key);

        Specification<DonationAd> spec = new AdvertisementSpecification<DonationAd>(adFilter);

        return repository.findAll(spec,pageable);
    }

}
