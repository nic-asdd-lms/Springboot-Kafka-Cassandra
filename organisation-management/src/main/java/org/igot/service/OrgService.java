package org.igot.service;

import org.igot.model.ApiRespOrgainsation;
import org.igot.model.ApiResponse;

import java.util.Map;

public interface OrgService {
    public ApiResponse createOrg(Map<String, Object> request);
    ApiResponse listOrg(String parentMapId);
    ApiResponse orgSearch(Map<String, Object> request) throws Exception;
  //  public void initiateCreateOrgFlow(ApiRespOrgainsation registrationCode);
}
