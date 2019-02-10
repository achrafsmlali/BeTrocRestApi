package com.betroc.controller;

import com.betroc.model.ExchangeAd;
import com.betroc.model.State;
import com.betroc.repository.AdvertisementSpecification;
import com.betroc.repository.ExchangeAdRepository;
import com.betroc.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exchangeAds")
public class ExchangesAdController extends AdBaseController<ExchangeAd,ExchangeAdRepository>{

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @GetMapping("/closet/lat/{lat}/long/{lng}")
    public ResponseEntity<?> getEchangeAdsByDistance(@PathVariable("lat") Double lat, @PathVariable("lng") Double lng , @PageableDefault(size = 10, sort = "id") Pageable pageable){
        Page page = this.repository.getAllByDistanceAndValidated(pageable,lng,lat);

        if (page != null)
            return ResponseEntity.ok().body(page);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required=false) String key, @RequestParam(required=false) Long category, @RequestParam(required=false) State state,@PageableDefault(size = 10, sort = "id") Pageable pageable){

        ExchangeAd adFilter = new ExchangeAd();

        if (category != null)
            adFilter.setSubCategory(subCategoryRepository.findAllById(category));

        adFilter.setState(state);
        adFilter.setTitle(key);

        Specification<ExchangeAd> spec = new AdvertisementSpecification<ExchangeAd>(adFilter);

        Page page = repository.findAll(spec,pageable);
        if (page != null)
            return ResponseEntity.ok().body(page);
        else
            return ResponseEntity.notFound().build();

    }
}
