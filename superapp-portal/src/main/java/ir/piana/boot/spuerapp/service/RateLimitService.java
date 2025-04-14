package ir.piana.boot.spuerapp.service;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import ir.piana.boot.spuerapp.common.errors.APIErrorType;
import ir.piana.boot.spuerapp.common.errors.APIRuntimeException;
import ir.piana.boot.spuerapp.common.errors.OneHttpHeader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class RateLimitService {
    private final Supplier<BucketConfiguration> bucketConfiguration;
    private final ProxyManager<String> proxyManager;

    public RateLimitService(
            @Qualifier("signInOtpBucketConfiguration") Supplier<BucketConfiguration> bucketConfiguration,
            ProxyManager<String> proxyManager) {
        this.bucketConfiguration = bucketConfiguration;
        this.proxyManager = proxyManager;
    }

    public void checkSignInLimit(String mobile) throws APIRuntimeException {
        Bucket bucket = proxyManager.builder().build(mobile, bucketConfiguration);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (!probe.isConsumed()) {
            APIErrorType.SignInRateLimitError.doThrows(new OneHttpHeader(
                    "X-Rate-Limit-Retry-After-Seconds",
                    "" + TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill())
            ));
        }
    }
}
