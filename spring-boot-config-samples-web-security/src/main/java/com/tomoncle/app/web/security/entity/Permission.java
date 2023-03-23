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

package com.tomoncle.app.web.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author tomoncle
 */
@Getter
@Setter
@Entity
@Table(name = "t_permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pattern;
    private String description;
    private String iconCls;
    @Enumerated(EnumType.STRING)
    private STATE state;
    @Enumerated(EnumType.STRING)
    private TYPE resourceType;
    // 父级资源ID
    @ManyToOne
    private Permission permission;

    @JsonIgnoreProperties(value = "permissions")
    @ManyToMany
    @JoinTable(name = "i_role_permission",
            joinColumns = @JoinColumn(name = "permission_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public enum TYPE {
        MENU, PAGE, BUTTON
    }

    public enum STATE {
        open, closed
    }


}
