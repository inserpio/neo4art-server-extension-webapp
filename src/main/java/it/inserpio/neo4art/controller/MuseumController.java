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

import it.inserpio.neo4art.domain.Museum;
import it.inserpio.neo4art.service.MuseumService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST API:
 * 
 * /museums/lon/51.5086/lat/-0.1283/distanceInKm/10.0
 * 
 * @author Lorenzo Speranzoni
 * @since Apr 27, 2014
 */
@Controller
@RequestMapping("/museums")
public class MuseumController
{
  @Autowired
  private MuseumService museumService;
  
  /**
   * Find museums hosting selected artist's artworks
   * 
   * @param artistId
   * @return
   */
  @Transactional
  @RequestMapping(value="/lon/{lon}/lat/{lat}/distanceInKm/{distanceInKm}", method=RequestMethod.GET, produces={"application/xml", "application/json"})
  public @ResponseBody List<Museum> getMuseumsWithinDistance(@PathVariable("lon") double longitude, @PathVariable("lat") double latitude, @PathVariable double distanceInKm)
  {
    return this.museumService.getMuseumsWithinDistance(longitude, latitude, distanceInKm);
  }
}
