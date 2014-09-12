package it.inserpio.neo4art.cloudfoundry;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Neo4jConnectionService extends AbstractCloudConfig {
	@Bean
	public Neo4jServiceInfo neo4jServiceInfo() {
		Neo4jServiceInfoCreator neo4jServiceInfoCreator = new Neo4jServiceInfoCreator();
		return neo4jServiceInfoCreator.createServiceInfo(CloudFoundryServiceManager.getService("graphenedb-neo4art-sdn301"));
	}
}
