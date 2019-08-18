package io.tps.yugioh.cardcatalogservice.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CatalogCard {
	
	private String name;
	private String id;
	private String desc;
	private String image_url;

    public CatalogCard(LinkedHashMap rawCardData){
        this.name = (String) rawCardData.get("name");
        this.id = (String) rawCardData.get("id");
        this.desc = (String) rawCardData.get("desc");
        this.image_url = (String) ((LinkedHashMap) ((List) rawCardData.get("card_images")).get(0)).get("image_url");
    }

	public CatalogCard(String name, String desc, String id, String image_url) {
		this.name = name;
		this.id = id;
		this.desc = desc;
		this.image_url = image_url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
