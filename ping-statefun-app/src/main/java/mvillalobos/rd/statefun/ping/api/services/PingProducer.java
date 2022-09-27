package mvillalobos.rd.statefun.ping.api.services;

import mvillalobos.rd.statefun.ping.api.domain.Ping;

public interface PingProducer {
	void sendPingMessage(Ping ping);
}
