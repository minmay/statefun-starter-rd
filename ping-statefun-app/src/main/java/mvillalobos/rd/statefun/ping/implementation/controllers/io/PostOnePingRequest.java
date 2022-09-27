package mvillalobos.rd.statefun.ping.implementation.controllers.io;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PostOnePingRequest {
	@ApiModelProperty(value = "The id of the stateful function instance. Each id has its own state.", example = "1", position = 0)
	private String id;
	@ApiModelProperty(value = "The ping message to send.", example = "ping", position = 0)
	private String message;
}
