package com.rpm.orderserviceapi.api.model;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientIdRequestDTO {

	@NotNull
	private Long id;
	
}
