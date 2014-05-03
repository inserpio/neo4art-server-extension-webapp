/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.inserpio.neo4art.util;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Lorenzo Speranzoni
 * @since May 3, 2014
 */
@Component
public class Neo4jRestClientFactory
{
  private static String databaseUser;

  private static String databasePassword;

  @Value("${database_usr}")
  public void setDatabaseUser(String databaseUser)
  {
    Neo4jRestClientFactory.databaseUser = databaseUser;
  }

  @Value("${database_pwd}")
  public void setDatabasePassword(String databasePassword)
  {
    Neo4jRestClientFactory.databasePassword = databasePassword;
  }

  public static RestTemplate getInstance()
  {
    CredentialsProvider credsProvider = new BasicCredentialsProvider();

    credsProvider.setCredentials(
            new AuthScope(null, -1),
            new UsernamePasswordCredentials(databaseUser, databasePassword));

    HttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

    RestTemplate restTemplate = new RestTemplate();

    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

    return restTemplate;
  }
}