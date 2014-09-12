package it.inserpio.neo4art.cloudfoundry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.util.EnvironmentAccessor;

public class CloudFoundryServiceManager {
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getService(String serviceName) {
		ObjectMapper objectMapper = new ObjectMapper();
		EnvironmentAccessor environment = new EnvironmentAccessor();
		String servicesString = environment.getEnvValue("VCAP_SERVICES");
		try {
			Map<String, List<Map<String,Object>>> rawServices = objectMapper.readValue(servicesString, Map.class);

			List<Map<String,Object>> services = new ArrayList<Map<String,Object>>();
			for (Map.Entry<String, List<Map<String,Object>>> entry : rawServices.entrySet()) {
				services.addAll(entry.getValue());
			}

			for (Map<String, Object> service : services) {
				if (service.get("name").equals("graphenedb-neo4art-sdn301")) {
					return service;
				}
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
