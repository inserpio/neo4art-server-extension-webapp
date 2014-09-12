package it.inserpio.neo4art.cloudfoundry;

import org.springframework.cloud.service.BaseServiceInfo;

public class Neo4jServiceInfo extends BaseServiceInfo {

	private String url;
	private String usr;
	private String pwd;

	public Neo4jServiceInfo(String id, String url, String usr, String pwd) {
		super(id);
		
		this.url = url;
		this.usr = usr;
		this.pwd = pwd;
	}

	public String getUrl() {
		return url;
	}

	public String getUsr() {
		return usr;
	}

	public String getPwd() {
		return pwd;
	}

}
