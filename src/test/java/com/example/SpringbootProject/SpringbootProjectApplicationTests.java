package com.example.SpringbootProject;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//@WebMvcTest(value=MyController.class)
class SpringbootProjectApplicationTests {
	
	final String username="reshamguru123@gmail.com";
	final String api_key="CQLhjb6Av1AYEt26EQwNBCD1";
	final String url = "https://resham1.atlassian.net/rest/api/3/issue/";
	
	@Test
	void contextLoads() {
	}
		
	@Test
	public void getTicketDataTest() throws UnirestException {
		
		HttpResponse<String> response = Unirest.get(url + "FP-25")
				  .basicAuth(username, api_key)
				  .header("Accept", "application/json")
				  .asString();
	    assertEquals(200, response.getStatus());	   
    }
	
	@Test
	public void createIssueTest() throws UnirestException 
	{
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ObjectNode dataTable = new ObjectNode(factory);
		{
			com.fasterxml.jackson.databind.node.ObjectNode fields = dataTable.putObject("fields");
			{
				com.fasterxml.jackson.databind.node.ObjectNode project = fields.putObject("project");
				{
					project.put("key", "FP");
				}
				fields.put("summary", "Main order flow broken");		    
				com.fasterxml.jackson.databind.node.ObjectNode description = fields.putObject("description");
				{
					description.put("type", "doc");
					description.put("version", 1);
					com.fasterxml.jackson.databind.node.ArrayNode content = description.putArray("content");
					com.fasterxml.jackson.databind.node.ObjectNode content0 = content.addObject();
					{
						content0.put("type", "paragraph");
						com.fasterxml.jackson.databind.node.ArrayNode content1 = content0.putArray("content");
						com.fasterxml.jackson.databind.node.ObjectNode content01 = content1.addObject();
						{
							content01.put("text", "Order entry fails when selecting supplier.");
							content01.put("type", "text");
						}
					}
				}
				com.fasterxml.jackson.databind.node.ObjectNode issuetype = fields.putObject("issuetype");
				{
					issuetype.put("name","Story");
				} 		    
				com.fasterxml.jackson.databind.node.ObjectNode reporter = fields.putObject("reporter");
				{
					reporter.put("id", "61f77fddf51e850070836bc9");
				}
				com.fasterxml.jackson.databind.node.ObjectNode assignee = fields.putObject("assignee");
				{
					assignee.put("id", "61f77fddf51e850070836bc9");
				}
			}
		}
		Unirest.setObjectMapper(new com.mashape.unirest.http.ObjectMapper() {
			private ObjectMapper jacksonObjectMapper = new ObjectMapper();
			
			public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
						
			public <T> T readValue(String value, Class<T> valueType) {
				try {
					return jacksonObjectMapper.readValue(value, valueType);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		HttpResponse<String> response = Unirest.post(url)
				.basicAuth(username, api_key)
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.body(dataTable)
				.asString();
		
		 // Check if the status code is 201-CREATE
	    assertEquals(201, response.getStatus());
	}

	@Test
	public void updateIssueTest() throws UnirestException {
		// The dataTable definition using the Jackson library
		com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
		com.fasterxml.jackson.databind.node.ObjectNode dataTable = mapper.createObjectNode();
		{
			com.fasterxml.jackson.databind.node.ObjectNode update = dataTable.putObject("update");
			{   	    
				com.fasterxml.jackson.databind.node.ArrayNode labels = update.putArray("labels");
				com.fasterxml.jackson.databind.node.ObjectNode labels0 = labels.addObject();
				{
					labels0.put("add", "triaged");
				}
				com.fasterxml.jackson.databind.node.ObjectNode labels1 = labels.addObject();
				{
					labels1.put("remove", "newlabel");
				}
				ArrayNode summary = update.putArray("summary");
				ObjectNode summary0 = summary.addObject();
				{
					summary0.put("set", "Order entry fails when selecting supplier section.");
				}
			}
			com.fasterxml.jackson.databind.node.ObjectNode fields = dataTable.putObject("fields");
			{
				ObjectNode description = fields.putObject("description");
				{
					description.put("type", "doc");
					description.put("version", 1);
					ArrayNode content = description.putArray("content");
					ObjectNode content0 = content.addObject();
					{
						content0.put("type", "paragraph");
						ArrayNode content1 = content0.putArray("content");
						ObjectNode content01 = content1.addObject();
						{
							content01.put("text", "Order entry fails when selecting supplier1.");
							content01.put("type", "text");
						}
					}
				}	    
				ObjectNode assignee = fields.putObject("assignee");
				{
					assignee.put("id", "61f77fddf51e850070836bc9");
				}
			}
		}
		// Connect Jackson ObjectMapper to Unirest
		Unirest.setObjectMapper(new com.mashape.unirest.http.ObjectMapper() {
			private ObjectMapper jacksonObjectMapper = new ObjectMapper();
			
			public String writeValue(Object value) {
				try {
					return jacksonObjectMapper.writeValueAsString(value);

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
						
			public <T> T readValue(String value, Class<T> valueType) {
				try {
					return jacksonObjectMapper.readValue(value, valueType);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

		HttpResponse<String> response = Unirest.put(url + "FP-65")
				.basicAuth(username, api_key)
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.body(dataTable)
				.asString();

		// Check if the status code is 204-NO_CONTENT
	    assertEquals(204, response.getStatus());
	}
	
	@Test
	public void deleteIssueTest() throws UnirestException {
		HttpResponse<String> response = Unirest.delete(url + "FP-72")
				  .basicAuth(username, api_key)
				  .asString();

		// Check if the status code is 204-NO_CONTENT
	    assertEquals(204, response.getStatus());
	}
	 
	@Test
	public void addAttachmentToJiraTest() throws UnirestException {
		HttpResponse<String> response = Unirest.post(url + "FP-30/attachments")
		         .basicAuth(username, api_key)
		         .header("Accept", "application/json")
		         .header("X-Atlassian-Token", "no-check")
		         .field("file", new File("newFile.txt"))
		         .asString();
		
		 // Check if the status code is 200
	    assertEquals(200, response.getStatus());
	}
}