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
package com.stormpath.spring.boot.autoconfigure;

import com.stormpath.spring.config.AbstractStormpathSpringSecurityConfiguration;
import com.stormpath.spring.config.StormpathSecurityEnabled;
import com.stormpath.spring.security.provider.AccountGrantedAuthorityResolver;
import com.stormpath.spring.security.provider.AccountPermissionResolver;
import com.stormpath.spring.security.provider.AuthenticationTokenFactory;
import com.stormpath.spring.security.provider.GroupGrantedAuthorityResolver;
import com.stormpath.spring.security.provider.GroupPermissionResolver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

/**
 * @since 1.0.RC5
 */
@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
@ConditionalOnProperty(name = { "stormpath.enabled", "stormpath.spring.security.enabled" }, matchIfMissing = true)
@AutoConfigureAfter({ StormpathAutoConfiguration.class, SecurityAutoConfiguration.class })
public class StormpathSpringSecurityAutoConfiguration extends AbstractStormpathSpringSecurityConfiguration {

    @Bean
    @Override
    @ConditionalOnMissingBean
    @Conditional(StormpathSecurityEnabled.class)
    public GroupGrantedAuthorityResolver stormpathGroupGrantedAuthorityResolver() {
        return super.stormpathGroupGrantedAuthorityResolver();
    }

    @Bean
    @Override
    @ConditionalOnMissingBean
    @Conditional(StormpathSecurityEnabled.class)
    public GroupPermissionResolver stormpathGroupPermissionResolver() {
        return super.stormpathGroupPermissionResolver();
    }

    @Bean
    @Override
    @ConditionalOnMissingBean
    @Conditional(StormpathSecurityEnabled.class)
    public AccountGrantedAuthorityResolver stormpathAccountGrantedAuthorityResolver() {
        return super.stormpathAccountGrantedAuthorityResolver();
    }

    @Bean
    @Override
    @ConditionalOnMissingBean
    @Conditional(StormpathSecurityEnabled.class)
    public AccountPermissionResolver stormpathAccountPermissionResolver() {
        return super.stormpathAccountPermissionResolver();
    }

    @Bean
    @Override
    @ConditionalOnMissingBean
    @Conditional(StormpathSecurityEnabled.class)
    public AuthenticationTokenFactory stormpathAuthenticationTokenFactory() {
        return super.stormpathAuthenticationTokenFactory();
    }

    @Bean
    @Override
    @ConditionalOnMissingBean
    @Conditional(StormpathSecurityEnabled.class)
    public AuthenticationProvider stormpathAuthenticationProvider() {
        return super.stormpathAuthenticationProvider();
    }

}
