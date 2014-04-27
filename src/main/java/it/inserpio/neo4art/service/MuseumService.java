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

package it.inserpio.neo4art.service;

import it.inserpio.neo4art.domain.Museum;

import java.util.List;

/**
 * @author Lorenzo Speranzoni
 * @since Mar 26, 2014
 */
public interface MuseumService
{
  /**
   * 
   * @param longitude
   * @param latitude
   * @param distanceInKm
   * @return
   */
  List<Museum> getMuseumsWithinDistance(double longitude, double latitude, double distanceInKm);
}
