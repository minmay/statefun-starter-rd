package mvillalobos.rd.statefun.ping.implementation.statefun;

import mvillalobos.rd.statefun.ping.api.annotations.Statefun;
import mvillalobos.rd.statefun.ping.api.annotations.ValueSpecMarker;
import org.apache.flink.statefun.sdk.java.Context;
import org.apache.flink.statefun.sdk.java.StatefulFunction;
import org.apache.flink.statefun.sdk.java.TypeName;
import org.apache.flink.statefun.sdk.java.ValueSpec;
import org.apache.flink.statefun.sdk.java.message.Message;
import org.apache.flink.statefun.sdk.java.message.MessageBuilder;
import org.apache.flink.statefun.sdk.java.types.Types;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

@Statefun(namespace = "java.mvillalobos.rd.statefun", name = "ping")
public class PingFn implements StatefulFunction {

	private final static Logger logger = LoggerFactory.getLogger(PingFn.class);

	public final static TypeName pongFn = TypeName.typeNameOf("python.mvillalobos.rd.statefun", "pong");

	@ValueSpecMarker
	private final ValueSpec<Integer> seenSpec = ValueSpec.named("seen").withIntType();

	@Override
	public CompletableFuture<Void> apply(Context context, Message message) throws Throwable {
		logger.info("Received message.");

		if (message.is(Types.stringType())) {
			final String pingRequest = message.as(Types.stringType());
			logger.info("Retrieved: {} with id: {}", pingRequest, context.self().id());

			var storage = context.storage();
			var seen = storage.get(seenSpec).orElse(0);
			seen += 1;
			storage.set(seenSpec, seen);

			context.send(
					MessageBuilder.forAddress(pongFn, "default")
							.withValue("Ping for the " + seen + "th time! with message: " + pingRequest)
							.build());
		}

		return context.done();
	}

}
