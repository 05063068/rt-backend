package com.rottentomatoes.movieapi.domain.model.ems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.rottentomatoes.movieapi.domain.model.Affiliate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmsAffiliateModel extends EmsModel<Affiliate, EmsAffiliateModel> {
    protected String videoId;
    protected String url;
    protected EmsAffiliateHostModel host;
    
    // Affiliate name strings are used in the front end to select icons, and such, so preserve historical naming
    private static final Map<String, String> affiliateNameMap = new HashMap<>();
    private static final Map<String, String> affiliateIdMap = new HashMap<>();
    static int lastUsedAffiliateId = 1;

    
    static {
        affiliateNameMap.put("Amazon.com", "Amazon");
        affiliateNameMap.put("Itunes Store", "Itunes");
        // should get this from an API, but hard coded, for now
        affiliateIdMap.put("Fandango Now", "01");
        affiliateIdMap.put("VUDU", "02");
        affiliateIdMap.put("TNT", "03");
        affiliateIdMap.put("YouTube", "04");
        affiliateIdMap.put("iTunes Store", "05");
        affiliateIdMap.put("Amazon", "06");
        affiliateIdMap.put("HBO", "07");
        affiliateIdMap.put("Netflix", "08");
        affiliateIdMap.put("unknown", "08");
    }
    
    public String getIdForAffiliate(String affiliateName) {
        String affiliateId = affiliateIdMap.get(affiliateName);
        if (affiliateId == null) {
            affiliateId = affiliateIdMap.get("unknown");
        }
        return affiliateId;
    }

    @Override
    public Affiliate convert(EmsAffiliateModel m) {
        Affiliate a = new Affiliate();
        a.setUrl(m.url);
        String affiliateName = m.host.name;
        if (affiliateNameMap.get(affiliateName) != null) {
            affiliateName = affiliateNameMap.get(affiliateName);
        }
        a.setId(m.videoId + getIdForAffiliate(affiliateName));  // existing api uses first two digits to identify affiliate vendor
        a.setAffiliateName(affiliateName);
        String specialStatus = "";
        if (affiliateName.equals("Netflix")) {
            specialStatus = "streaming";
        }
        a.setSpecialStatus(specialStatus);
        return a;
    }
    
}
