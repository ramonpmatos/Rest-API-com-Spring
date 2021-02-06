package com.rpm.orderserviceapi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rpm.orderserviceapi.api.model.CommentDTO;
import com.rpm.orderserviceapi.api.model.CommentRequestDTO;
import com.rpm.orderserviceapi.domain.exception.EntityNotFoundException;
import com.rpm.orderserviceapi.domain.model.Comment;
import com.rpm.orderserviceapi.domain.model.ServiceOrder;
import com.rpm.orderserviceapi.domain.repository.ServiceOrderRepository;
import com.rpm.orderserviceapi.domain.service.ManagementServiceOrderService;

@RestController
@RequestMapping("/service-order/{serviceOrderId}/comments")
public class CommentController {

	@Autowired
	private ManagementServiceOrderService managementServiceOrderService;

	@Autowired
	private ServiceOrderRepository serviceOrderRepository;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<CommentDTO> list(@PathVariable Long serviceOrderId) {
		ServiceOrder serviceOrder = serviceOrderRepository.findById(serviceOrderId)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada"));
		return toModels(serviceOrder.getComments());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CommentDTO add(@PathVariable Long serviceOrderId, @Valid @RequestBody CommentRequestDTO commentRequestDTO) {
		Comment comment = managementServiceOrderService.addComment(serviceOrderId, commentRequestDTO.getDescription());

		return toModel(comment);
	}

	private CommentDTO toModel(Comment comment) {
		return modelMapper.map(comment, CommentDTO.class);
	}

	private List<CommentDTO> toModels(List<Comment> comments) {
		return comments.stream().map(comment -> toModel(comment)).collect(Collectors.toList());
	}

}
