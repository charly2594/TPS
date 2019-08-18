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
	public Object testPlayground(@PathVariable("cardName") String cardName){

        LinkedHashMap[] arrayResponse =  this.restTemplate.getForObject("https://db.ygoprodeck.com/api/v5/cardinfo.php?fname={cardName}", LinkedHashMap[].class, cardName);
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
    @RequestMapping("/search={cardName}")
    public List<CatalogCard> cardSearch(@PathVariable("cardName") String cardName){
	    /*
        //db.yugioh API request:
        LinkedHashMap[] rawCardDataList =  this.restTemplate.getForObject("https://db.ygoprodeck.com/api/v5/cardinfo.php?fname={cardName}", LinkedHashMap[].class, cardName);

        //card object instances:
        List<CatalogCard> cardList = new LinkedList<CatalogCard>();
        for (LinkedHashMap rawCardData : rawCardDataList){
            CatalogCard cardInstance = new CatalogCard(rawCardData);
            cardList.add(cardInstance);
            //System.out.println(cardInstance.getImage_url());
        }
        return cardList;
        */

        List<CatalogCard> cardList = new LinkedList<CatalogCard>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            String url_api_request = "https://db.ygoprodeck.com/api/v5/cardinfo.php?fname="+cardName;
            ResponseEntity<Object> responseEntity = restTemplate.exchange(url_api_request, HttpMethod.GET,entity,Object.class);

            System.out.println(responseEntity.getClass());
            System.out.println(responseEntity.getBody().getClass());
            ArrayList<LinkedHashMap> rawCardDataList = (ArrayList<LinkedHashMap> ) responseEntity.getBody();


            //card object instances:

            for (LinkedHashMap rawCardData : rawCardDataList){
                CatalogCard cardInstance = new CatalogCard(rawCardData);
                cardList.add(cardInstance);
                //System.out.println(cardInstance.getImage_url());
            }
            return cardList;


        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }


    }



	}

