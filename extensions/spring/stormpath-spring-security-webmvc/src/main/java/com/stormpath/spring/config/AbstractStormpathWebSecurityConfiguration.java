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
package com.stormpath.spring.config;

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

import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.servlet.csrf.CsrfTokenManager;
import com.stormpath.sdk.servlet.http.Saver;
import com.stormpath.spring.csrf.SpringSecurityCsrfTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;


/**
 * @since 1.0.RC5
 */
@Order(99)
public abstract class AbstractStormpathWebSecurityConfiguration {

    @Autowired
    protected Client client;

    @Autowired
    @Qualifier("stormpathAuthenticationProvider")
    protected AuthenticationProvider stormpathAuthenticationProvider; //provided by stormpath-spring-security

    @Autowired(required = false) //required = false when stormpath.web.enabled = false
    @Qualifier("stormpathAuthenticationResultSaver")
    protected Saver<AuthenticationResult> authenticationResultSaver; //provided by stormpath-spring-webmvc

    @Value("#{ @environment['stormpath.spring.security.enabled'] ?: true }")
    protected boolean stormpathSecuritybEnabled;

    @Value("#{ @environment['stormpath.web.enabled'] ?: true }")
    protected boolean stormpathWebEnabled;

    @Value("#{ @environment['stormpath.web.login.enabled'] ?: true }")
    protected boolean loginEnabled;

    @Value("#{ @environment['stormpath.web.login.uri'] ?: '/login' }")
    protected String loginUri;

    @Value("#{ @environment['stormpath.web.login.nextUri'] ?: '/' }")
    protected String loginNextUri;

    @Value("#{ @environment['stormpath.web.logout.enabled'] ?: true }")
    protected boolean logoutEnabled;

    @Value("#{ @environment['stormpath.web.logout.uri'] ?: '/logout' }")
    protected String logoutUri;

    @Value("#{ @environment['stormpath.web.logout.nextUri'] ?: '/login?status=logout' }")
    protected String logoutNextUri;

    @Value("#{ @environment['stormpath.web.forgot.enabled'] ?: true }")
    protected boolean forgotEnabled;

    @Value("#{ @environment['stormpath.web.forgot.nextUri'] ?: '/forgot' }")
    protected String forgotUri;

    @Value("#{ @environment['stormpath.web.change.enabled'] ?: true }")
    protected boolean changeEnabled;

    @Value("#{ @environment['stormpath.web.change.nextUri'] ?: '/change' }")
    protected String changeUri;

    @Value("#{ @environment['stormpath.web.register.enabled'] ?: true }")
    protected boolean registerEnabled;

    @Value("#{ @environment['stormpath.web.register.nextUri'] ?: '/register' }")
    protected String registerUri;

    @Value("#{ @environment['stormpath.web.verify.enabled'] ?: true }")
    protected boolean verifyEnabled;

    @Value("#{ @environment['stormpath.web.verify.nextUri'] ?: '/verify' }")
    protected String verifyUri;

    @Value("#{ @environment['stormpath.web.csrfProtection.enabled'] ?: true }")
    protected boolean csrfProtectionEnabled;

    @Value("#{ @environment['stormpath.web.csrf.token.name'] ?: '_csrf'}")
    protected String csrfTokenName;

    public StormpathWebSecurityConfigurer stormpathWebSecurityConfigurer() {
        return new StormpathWebSecurityConfigurer();
    }

    public AuthenticationSuccessHandler stormpathAuthenticationSuccessHandler() {
        return new StormpathLoginSuccessHandler(client, authenticationResultSaver);
    }

    public LogoutHandler stormpathLogoutHandler() {
        return new StormpathLogoutHandler(authenticationResultSaver);
    }

    public CsrfTokenRepository stormpathCsrfTokenRepository() {
        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        csrfTokenRepository.setParameterName(csrfTokenName);
        return csrfTokenRepository;
    }

    public CsrfTokenManager stormpathCsrfTokenManager() {
        return new SpringSecurityCsrfTokenManager(stormpathCsrfTokenRepository(), csrfTokenName);
    }
}
