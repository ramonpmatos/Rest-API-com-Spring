package com.rpm.orderserviceapi.domain.model;

import java.time.OffsetDateTime;
import java.util.List;
import com.rpm.orderserviceapi.domain.exception.BusinessException;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.math.BigDecimal;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.validation.groups.ConvertGroup;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import javax.validation.groups.Default;

import com.rpm.orderserviceapi.domain.ValidationGroups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ServiceOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.ClientId.class)
	@NotNull
	@ManyToOne
	private Client client;

	@NotBlank
	private String description;

	@NotNull
	private BigDecimal price;

	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime openDate;

	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime endDate;

	@OneToMany(mappedBy = "serviceOrder")
	private List<Comment> comments;

	@JsonProperty(access = Access.READ_ONLY)
	@Enumerated(EnumType.STRING)
	private StatusServiceOrder status;

	public void finish() {
		if (!StatusServiceOrder.OPEN.equals(getStatus())) {
			throw new BusinessException("Ordem de serviço não pode ser finalizada.");
		}

		setStatus(StatusServiceOrder.END);
		setEndDate(OffsetDateTime.now());
	}

}
