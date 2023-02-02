package org.igot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.igot.core.Producer;
import org.igot.logger.Logger;
import org.igot.model.ApiOrgSearchRequest;
import org.igot.model.ApiRespOrgainsation;
import org.igot.model.ApiResponse;
import org.igot.utils.CassandraOperation;
import org.igot.utils.Constants;
import org.igot.utils.ProjectUtil;
import org.igot.utils.ServerProperties;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrgServiceImpl implements OrgService {

    private Logger log = new Logger(getClass().getName());
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OrgCreationConsumer.class);


    @Autowired
    CassandraOperation cassandraOperation;

    @Autowired
    ServerProperties configProperties;

    @Autowired
    Producer kafkaProducer;

    @Autowired
    ServerProperties serverProperties;


 /*   public ApiResponse createOrg(Map<String, Object> request) {

        ApiResponse response = ProjectUtil.createDefaultResponse(Constants.API_ORG_EXT_CREATE);

        kafkaProducer.push(serverProperties.getOrgCreationKafkaTopic(),request);

        //cassandraOperation.insertRecord(Constants.SUNBIRD_KEY_SPACE_NAME, Constants.TABLE_ORG_HIERARCHY, request);

        return response;
    }
*/

    @Override
    public ApiResponse listOrg(String parentMapId) {
        ApiResponse response = ProjectUtil.createDefaultResponse(Constants.API_ORG_LIST);


        Map<String, Object> request = new HashMap<>();
        if (StringUtils.isEmpty(parentMapId)) {
            parentMapId = Constants.SPV;
        }

        if (Constants.MINISTRY.equalsIgnoreCase(parentMapId) || Constants.STATE.equalsIgnoreCase(parentMapId)) {
            request.put(Constants.SB_ORG_TYPE, parentMapId);
        } else {
            request.put(Constants.PARENT_MAP_ID, parentMapId);
        }

        List<Map<String, Object>> existingDataList = cassandraOperation
                .getRecordsByProperties(Constants.KEYSPACE_SUNBIRD, Constants.TABLE_ORG_HIERARCHY, request, null);
        if (CollectionUtils.isNotEmpty(existingDataList)) {
            Map<String, Object> responseMap = new HashMap<String, Object>();
            responseMap.put(Constants.CONTENT, existingDataList);
            responseMap.put(Constants.COUNT, existingDataList.size());
            response.put(Constants.RESPONSE, responseMap);
        } else {
            response.setResponseCode(HttpStatus.NOT_FOUND);
            response.getParams().setErrmsg("Failed to get Org Details");
        }

        return response;
    }

    @Override
    public ApiResponse orgSearch(Map<String, Object> request) throws Exception {
        ApiResponse response = ProjectUtil.createDefaultResponse(Constants.API_ORG_EXT_SEARCH);
        try {
            String errMsg = validateOrgSearchReq(request);
            if (!StringUtils.isEmpty(errMsg)) {
                response.getParams().setErrmsg(errMsg);
                response.setResponseCode(HttpStatus.BAD_REQUEST);
                return response;
            }

            Map<String, Object> requestData = (Map<String, Object>) request.get(Constants.REQUEST);
            Map<String, Object> filters = (Map<String, Object>) requestData.get(Constants.FILTERS);
            String sbRootOrgId = (String) filters.get(Constants.SB_ROOT_ORG_ID);
            Map<String, Object> searchRequest = new HashMap<String, Object>() {
                private static final long serialVersionUID = 1L;
                {
                    put(Constants.SB_ROOT_ORG_ID, sbRootOrgId);
                }
            };

            List<Map<String, Object>> existingDataList = cassandraOperation.getRecordsByProperties(
                    Constants.KEYSPACE_SUNBIRD, Constants.TABLE_ORG_HIERARCHY, searchRequest, null);
            if (CollectionUtils.isNotEmpty(existingDataList)) {
                List<String> orgIdList = existingDataList.stream().filter(item -> !ObjectUtils.isEmpty(item))
                        .map(item -> {
                            return (String) item.get(Constants.SB_ORG_ID);
                        }).collect(Collectors.toList());
                ApiOrgSearchRequest orgSearchRequest = new ApiOrgSearchRequest();
                orgSearchRequest.getFilters().setId(orgIdList);

                Map<String, Object> orgSearchRequestBody = new HashMap<String, Object>() {
                    private static final long serialVersionUID = 1L;

                    {
                        put(Constants.REQUEST, orgSearchRequest);
                    }
                };

                /*
                Map<String, String> headers = new HashMap<String, String>();
                headers.put(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
                 String url = configProperties.getSbUrl() + configProperties.getSbOrgSearchPath();

               Map<String, Object> apiResponse = (Map<String, Object>) outboundService.fetchResultUsingPost(url,
                        orgSearchRequestBody, headers);
                if (Constants.OK.equalsIgnoreCase((String) apiResponse.get(Constants.RESPONSE_CODE))) {
                    Map<String, Object> apiResponseResult = (Map<String, Object>) apiResponse.get(Constants.RESULT);
                    response.put(Constants.RESPONSE, apiResponseResult.get(Constants.RESPONSE));
                } else {
                    response.getParams().setErrmsg("Failed to search org details");
                    response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
                }*/
            } else {
                Map<String, Object> responseMap = new HashMap<String, Object>();
                responseMap.put(Constants.COUNT, 0);
                responseMap.put(Constants.CONTENT, Collections.EMPTY_LIST);
                response.put(Constants.RESPONSE, responseMap);
            }
        } catch (Exception e) {
            log.error(e);
            response.getParams().setErrmsg(e.getMessage());
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public void initiateCreateOrgFlow(ApiRespOrgainsation org) {

        /*
        1. Insert to Cassandra      2. Insert to ES
         */

        LOGGER.info("Initiated Org Creation flow for mapid :: " + org.getMapid());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> orgMap = objectMapper.convertValue(org, Map.class);

        cassandraOperation.insertRecord(Constants.SUNBIRD_KEY_SPACE_NAME, Constants.TABLE_ORG_HIERARCHY, orgMap);



    }


    private String validateOrgSearchReq(Map<String, Object> requestData) {
        List<String> params = new ArrayList<String>();
        StringBuilder strBuilder = new StringBuilder();

        Map<String, Object> request = (Map<String, Object>) requestData.get(Constants.REQUEST);
        Map<String, Object> filters = (Map<String, Object>) request.get(Constants.FILTERS);
        if (ObjectUtils.isEmpty(filters)) {
            strBuilder.append("Filters in Request object is empty.");
            return strBuilder.toString();
        }

        if (StringUtils.isEmpty((String) filters.get(Constants.SB_ROOT_ORG_ID))) {
            params.add(Constants.SB_ROOT_ORG_ID);
        }

        if (!params.isEmpty()) {
            strBuilder.append("Invalid filters in Request. Missing params - " + params);
        }

        return strBuilder.toString();
    }
}
