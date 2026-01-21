package com.hawa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@ConfigurationProperties(prefix = "stuart.api")
@Data
public class StuartApiConfig {

    private String authUrl;
    private Job job = new Job();
    private Client client = new Client();

    public String getJobCreateUrl() {
        return this.job.createUrl;
    }

    public String getJobValidateUrl() {
        return this.job.validateUrl;
    }

    public String getJobPricingUrl() {
        return this.job.pricingUrl;
    }

    public String getJobPickupEtaUrl() {
        return this.job.pickupEtaUrl;
    }

    public String getJobDropoffEtaUrl() {
        return this.job.dropoffEtaUrl;
    }

    public String getJobCancellationUrl(long jobId) {
        return this.job.getCancellationUrl(jobId);
    }

    public String getJobReadUrl(long jobId) {
        return this.job.getReadUrl(jobId);
    }

    public String getClientId(){
        return this.client.id;
    }

    public String getClientSecret(){
        return this.client.secret;
    }

    @Data
    public static class Job {

        private String createUrl;

        private String validateUrl;

        private String pricingUrl;

        private String pickupEtaUrl;

        private String dropoffEtaUrl;

        private String cancellationUrl;

        private String readUrl;

        public String getReadUrl(long jobId) {
            return UriComponentsBuilder
                    .fromHttpUrl(readUrl)
                    .buildAndExpand(jobId)
                    .toUriString();
        }

        public String getCancellationUrl(long jobId) {
            return UriComponentsBuilder
                    .fromHttpUrl(cancellationUrl)
                    .buildAndExpand(jobId)
                    .toUriString();
        }
    }

    @Data
    public static class Client {
        private String id;

        private String secret;

    }
}
