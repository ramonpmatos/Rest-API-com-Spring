package com.rpm.orderserviceapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Problem {

	private Integer status;
	private LocalDateTime dateHour;
	private String title;
	private List<Field> fields;
	
	@Getter
	@Setter
	@AllArgsConstructor
	public static class Field{
		private String name;
		private String message;
		
	}
	
}
