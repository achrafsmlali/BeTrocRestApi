package com.betroc.controller;

import com.betroc.model.ExchangeAd;
import com.betroc.repository.ExchangeAdRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exchangeAds")
public class ExchangesAdController extends AdBaseController<ExchangeAd,ExchangeAdRepository>{
}
