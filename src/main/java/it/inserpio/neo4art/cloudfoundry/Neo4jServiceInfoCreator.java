package it.inserpio.neo4art.cloudfoundry;

import java.util.Map;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;

public class Neo4jServiceInfoCreator extends CloudFoundryServiceInfoCreator<Neo4jServiceInfo> {  
  public Neo4jServiceInfoCreator() {  
      super("user-provided");  
  }  

  @Override  
  @SuppressWarnings("unchecked")  
  public boolean accept(Map<String, Object> serviceData) {  
      Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");  
      String uri = (String) credentials.get("database_url");  
      return uri != null;  
  }  

  @Override  
  @SuppressWarnings("unchecked")  
  public Neo4jServiceInfo createServiceInfo(Map<String, Object> serviceData) {  
      String id = (String) serviceData.get("name");  
      Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");  
      String url = credentials.get("database_url").toString();  
      String usr = credentials.get("database_usr").toString();  
      String pwd = credentials.get("database_pwd").toString();
      return new Neo4jServiceInfo(id, url, usr, pwd);  
  }  
}  