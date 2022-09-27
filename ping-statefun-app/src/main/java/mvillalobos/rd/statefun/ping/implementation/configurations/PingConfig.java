package mvillalobos.rd.statefun.ping.implementation.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("mvillalobos.rd.statefun.ping")
@Getter
@Setter
public class PingConfig {

	@Getter
	@Setter
	private PingRequest pingRequest;

	@Getter
	@Setter
	public static class PingRequest {
		private StateFun stateFun;
	}

	@Getter
	@Setter
	public static class StateFun {
		private Ingress ingress;
	}

	@Getter
	@Setter
	public static class Ingress {
		private String topic;
	}
}
