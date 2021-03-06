/*
 * Copyright 2015 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stormpath.spring.boot.examples;

import com.stormpath.spring.config.StormpathWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @since 1.0.RC5
 */
@Configuration
public class SpringSecurityWebAppConfig extends StormpathWebSecurityConfigurerAdapter {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doConfigure(HttpSecurity http) throws Exception {
        // Access to all paths is restricted by default.
        // We want to restrict access to one path and leave all other paths open.
        http
            .authorizeRequests()
            .antMatchers("/restricted").fullyAuthenticated()
            .antMatchers("/**").permitAll();
    }
}