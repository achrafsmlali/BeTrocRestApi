package com.betroc.controller;

import com.betroc.model.DonationRequestAd;
import com.betroc.repository.AdvertisementSpecification;
import com.betroc.repository.DonationRequestAdRepository;
import com.betroc.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/DonationRequestAd")
public class DonationRequestAdController extends AdBaseController<DonationRequestAd, DonationRequestAdRepository> {

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required=false) String key, @RequestParam(required=false) Long category, @PageableDefault(size = 10, sort = "id") Pageable pageable){

        DonationRequestAd adFilter = new DonationRequestAd();

        if (category != null)
            adFilter.setSubCategory(subCategoryRepository.findAllById(category));

        adFilter.setTitle(key);

        Specification<DonationRequestAd> spec = new AdvertisementSpecification<DonationRequestAd>(adFilter);

        Page page = repository.findAll(spec, pageable);
        if (page != null)
            return ResponseEntity.ok().body(page);
        else
            return ResponseEntity.notFound().build();
    }
}
