package com.rpm.orderserviceapi.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {

	@NotBlank
	private String description;
	
}
