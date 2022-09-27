package mvillalobos.rd.statefun.ping.implementation.services;

import mvillalobos.rd.statefun.ping.api.domain.Ping;
import mvillalobos.rd.statefun.ping.api.services.PingProducer;
import mvillalobos.rd.statefun.ping.implementation.configurations.PingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DefaultPingProducer implements PingProducer {

	private final PingConfig pingConfig;
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public DefaultPingProducer(PingConfig pingConfig, KafkaTemplate<String, String> kafkaTemplate) {
		this.pingConfig = pingConfig;
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public void sendPingMessage(Ping ping) {
		final String topic = pingConfig.getPingRequest().getStateFun().getIngress().getTopic();
		// note that I am using a id of the concept as the kafka key.
		// this is crucial step that allows each individual concept to have its own state within stateful functions
		// this key is required to be a string
		kafkaTemplate.send(topic, ping.getId(), ping.getMessage());
	}
}
