package com.betroc.repository;

import com.betroc.model.Advertisement;
import com.betroc.model.DonationAd;
import com.betroc.model.ExchangeAd;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementSpecification <T extends Advertisement>  implements Specification<T>{

    private T adFilter;

    public AdvertisementSpecification(T adFilter) {
        super();
        this.adFilter = adFilter;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        Predicate predicate = cb.disjunction();

        List<Predicate> predicates = new ArrayList<>();


        // state only exist in Donation and Exchange Ads
        if ((adFilter instanceof DonationAd) || (adFilter instanceof ExchangeAd)){

            if(adFilter instanceof DonationAd){

                if (((DonationAd) adFilter).getState() != null)
                    predicates.add(cb.equal(root.get("state"),((DonationAd) adFilter).getState()));

            }else if(adFilter instanceof ExchangeAd){

                if (((ExchangeAd) adFilter).getState() != null)
                    predicates.add(cb.equal(root.get("state"),((ExchangeAd) adFilter).getState()));
            }

        }

        if (adFilter.getCategory() != null){
            predicates.add(cb.equal(root.get("category"),adFilter.getCategory()));
        }

        if(adFilter.getTitle() != null ){
            predicates.add(cb.or(
                    cb.like(root.get("description"),"%"+adFilter.getTitle()+"%"),
                    cb.like(root.get("title"),"%"+adFilter.getTitle()+"%")
            ));
        }


        predicate.getExpressions().add(cb.and(predicates.toArray(new Predicate[0])));

        return predicate;

    }
}
