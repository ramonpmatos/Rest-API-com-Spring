package com.rpm.orderserviceapi.api.model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

	private Long id;
	private String description;
	private OffsetDateTime sendDate;
}
