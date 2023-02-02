package org.igot.utils;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.igot.logger.Logger;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.igot.exception.ProjectCommonException;
import org.igot.exception.ResponseCode;
import org.igot.model.ApiResponse;
import org.igot.model.ApiRespParam;

/**
 * This class will contain all the common utility methods.
 *
 * @author Manzarul
 */
public class ProjectUtil {

    public static Logger logger = new Logger(ProjectUtil.class.getName());

    public static PropertiesCache propertiesCache;
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        propertiesCache = PropertiesCache.getInstance();
    }

    public static String getConfigValue(String key) {
        if (StringUtils.isNotBlank(System.getenv(key))) {
            return System.getenv(key);
        }
        return propertiesCache.readProperty(key);
    }

    /**
     * This method will check incoming value is null or empty it will do empty check by doing trim
     * method. in case of null or empty it will return true else false.
     *
     * @param value
     * @return
     */
    public static boolean isStringNullOREmpty(String value) {
        return (value == null || "".equals(value.trim()));
    }

    /**
     * This method will create and return server exception to caller.
     *
     * @param responseCode ResponseCode
     * @return ProjectCommonException
     */
    public static ProjectCommonException createServerError(ResponseCode responseCode) {
        return new ProjectCommonException(
                responseCode.getErrorCode(),
                responseCode.getErrorMessage(),
                ResponseCode.SERVER_ERROR.getResponseCode());
    }

    public static ProjectCommonException createClientException(ResponseCode responseCode) {
        return new ProjectCommonException(
                responseCode.getErrorCode(),
                responseCode.getErrorMessage(),
                ResponseCode.CLIENT_ERROR.getResponseCode());
    }

    public static ApiResponse createDefaultResponse(String api) {
        ApiResponse response = new ApiResponse();
        response.setId(api);
        response.setVer(Constants.API_VERSION_1);
        response.setParams(new ApiRespParam());
        response.getParams().setStatus(Constants.SUCCESS);
        response.setResponseCode(HttpStatus.OK);
        response.setTs(DateTime.now().toString());
        return response;
    }


    public enum Method {
        GET,
        POST,
        PUT,
        DELETE,
        PATCH
    }

}