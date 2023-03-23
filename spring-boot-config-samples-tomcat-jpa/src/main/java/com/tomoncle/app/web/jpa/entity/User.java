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

package com.tomoncle.app.web.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 使用多对多配置时，不要使用 lombok.@Data 注解，该注解会调用 ToString 注解，
 * 该注解会触发jpa的懒加载, 造成循环引用，
 * 造成注解 JsonIgnoreProperties 看起来没有生效，其实原因时因为  lombok.@ToString
 * <p>
 * <p>
 * 中间表不要外键
 * 配置：@JoinTable(name = "i_role_user",
 * joinColumns = @JoinColumn(name = "user_id"),
 * inverseJoinColumns = @JoinColumn(name = "role_id"),
 * foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT),
 * inverseForeignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
 *
 * @author tomoncle
 */
@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String nickName;
    /**
     * 锁定状态 0 锁定 1 正常（ true ）
     */
    @Column(columnDefinition = "boolean default true")
    private boolean locked;
    /**
     * 账号状态 0 冻结 1 正常（ true ）
     */
    @Column(columnDefinition = "boolean default true")
    private boolean enabled;

    @JsonIgnoreProperties(value = "users")
    @ManyToMany
    @JoinTable(name = "i_role_user",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    public String toString() {
        return getClass().getName() + "@" + this.toString();
    }
}
