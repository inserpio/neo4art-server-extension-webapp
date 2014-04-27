/*
 * Copyright 2012 the original author or authors.
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
package it.inserpio.neo4art.service;

import it.inserpio.neo4art.domain.Museum;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/application-context.xml" })
@Transactional
@EnableTransactionManagement
public class MuseumServiceTest
{
  @Autowired
  private MuseumService museumService;
  
  @Test
  public void shouldGetMuseumsWithinDistance()
  {
    double longitude    = -0.1283d;
    double latitude     = 51.5086d;
    double distanceInKm = 10.0d;
    
    List<Museum> museumList = this.museumService.getMuseumsWithinDistance(longitude, latitude, distanceInKm);
    
    Assert.assertNotNull(museumList);
    Assert.assertEquals(3, museumList.size());
    
    System.out.println(museumList);
  }
}
