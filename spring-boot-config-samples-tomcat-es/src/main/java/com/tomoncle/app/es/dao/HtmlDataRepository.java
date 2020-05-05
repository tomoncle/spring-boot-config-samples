/*
 * Copyright 2018 tomoncle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomoncle.app.es.dao;

import com.tomoncle.app.es.entity.HtmlData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author tomoncle
 */
public interface HtmlDataRepository extends JpaRepository<HtmlData, String> {
    /**
     * SELECT * FROM t_analysis_result_history WHERE id >=(SELECT id FROM t_analysis_result_history LIMIT 3266613, 1) LIMIT 10;
     *
     * @param index index
     * @param size  offset
     * @return List<HtmlData>
     */
    @Query(value = "SELECT * FROM t_analysis_result_history " +
            "WHERE id >= " +
            "(SELECT id FROM t_analysis_result_history LIMIT :index, 1) " +
            "LIMIT :offset",
            nativeQuery = true)
    List<HtmlData> queryBatch(@Param("index") int index, @Param("offset") int size);
}
