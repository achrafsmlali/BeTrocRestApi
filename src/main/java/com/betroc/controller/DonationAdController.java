package com.betroc.controller;

import com.betroc.model.DonationAd;
import com.betroc.repository.DonationAdRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/donationAds")
public class DonationAdController extends AdBaseController<DonationAd,DonationAdRepository>{
}
