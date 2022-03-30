package com.example.SpringbootProject;

import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Controller
public class MyController {
	
	final String username="reshamguru123@gmail.com";
	final String api_key="6gxS3ub047gt1uhIUefr1DC1";
	final String url = "https://resham1.atlassian.net/rest/api/3/issue/";

	@GetMapping("/getTicketData")
    public String getTicketData() throws UnirestException {

		HttpResponse<JsonNode> response = Unirest.get(url + "FP-25")
				  .basicAuth(username, api_key)
				  .header("Accept", "application/json")
				  .asJson();

		int responseCode = response.getStatus();
		if(responseCode == 200) {
			return ("Check the data\n" + response.getBody());	
		}
		else {
			return ("Please check the Issue id or key\n" + response.getBody());
		}
    }

	@RequestMapping("/createIssue")
	public String createIssue() throws UnirestException {
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
		
		HttpResponse<JsonNode> response = Unirest.post(url)
				.basicAuth(username, api_key)
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.body(dataTable)
				.asJson();

		return ("Issue is created successfully. Please check the created issue on Jira cloud. \n" + response.getBody());
	}
	
	@RequestMapping("/updateIssue")
	public String updateIssue() throws UnirestException {
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
							content01.put("text", "Order entry fails when selecting supplier.");
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

		HttpResponse<JsonNode> response = Unirest.put(url + "FP-30")
				.basicAuth(username, api_key)
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.body(dataTable)
				.asJson();

		int responseCode = response.getStatus();	
		if(responseCode == 204) {
			return ("Updated the issue successfully. Please check the updated issue on Jira cloud");
		}
		else {
			return ("Please check the Issue id or key\n" + response.getBody());
		}
	}
	
	@RequestMapping("/deleteIssue")
	public String deleteIssue() throws UnirestException {
		HttpResponse<String> response = Unirest.delete(url + "FP-60")
				  .basicAuth(username, api_key)
				  .asString();

		int responseCode = response.getStatus();
		if(responseCode == 204) {
			System.out.println(response.getBody());
			return "Deleted the issue successfully. Please check the once on Jira cloud";
		}
		else {
			return "Please check the Issue id or key";
		}
	}
	@RequestMapping("/addAttachmentToJira")
	public String addAttachmentToJira() throws UnirestException {
		HttpResponse<JsonNode> response = Unirest.post(url + "FP-30/attachments")
		         .basicAuth(username, api_key)
		         .header("Accept", "application/json")
		         .header("X-Atlassian-Token", "no-check")
		         .field("file", new File("newFile.txt"))
		         .asJson();

		 int responseCode = response.getStatus();
		 if(responseCode == 200) {
			return ("Attached file added to jira successfully..." + response.getBody());
		}
		else {
			return "Please check the Issue id or key";
		}	
	}
	
/*	  @RequestMapping("/download")
    public String downloadFile() throws IOException, GeneralSecurityException, UnirestException {
      
      String fileId = "1mB8ur76r5gxBtkIhiRyejerHtg04yUvY";

      OutputStream outputstream = new FileOutputStream(fileId);
      GoogleDriveManager.getInstance().files().get(fileId).executeMediaAndDownloadTo(outputstream);

      HttpResponse<JsonNode> response = Unirest.post(url + "FP-27/attachments")
		         .basicAuth(username, api_key)
		         .header("Accept", "application/json")
		         .header("X-Atlassian-Token", "no-check")
		         .field("file", new File(fileId))
		         .asJson();

      	int responseCode = response.getStatus();
		if(responseCode == 200) {
			return ("Attached the document in jira from the Google Drive successfully..." + response.getBody());
		}
		else {
			return ("Please check the Issue id or key\n" + response.getBody());
		}
    }*/
	  
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
	     return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}