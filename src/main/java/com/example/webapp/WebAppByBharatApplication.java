package com.example.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.webapp.exception.RestException;
import com.example.webapp.pojo.TestPojo;
import com.example.webapp.repository.RolesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@SpringBootApplication(exclude = { JacksonAutoConfiguration.class })
@RestController
@EnableJpaRepositories
@EnableScheduling
@EnableEurekaClient
public class WebAppByBharatApplication implements ApplicationRunner {
	Logger log = LoggerFactory.getLogger(WebAppByBharatApplication.class);

	@Autowired
	GsonBuilder gsonBuilder;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	WebClient.Builder webClientBuilder;

	public static void main(String[] args) {
		SpringApplication.run(WebAppByBharatApplication.class, args);
	}

	@Autowired
	RolesRepository rolesRepository;

	
	  
	@Autowired
	ResourceBundleMessageSource messageSource;

	@GetMapping(value = "/")
	public List<String> isAppUpp() {
		List<String> names = rolesRepository.getAllNames();
		names.add("No of users present is: " + rolesRepository.count());

		return names;
	}

	@GetMapping(value = "/gsonTest")
	public TestPojo gsonTest() throws JsonMappingException, JsonProcessingException {

		TestPojo pojo = new TestPojo();
		pojo.setName("bharat");
		pojo.setRollNo(null);
		log.info("pojo-====="+pojo);


		
		//Gson way to convert Java Object to JSON
		String gsonwithoutNull = new Gson().toJson(pojo);
		
		//Jackson way to convert Java Object to JSON
		ObjectMapper mapper=new ObjectMapper();
		String jsonString = mapper.writeValueAsString(pojo);
		
		//Gson way to convert JSON to Java Object
		TestPojo gsonJsonMapper=new Gson().fromJson(gsonwithoutNull, TestPojo.class);
		
		//Jackson ObjectMapper  way to convert JSON to Java Object
		ObjectMapper mapper1=new ObjectMapper();
		TestPojo jacksonJsonMapper=mapper1.readValue(gsonwithoutNull, TestPojo.class); 
		

		
		
		//*********************************************************************************
		
		System.out.println("gsonwithoutNull======" + gsonwithoutNull);
		String gsonwithNull = gsonBuilder.create().toJson(pojo);
		System.out.println("gsonwithNull===" + gsonwithNull);
		
		return pojo;
	}

	@GetMapping(value = "/restemplateTest")
	public TestPojo checkRestTemplateResponse() {

		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<TestPojo> restCall = new HttpEntity<TestPojo>(header);

		TestPojo testPojo = restTemplate
				.exchange("http://localhost:1010/gsonTest", HttpMethod.GET, restCall, TestPojo.class).getBody();

		return testPojo;
	}

	@GetMapping(value = "/testglobalexceptionhandler")
	public String testException() throws RestException {
		throw new RestException(HttpStatus.BAD_REQUEST, "this is a bad request");

	}

	
	 @RequestMapping(value = "/upload", method = RequestMethod.POST, 
		      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		   public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		      File convertFile = new File("/C://Users//Bharat.S//Desktop//oracle//"+file.getOriginalFilename());
		      convertFile.createNewFile();
		      FileOutputStream fout = new FileOutputStream(convertFile);
		
		      fout.write(file.getBytes());
		      fout.close();
		      return "File is upload successfully";
		   }
	 
	 
	  @RequestMapping(value = "/download", method = RequestMethod.GET) 
	   public ResponseEntity<Object> downloadFile() throws IOException  {
	      String filename = "/C://Users//Bharat.S//Desktop//oracle//CPO Honda IN Login URL.docx";
	      File file = new File(filename);
	      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	      HttpHeaders headers = new HttpHeaders();
	      
	      headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
	      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	      headers.add("Pragma", "no-cache");
	      headers.add("Expires", "0");
	      
	      ResponseEntity<Object> 
	      responseEntity = ResponseEntity.ok().headers(headers).contentLength(
	         file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
	      
	      return responseEntity;
	   }
	  
//	  @Value(value = "${welcome.text}")
//	  public String value;

	  
	  
		@GetMapping(value = "/testLocale")
		public String testMessageBundle(@RequestParam(value = "language",required = false) String language)  {
			if(language!=null) {
				return messageSource.getMessage("welcome.text", null, new Locale(language));
			}
			return messageSource.getMessage("welcome.text", null, null);
		}
		
		
//		@Value("${microservice.EurekaClient-1.endpoints.endpoint.uri}")
//		String eurekaClientAPI;
		
		@GetMapping("/getWebclientResponse")
		public String webClient() {
			
			return webClientBuilder.build().get().uri("http://EurekaClient-1/eurekaClient1").retrieve().bodyToMono(String.class).block();
		}
	  
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("application runner interface method called before starting th app ........");

	}

}
