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

import it.inserpio.neo4art.cloudfoundry.Neo4jServiceInfo;
import it.inserpio.neo4art.domain.Artwork;
import it.inserpio.neo4art.service.ArtworkService;
import it.inserpio.neo4art.util.Neo4jRestClientFactory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * @author Lorenzo Speranzoni
 * @since Mar 26, 2014
 */
@Service
public class ArtworkRestClientService implements ArtworkService
{
  @Autowired
  private Neo4jServiceInfo neo4jServiceInfo;
  
  @Value("${neo4art_extension}")
  private String neo4artExtension;
  
  /* (non-Javadoc)
   * @see it.inserpio.neo4art.service.ArtworkService#findArtworkByMuseum(long)
   */
  @Override
  @Transactional
  @SuppressWarnings("unchecked")
  public List<Artwork> findArtworkByMuseum(long museumId)
  {
    RestTemplate restTemplate = Neo4jRestClientFactory.getInstance();
    
    String url = String.format(this.neo4jServiceInfo.getUrl() + "/" + this.neo4artExtension + "/artworks/museum/%s", museumId);
    
    java.util.List<Artwork> response = restTemplate.getForObject(url, java.util.List.class);
    
    return response;
  }
}
