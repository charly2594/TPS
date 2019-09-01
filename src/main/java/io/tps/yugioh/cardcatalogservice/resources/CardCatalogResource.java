package io.tps.yugioh.cardcatalogservice.resources;

import java.lang.reflect.Array;
import java.util.*;


import io.tps.yugioh.cardcatalogservice.models.CatalogCard;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/catalog")
public class CardCatalogResource {
    private final RestTemplate restTemplate;


    public CardCatalogResource(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping("/{cardName}")
    public Object testPlayground(@PathVariable("cardName") String cardName) {

        LinkedHashMap[] arrayResponse = this.restTemplate.getForObject("https://db.ygoprodeck.com/api/v5/cardinfo.php?fname={cardName}", LinkedHashMap[].class, cardName);
        /*
        System.out.println(arrayResponse.getClass());
        System.out.println(arrayResponse[1].getClass());
        System.out.println(arrayResponse[1].get("name"));
        */
        //Testing CatalogCard custom class:
        CatalogCard cardInstance = new CatalogCard(arrayResponse[1]);
        System.out.println(cardInstance);
        return arrayResponse[1];
    }

    @RequestMapping("/fsearch={cardName}")
    public List<CatalogCard> cardFuzzySearch(@PathVariable("cardName") String cardName) {

        // db.yugioh API request fuzzy search:
        List<CatalogCard> cardList = new LinkedList<CatalogCard>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            String url_api_request = "https://db.ygoprodeck.com/api/v5/cardinfo.php?fname=" + cardName;
            ResponseEntity<Object> responseEntity = restTemplate.exchange(url_api_request, HttpMethod.GET, entity, Object.class);

            ArrayList<LinkedHashMap> rawCardDataList = (ArrayList<LinkedHashMap>) responseEntity.getBody();


            //card object instances:

            for (LinkedHashMap rawCardData : rawCardDataList) {
                CatalogCard cardInstance = new CatalogCard(rawCardData);
                cardList.add(cardInstance);
                //System.out.println(cardInstance.getImage_url());
            }
            System.out.println("fsearch made");
            return cardList;


        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }


    }
    @RequestMapping("/search={cardNameOrId}")
    public CatalogCard cardSearch(@PathVariable("cardNameOrId") String cardName) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            String url_api_request = "https://db.ygoprodeck.com/api/v5/cardinfo.php?name=" + cardName;
            ResponseEntity<Object> responseEntity = restTemplate.exchange(url_api_request, HttpMethod.GET, entity, Object.class);
            LinkedHashMap rawCardDataList = (LinkedHashMap) ( (ArrayList) responseEntity.getBody()).get(0);


            //card object instances:
            CatalogCard cardInstance = new CatalogCard(rawCardDataList);
            return cardInstance;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }


    }
    @RequestMapping("/IdToImageURL={Id}")
    public String IdToImageURL(@PathVariable("Id") String Id){
        CatalogCard catalogCard = this.cardSearch(Id);
        return catalogCard.getImage_url();
    }
    @RequestMapping("/IdToName={Id}")
    public String IdToName(@PathVariable("Id") String Id){
        CatalogCard catalogCard = this.cardSearch(Id);
        return catalogCard.getName();
    }
    @RequestMapping("/IdToDesc={Id}")
    public String IdToDesc(@PathVariable("Id") String Id){
        CatalogCard catalogCard = this.cardSearch(Id);
        return catalogCard.getDesc();
    }
}
