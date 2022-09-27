package mvillalobos.rd.statefun.ping.api.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Ping {
	private final String id;
	private final String message;
}
