package com.example.SpringbootProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
public class MyController {
	
	final String username="reshamguru123@gmail.com";
	final String api_key="M8opIStrIui2S0wulnEz90FC";
	final String url = "https://resham1.atlassian.net/rest/api/3/issue/";

	@GetMapping("/getHome")
    public String getHome() throws UnirestException {

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
		HttpResponse<String> response = Unirest.delete(url + "FP-24")
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
//	private final OauthTokenService oauthTokenService;
//
//    public GoogleAuthRedirectController(OauthTokenService oauthTokenService) {
//        this.oauthTokenService = oauthTokenService;
//    }
//	@RequestMapping("/oauth2/callback/google")
//	public String callbackUrl(
//	        HttpServletRequest request,
//	        HttpSession httpSession) {
//	    String code = request.getParameter("code");
//	    String accessDenied = request.getParameter("access_denied") == null
//	            ? "" : request.getParameter("access_denied");
//	    if (!accessDenied.isBlank()) throw new AccessDeniedException("Authorization from google failed");
//	    String error = request.getParameter("error") == null
//	            ? "" : request.getParameter("error");
//	    if (!error.isBlank()) throw new AccessDeniedException("Authorization from google failed");
//	    String[] scopes = request.getParameter("scope").split(" ");
//	    if (code.isBlank()) throw new AccessDeniedException("Authorization from google failed");
//	    String scopeWithCalendarPermission =
//	            Stream.of(scopes)
//	                    .filter(s -> s.contains("drive"))
//	                    .findFirst()
//	                    .orElseThrow(() -> new AccessDeniedException("You must have to allow calendar data to be accessed."));
//	    httpSession
//	            .setAttribute(SessionKey.GOOGLE_OAUTH_TOKEN.toString(),
//	                    oauthTokenService.fetchToken(code, scopeWithCalendarPermission)
//	            );
//	    return "redirect:/agenda";
//	}
//	
//	public String fetchToken(String code, String scope) {
//	    final String uri = "https://accounts.google.com/o/oauth2/token";
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//	    String clientCredentials = Base64.getEncoder().encodeToString((CLIENT_ID+":"+CLIENT_SECRET).getBytes());
//	    headers.add("Authorization", "Basic "+clientCredentials);
//	    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//	    requestBody.add("code", code);
//	    requestBody.add("grant_type", "authorization_code");
//	    requestBody.add("redirect_uri", "http://localhost:8080/oauth2/callback/google");
//	    requestBody.add("scope", scope);
//
//	    HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
//
//	    ResponseEntity<OauthResponse> response = restTemplate.exchange(uri, HttpMethod.POST, formEntity,  OauthResponse.class);
//	    return response.getBody().getAccess_token();
//	}
//	public DriveFiles getDriveFiles(String accessToken) {
//	    String requestUri = "https://www.googleapis.com/drive/v3/files";
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//	    headers.setContentType(MediaType.APPLICATION_JSON);
//	    headers.add("Authorization", "Bearer " + accessToken);
//
//	    HttpEntity request = new HttpEntity(headers);
//	    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
//	            new MappingJackson2HttpMessageConverter();
//	    restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
//	    ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, request, String.class);
//
//	    Gson gson = new Gson();
//	    DriveFiles driveFiles = gson.fromJson(response.getBody(), DriveFiles.class);
//
//	    return driveFiles;
//	}
//	public DriveFiles getDriveFiles(String accessToken) {
//	    String requestUri = "https://www.googleapis.com/drive/v3/files";
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//	    headers.setContentType(MediaType.APPLICATION_JSON);
//	    headers.add("Authorization", "Bearer " + accessToken);
//
//	    HttpEntity request = new HttpEntity(headers);
//	    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
//	            new MappingJackson2HttpMessageConverter();
//	    restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
//	    ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, request, String.class);
//
//	    Gson gson = new Gson();
//	    DriveFiles driveFiles = gson.fromJson(response.getBody(), DriveFiles.class);
//
//	    return driveFiles;
//	}
   
    @RequestMapping("/download")
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
    }
//    // MainController.java
//    @GetMapping("/download/{id}")
//    public void download(@PathVariable String id) throws IOException, GeneralSecurityException {
//    	HttpResponse<JsonNode> response = Unirest.post(url + "FP-27/attachments")
//		         .basicAuth(username, api_key)
//		         .header("Accept", "application/json")
//		         .header("X-Atlassian-Token", "no-check")
//		         .field("file", new File(fileId))
//		         .asJson();
//    	downloadFile(id, response.getOutputStream();
//    }
//    @RequestMapping("/DownloadFileFromGdrive")
//    public String DownloadFileFromGdrive() throws IOException, GeneralSecurityException, UnirestException {
//        
//        String fileId = "1mB8ur76r5gxBtkIhiRyejerHtg04yUvY";
//        
//        OutputStream outputstream = new FileOutputStream(fileId);
//        service.files().get(fileId)
//        .executeMediaAndDownloadTo(outputstream);   
//        
//        HttpResponse<JsonNode> response = Unirest.post(url + "FP-27/attachments")
//		         .basicAuth(username, api_key)
//		         .header("Accept", "application/json")
//		         .header("X-Atlassian-Token", "no-check")
//		         .field("file", new File(fileId))
//		         .asJson();
//
//        int responseCode = response.getStatus();
//		if(responseCode == 200) {
//			return ("Attached the document in jira from the Google Drive successfully..." + response.getBody());
//		}
//		else {
//			return ("Please check the Issue id or key\n" + response.getBody());
//		}        
//    }
}