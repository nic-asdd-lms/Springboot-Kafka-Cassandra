package org.igot.controller;

import java.util.Map;
import org.igot.model.ApiResponse;
import org.igot.service.OrgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/org")
public class OrgController {

	@Autowired
	private OrgServiceImpl orgService;


	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createOrg(@RequestBody Map<String, Object> orgRequest) throws Exception {
		ApiResponse response = orgService.createOrg(orgRequest);
		return new ResponseEntity<>(response, response.getResponseCode());

	}

	@GetMapping("/list/{parentMapId}")
	public ResponseEntity<ApiResponse> listOrg(@PathVariable("parentMapId") String parentMapId) {
		ApiResponse response = orgService.listOrg(parentMapId);
		return new ResponseEntity<>(response, response.getResponseCode());
	}




}