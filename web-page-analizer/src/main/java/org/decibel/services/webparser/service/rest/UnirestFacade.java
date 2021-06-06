package org.decibel.services.webparser.service.rest;

import java.util.List;

import org.decibel.services.webparser.model.ImageTagsCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class UnirestFacade {
	
	private Logger logger = LoggerFactory.getLogger(UnirestFacade.class);

	public String post(String urlCommand, String body) {

		HttpResponse<JsonNode> jsonResponse = null;

		String response = null;
		try {
			jsonResponse = Unirest.post(urlCommand).header("accept", "application/json")
					.header("content-type", "application/json").body(body).asJson();

			response = jsonResponse.getBody().toString();
		} catch (UnirestException e) {
			logger.error("post(): %s", e.getMessage());
			response = "{error: " + e.getMessage() + "}";
		}

		return response;

	}

}
