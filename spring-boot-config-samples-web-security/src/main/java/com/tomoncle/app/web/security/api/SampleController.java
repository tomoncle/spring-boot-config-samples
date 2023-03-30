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

package com.tomoncle.app.web.security.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author tomoncle
 */
@RestController
@RequestMapping("/sample")
@SuppressWarnings("unused")
public class SampleController {

    @PreAuthorize("@sysExpr.hasAuthority('sample:get')")
    @GetMapping
    String get() {
        return "GET";
    }

    @PreAuthorize("@sysExpr.hasAuthority('sample:post')")
    @PostMapping
    String post() {
        return "POST";
    }

    @PreAuthorize("@sysExpr.hasAuthority('sample:put')")
    @PutMapping
    String put() {
        return "PUT";
    }

    @PreAuthorize("@sysExpr.hasAuthority('sample:delete')")
    @DeleteMapping
    String delete() {
        return "DELETE";
    }

}
