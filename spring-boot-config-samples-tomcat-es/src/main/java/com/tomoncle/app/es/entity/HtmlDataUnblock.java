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

package com.tomoncle.app.es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * @author tomoncle
 */
@Data
@Document(
        indexName = HtmlDataUnblock.INDEX_NAME,
        type = HtmlDataUnblock.INDEX_TYPE)
@Setting(settingPath = "elasticsearch/index_analysis_result_history_settings.json")
public class HtmlDataUnblock {
    public static final String INDEX_NAME = "analysis_result_history";
    public static final String INDEX_TYPE = "unblock-html-data";

    @Id
    private String id;

    // url
    @Field(type = FieldType.Keyword)
    private String url;

    // 文档id
    @Field(type = FieldType.Keyword)
    private String docId;

    // 状态
    @Field(type = FieldType.Integer)
    private Integer status;

    // 分析结果, json类型
    @Field(type = FieldType.Keyword)
    private String result;

    // 创建时间
    @Field(type = FieldType.Long)
    private Long createTimestamp;

    // 更新时间
    @Field(type = FieldType.Long)
    private Long updateTimestamp;
}
