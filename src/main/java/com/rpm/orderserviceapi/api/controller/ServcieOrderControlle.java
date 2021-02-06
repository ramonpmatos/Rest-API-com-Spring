package com.rpm.orderserviceapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rpm.orderserviceapi.api.model.ServiceOrderDTO;
import com.rpm.orderserviceapi.api.model.ServiceOrderRequestDTO;
import com.rpm.orderserviceapi.domain.model.ServiceOrder;
import com.rpm.orderserviceapi.domain.repository.ServiceOrderRepository;
import com.rpm.orderserviceapi.domain.service.ManagementServiceOrderService;

@RestController
@RequestMapping("/service-order")
public class ServcieOrderControlle {

	@Autowired
	private ManagementServiceOrderService managementServiceOrderService;

	@Autowired
	private ServiceOrderRepository serviceOrderRepository;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceOrderDTO create(@Valid @RequestBody ServiceOrderRequestDTO serviceOrderRequestDTO) {
		ServiceOrder serviceOrder = toEntity(serviceOrderRequestDTO);
		return toServiceOrderDTO(managementServiceOrderService.save(serviceOrder));
	}

	@GetMapping
	public List<ServiceOrderDTO> list() {
		return toServiceOrderDTOs(serviceOrderRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ServiceOrderDTO> read(@PathVariable Long id) {
		Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(id);

		if (serviceOrder.isPresent()) {
			ServiceOrderDTO serviceOrderDTO = toServiceOrderDTO(serviceOrder.get());
			return ResponseEntity.ok(serviceOrderDTO);
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<ServiceOrderDTO> update(@Valid @PathVariable Long id,
			@RequestBody ServiceOrderRequestDTO serviceOrderRequestDTO) {
		ServiceOrder serviceOrder = toEntity(serviceOrderRequestDTO);
		if (!serviceOrderRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		serviceOrder.setId(id);
		serviceOrder = managementServiceOrderService.save(serviceOrder);
		return ResponseEntity.ok(toServiceOrderDTO(serviceOrder));

	}

	@PutMapping("/{serviceOrderId}/finish")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finish(@PathVariable Long serviceOrderId) {
		managementServiceOrderService.finish(serviceOrderId);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ServiceOrder> delete(@PathVariable Long id) {
		if (!serviceOrderRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		managementServiceOrderService.delete(id);

		return ResponseEntity.noContent().build();

	}

	private ServiceOrderDTO toServiceOrderDTO(ServiceOrder serviceOrder) {
		return modelMapper.map(serviceOrder, ServiceOrderDTO.class);
	}

	private List<ServiceOrderDTO> toServiceOrderDTOs(List<ServiceOrder> serviceOrders) {
		return serviceOrders.stream().map(serviceOrder -> toServiceOrderDTO(serviceOrder)).collect(Collectors.toList());
	}

	private ServiceOrder toEntity(ServiceOrderRequestDTO serviceOrderRequestDTO) {
		return modelMapper.map(serviceOrderRequestDTO, ServiceOrder.class);
	}
}
