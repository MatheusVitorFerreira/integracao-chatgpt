package com.edu.integracaochatgpt.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechSettings;
import org.json.JSONException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class SpeechConfig {

    @Bean
    public SpeechClient speechClient() throws IOException {
        JSONObject options = configuringJsonObject();

        GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(options.toString().getBytes()));
        SpeechSettings settings = SpeechSettings.newBuilder().setCredentialsProvider(() -> credentials).build();
        return SpeechClient.create(settings);
    }

    private JSONObject configuringJsonObject() {
        JSONObject options = new JSONObject();
        try {
            options.put("type", "service_account");
            options.put("project_id", "transcrever-audio-em-texto");
            options.put("private_key_id", "b8708667f6a34b032babb2a81a84d28029e77447");
            options.put("private_key", "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDArvaFAT1tL7PS\nIGbpPbEXxMda25rL0sj+zeEmdVK9H+6ZFvHIja5+a1PJvjql5jRH1q541OsY0rDI\n0xqkFJM8klu9OBs87nNZUCx3p9m/t54E8eNl8uihnJyzO3RTkw9aHaMXDK6Cbh/4\nJtLsrC9IcEBQtKTNEMXk5RPNk+8NTa5k021FS1MOT1YrmZURMS8QmsvjOr9NTZ1T\nrVGwUxTdjX2TTkX/AmDI4H1uUx/lmWFMhUU244KYVaM24Gs5ov+QqA2qnT90AF0A\nJKNAdeUJyQb3Wxxek2Tbac7L4AN8STlH7A/TUq6pB9f6Mam9+Kj0WQk6Nz19grjV\npW3H4eYPAgMBAAECggEAQhdhgKK5vqt3mzI5CnT6OyRomw4rjRFKlzfoLChfDncX\nANThb0DF4yAOV+tTs5cARMnrA5BI6bVPJTtf7nsOO98Z75AAq8hAsZhyAEMHSh8B\n6UFNFimZSOHuz/2rO4V8ZRU4XU4gx6VdeuFaRQsUltzF+sjTpHFF5+r5fXDqNN3h\nf9mVRe/SIXFDVF5KkrNyhdsrtawsmcQuucXGODbBf+BgdzQNNiQM6Yilb51pIEKG\nVDRVreWst2bPEBt0q2cqBjh3Ldk4+uN+VMARuFIxASPvU6x4+DXxVD+yeydVqVYt\nsgsGA5E4LFEjw5fXKd8VBkr8+8iz5lh7KiTX9xZcgQKBgQDfPe4Bsm/7V15WUycr\nQpv4ge3Phdv6XTFnsNlgUBouM774I0VuNi+s64iv/Z8yyZE2zmPILobuwDE2mhyf\navuR6Nc4EPk6OwmE13p6Wor0I1TCOBhUjtrqTBPEVXTlpZvYwBDmXesGzyH1lz2O\nLLujlWSi+k49YbzHzErVP9TQgQKBgQDc9RsxlEpno1Csgt0TTu7NGX1KnVDlOVmR\nqJWpQTxCExMUaMe0IbRhi11cOkZgB/bZavZyvaa8YqCs1MQPguy4a4nTHqH3f59R\ndnJozdJILlHe+cObfJOjOwMgdeKhszdjxhcBjDxJbns6RxRxASn+GGY0Ksnonq/k\n2wEG2WpujwKBgQCMWu9s+bcWGxQgyh7yP+q4D1hpBQwNGNKbEsFYoIQVUEtczzAz\n3rWSznMd0yNoVvqJrrFlhnsnWIaYgcRFJooMbowBoApR5hRSBPfoPUznud7sTUBe\nyYIxlKtI4UGhsMGPQRJfA+VkXo5HTlNRNCa07pRbM2xFg1GhfHwAHyW6AQKBgFso\njEWSJnhHepsRil/S6taXM4e7C4rmdDuRz9z6Wv/vmqmK6Qk59o9fze6k5C/uWTst\n0+tWrxl9n0hup0y2+S7EvyHou22+q14SffUbJSw+X+ycIsVdShPH3uxfdla795gk\nO42Z661n9VL/zEEq98YrDs9q784W2f6TFANdY3mlAoGBAKE03SLgpn+4HGF6VnLc\nWZDJjobwE0G6RBPCckDaw3qIYjCwDpX/alXaTSnCOBS4grBk1X/tRRptsoL7sEMH\nV/H6BqRlyoPeatcvg3dc+6WA1+UlESicg3kUS4cAioF37IKkfRmR3KKxEAvVImD/\nOdvgURkgGxJOw619432jj/40\n-----END PRIVATE KEY-----\n");
            options.put("client_email", "transcrever-texto@transcrever-audio-em-texto.iam.gserviceaccount.com");
            options.put("client_id", "106416478050341884930");
            options.put("auth_uri", "https://accounts.google.com/o/oauth2/auth");
            options.put("token_uri", "https://oauth2.googleapis.com/token");
            options.put("auth_provider_x509_cert_url", "https://www.googleapis.com/oauth2/v1/certs");
            options.put("client_x509_cert_url", "https://www.googleapis.com/robot/v1/metadata/x509/transcrever-texto%40transcrever-audio-em-texto.iam.gserviceaccount.com");
            options.put("universe_domain", "googleapis.com");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return options;
    }
}
