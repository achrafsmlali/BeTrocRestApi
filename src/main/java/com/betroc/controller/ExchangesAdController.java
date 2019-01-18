package com.betroc.controller;

import com.betroc.model.ExchangeAd;
import com.betroc.repository.ExchangeAdRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchangeAds")
public class ExchangesAdController extends AdBaseController<ExchangeAd,ExchangeAdRepository>{

    @GetMapping("/closet/lat/{lat}/long/{lng}")
    public Page getEchangeAdsByDistance(@PathVariable("lat") Double lat, @PathVariable("lng") Double lng ,@PageableDefault(size = 10, sort = "id") Pageable pageable){

        return this.repository.getAllByDistance(pageable,lng,lat);
    }
}
