package com.rpm.orderserviceapi.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.rpm.orderserviceapi.domain.model.StatusServiceOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceOrderDTO {

	private Long id;
	private ClientDTO client;
	private BigDecimal price;
	private StatusServiceOrder status;
	private OffsetDateTime openDate;
	private OffsetDateTime endDate;
	
}
