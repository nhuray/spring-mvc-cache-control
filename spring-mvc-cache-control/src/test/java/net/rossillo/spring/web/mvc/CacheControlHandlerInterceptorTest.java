package net.rossillo.spring.web.mvc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import static org.junit.Assert.*;

/**
 * Provides cache control interceptor tests.
 * 
 * @author Scott Rossillo
 *
 */
public final class CacheControlHandlerInterceptorTest {

    private CacheControlHandlerInterceptor interceptor;

    private MockHttpServletRequest request;

    private MockHttpServletResponse response;

    private final CacheControlAnnotatedTestController controller = new CacheControlAnnotatedTestController();

    @Before
    public void setUp() {
        interceptor = new CacheControlHandlerInterceptor();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void testPublicPolicy() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("publicPolicy"));

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertTrue(response.containsHeader("Cache-Control"));
        String header = response.getHeader("Cache-Control");
        assertTrue(header.contains("public"));
        assertFalse(header.contains("private"));
    }

    @Test
    public void testPrivatePolicy() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("privatePolicy"));

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertTrue(response.containsHeader("Cache-Control"));
        String header = response.getHeader("Cache-Control");
        assertTrue(header.contains("private"));
        assertFalse(header.contains("public"));
    }


    @Test
    public void testMustRevalidatePolicy() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("mustRevalidatePolicy"));

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertTrue(response.containsHeader("Cache-Control"));
        String header = response.getHeader("Cache-Control");
        assertTrue(header.contains("must-revalidate"));
    }

    @Test
    public void testProxyRevalidatePolicy() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("proxyRevalidatePolicy"));

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertTrue(response.containsHeader("Cache-Control"));
        String header = response.getHeader("Cache-Control");
        assertTrue(header.contains("proxy-revalidate"));
    }

    @Test
    public void testNoCachePolicy() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("noCachePolicy"));

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertTrue(response.containsHeader("Cache-Control"));
        String header = response.getHeader("Cache-Control");
        assertTrue(header.contains("no-cache"));
    }

    @Test
    public void testNoStorePolicy() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("noStorePolicy"));

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertTrue(response.containsHeader("Cache-Control"));
        String header = response.getHeader("Cache-Control");
        assertTrue(header.contains("no-store"));
    }

    @Test
    public void testNoTransformPolicy() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("noTransformPolicy"));

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertTrue(response.containsHeader("Cache-Control"));
        String header = response.getHeader("Cache-Control");
        assertTrue(header.contains("no-transform"));
    }

    @Test
    public void testPublicPolicyWithMaxAge() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("publicPolicyWithMaxAge"));

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertTrue(response.containsHeader("Cache-Control"));
        String header = response.getHeader("Cache-Control");
        assertTrue(header.contains("public"));
        assertFalse(header.contains("private"));
        assertTrue(header.contains("max-age=360"));
    }

    @Test
    public void testPublicPolicyWithSharedMaxAge() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("publicPolicyWithSharedMaxAge"));

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertTrue(response.containsHeader("Cache-Control"));
        String header = response.getHeader("Cache-Control");
        assertTrue(header.contains("public"));
        assertFalse(header.contains("private"));
        assertTrue(header.contains("s-maxage=360"));
    }

    @Test
    public void testExpires() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("publicPolicyWithMaxAge"));

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertTrue(response.containsHeader("Expires"));
    }

    @Test
    public void testNoExpires() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("publicPolicyWithMaxAge"));

        // When
        interceptor.setUseExpiresHeader(false);
        interceptor.preHandle(request, response, handler);

        // Then
        assertFalse(response.containsHeader("Expires"));
    }


    @Test
    public void testPragmaNoCache() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("pragmaNoCache"));

        // When
        interceptor.preHandle(request, response, handler);

        // Then
        assertTrue(response.containsHeader("Pragma"));
        String header = response.getHeader("Pragma");
        assertTrue(header.contains("no-cache"));
    }

    @Test
    public void testDefaultPolicy() throws Exception {
        // Given
        final HandlerMethod handler = new HandlerMethod(
                controller,
                controller.getClass().getMethod("defaultPolicy"));

        // When
        CacheControl cacheControl = interceptor.getCacheControl(request, response, handler);

        // Then
        assertNotNull(cacheControl);
        CachePolicy[] policy = cacheControl.policy();
        assertEquals(1, policy.length);
        assertEquals(CachePolicy.NO_CACHE, policy[0]);
    }
}
