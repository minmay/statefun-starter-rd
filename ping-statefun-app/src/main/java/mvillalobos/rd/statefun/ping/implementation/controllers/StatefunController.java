package mvillalobos.rd.statefun.ping.implementation.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.flink.statefun.sdk.java.handler.RequestReplyHandler;
import org.apache.flink.statefun.sdk.java.slice.Slice;
import org.apache.flink.statefun.sdk.java.slice.Slices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class StatefunController {

	private final static Logger logger = LoggerFactory.getLogger(StatefunController.class);

	private final RequestReplyHandler handler;

	@PostMapping("/{functionName}")
	public CompletableFuture<byte[]> handle(@RequestBody byte[] body) {
		logger.info("retrieve request");
		return handler.handle(Slices.wrap(body))
				.thenApply(Slice::toByteArray);
	}
}
