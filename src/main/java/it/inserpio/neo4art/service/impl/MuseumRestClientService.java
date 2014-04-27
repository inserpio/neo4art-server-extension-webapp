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

package it.inserpio.neo4art.service.impl;

import it.inserpio.neo4art.domain.Museum;
import it.inserpio.neo4art.service.MuseumService;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * @author Lorenzo Speranzoni
 * @since Mar 26, 2014
 */
@Service
public class MuseumRestClientService implements MuseumService
{
  @Value("${database_url}")
  private String databaseURL;
  
  @Value("${neo4art_extension}")
  private String neo4artExtension;
  
  /* (non-Javadoc)
   * @see it.inserpio.neo4art.service.MuseumService#getMuseumsWithinDistance(double, double, double)
   */
  @Override
  @Transactional
  @SuppressWarnings("unchecked")
  public List<Museum> getMuseumsWithinDistance(double longitude, double latitude, double distanceInKm)
  {
    RestTemplate restTemplate = new RestTemplate();
    
    String url = String.format(this.databaseURL + "/" + this.neo4artExtension + "/museums/lon/%s/lat/%s/distanceInKm/%s", longitude, latitude, distanceInKm);
    
    java.util.Map<String, List<Museum>> response = restTemplate.getForObject(url, java.util.Map.class);
    
    return response.get("museum");
  }
}
