package mvillalobos.rd.statefun.ping.implementation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import mvillalobos.rd.statefun.ping.api.domain.Ping;
import mvillalobos.rd.statefun.ping.api.services.PingProducer;
import mvillalobos.rd.statefun.ping.implementation.controllers.io.PostOnePingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/pings")
@Tag(name = "Stateful Functions Ping API", description = "This API initiates stateful function requests.")
public class PingController {

	private final PingProducer pingProducer;

	@Autowired
	public PingController(PingProducer pingProducer) {
		this.pingProducer = pingProducer;
	}

	@Operation(
			summary = "Ping stateful function.",

			description =
					"This will transform the input ping request and publish " +
							"it to the Flink Stateful Functions ingress responsible for pinging."

	)
	@PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<?> postOnePingRequest(@RequestBody PostOnePingRequest postOnePingRequest) {
		final Ping ping = new Ping(postOnePingRequest.getId(), postOnePingRequest.getMessage());
		pingProducer.sendPingMessage(ping);
		return ResponseEntity.ok().build();
	}
}
