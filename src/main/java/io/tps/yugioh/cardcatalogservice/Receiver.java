package io.tps.yugioh.cardcatalogservice;

import io.tps.yugioh.cardcatalogservice.resources.CardCatalogResource;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    //private RestTemplateBuilder restTemplateBuilder;
    //private CardCatalogResource ccr = new CardCatalogResource(restTemplateBuilder);

    @RabbitListener(queues="jsa.queue")
    public void recievedMessage(String msg) {
        System.out.println("Recieved Message: " + msg);
        //String img_url = ccr.IdToImageURL(msg);
        //System.out.println("Resource fetched: " + img_url);
    }

}