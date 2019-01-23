package com.betroc.controller;

import com.betroc.model.DonationRequestAd;
import com.betroc.repository.DonationRequestAdRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/DonationRequestAd")
public class DonationRequestAdController extends AdBaseController<DonationRequestAd, DonationRequestAdRepository> {
}
