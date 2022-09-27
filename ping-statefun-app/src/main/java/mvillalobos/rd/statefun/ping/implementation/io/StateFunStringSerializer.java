package mvillalobos.rd.statefun.ping.implementation.io;

import org.apache.flink.statefun.sdk.java.slice.Slice;
import org.apache.flink.statefun.sdk.java.types.Types;
import org.apache.kafka.common.serialization.Serializer;

public class StateFunStringSerializer implements Serializer<String> {
	@Override
	public byte[] serialize(String topic, String data) {
		final Slice slice = Types.stringType().typeSerializer().serialize(data);
		return slice.toByteArray();
	}
}
