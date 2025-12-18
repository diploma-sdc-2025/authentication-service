package org.java.diploma.service.authservice.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Test
    void noAuthorizationHeader_doesNothing() throws Exception {
        JwtService jwtService = mock(JwtService.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void validBearerToken_setsAuthentication() throws Exception {
        JwtService jwtService = mock(JwtService.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService);

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("1");
        when(jwtService.parseAndValidate("valid-token")).thenReturn(claims);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid-token");

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("1",
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        verify(chain).doFilter(request, response);
    }

    @Test
    void invalidToken_clearsContext() throws Exception {
        JwtService jwtService = mock(JwtService.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService);

        when(jwtService.parseAndValidate(any()))
                .thenThrow(new RuntimeException("invalid"));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer bad-token");

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
    }
}

