package net.rossillo.spring.web.mvc;

import org.springframework.stereotype.Controller;

@CacheControl
@Controller
final class CacheControlAnnotatedTestController {

    @CacheControl(policy = CachePolicy.PUBLIC)
    public String publicPolicy() {
        return null;
    }

    @CacheControl(policy = CachePolicy.PRIVATE)
    public String privatePolicy() {
        return null;
    }

    @CacheControl(policy = CachePolicy.MUST_REVALIDATE)
    public String mustRevalidatePolicy() {
        return null;
    }

    @CacheControl(policy = CachePolicy.PROXY_REVALIDATE)
    public String proxyRevalidatePolicy() {
        return null;
    }

    @CacheControl(policy = CachePolicy.NO_CACHE)
    public String noCachePolicy() {
        return null;
    }

    @CacheControl(policy = CachePolicy.NO_STORE)
    public String noStorePolicy() {
        return null;
    }

    @CacheControl(policy = CachePolicy.NO_TRANSFORM)
    public String noTransformPolicy() {
        return null;
    }

    @CacheControl(policy = CachePolicy.PUBLIC, maxAge = 360)
    public String publicPolicyWithMaxAge() {
        return null;
    }

    @CacheControl(policy = CachePolicy.PUBLIC, sharedMaxAge = 360)
    public String publicPolicyWithSharedMaxAge() {
        return null;
    }

    @CacheControl(policy = CachePolicy.NO_CACHE)
    public String pragmaNoCache() {
        return null;
    }


    public String defaultPolicy() {
        return null;
    }

}

