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
package it.inserpio.neo4art.controller;

import it.inserpio.neo4art.domain.Artwork;
import it.inserpio.neo4art.service.ArtworkService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST API:
 * 
 * /artworks/museum/1051
 * 
 * @author Lorenzo Speranzoni
 * @since Apr 27, 2014
 */
@Controller
@RequestMapping("/artworks")
public class ArtworkController
{
  @Autowired
  private ArtworkService artworkService;
  
  /**
   * Retrieve artworks by museum id (i.e. nodeId)
   * 
   * @param museumId
   * @param 
   * @return
   */
  @RequestMapping(value="/museum/{museumId}", method=RequestMethod.GET, produces={"application/xml", "application/json"})
  public @ResponseBody List<Artwork> getArtworksByMuseum(@PathVariable long museumId)
  {
    return this.artworkService.findArtworkByMuseum(museumId);
  }
}
