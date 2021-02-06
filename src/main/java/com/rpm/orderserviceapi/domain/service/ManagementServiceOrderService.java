package com.rpm.orderserviceapi.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rpm.orderserviceapi.domain.exception.BusinessException;
import com.rpm.orderserviceapi.domain.exception.EntityNotFoundException;
import com.rpm.orderserviceapi.domain.model.Client;
import com.rpm.orderserviceapi.domain.model.Comment;
import com.rpm.orderserviceapi.domain.model.ServiceOrder;
import com.rpm.orderserviceapi.domain.model.StatusServiceOrder;
import com.rpm.orderserviceapi.domain.repository.ClientRepository;
import com.rpm.orderserviceapi.domain.repository.CommentRepository;
import com.rpm.orderserviceapi.domain.repository.ServiceOrderRepository;

@Service
public class ManagementServiceOrderService {

	@Autowired
	private ServiceOrderRepository serviceOrderRepository;

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	public ServiceOrder save(ServiceOrder serviceOrder) {

		Client client = clientRepository.findById(serviceOrder.getClient().getId())
				.orElseThrow(() -> new BusinessException("Cliente não encontrado."));

		serviceOrder.setClient(client);
		serviceOrder.setStatus(StatusServiceOrder.OPEN);
		serviceOrder.setOpenDate(OffsetDateTime.now());

		return serviceOrderRepository.save(serviceOrder);
	}
	
	public void finish(Long serviceOrderId) {
		ServiceOrder serviceOrder = search(serviceOrderId);
		
		serviceOrder.finish();
		
		serviceOrderRepository.save(serviceOrder);
	}

	public void delete(Long id) {
		serviceOrderRepository.deleteById(id);
	}
	
	public Comment addComment(Long serviceOrderId, String description) {
		ServiceOrder serviceOrder = search(serviceOrderId);
		
		Comment comment = new Comment();
		comment.setSendDate(OffsetDateTime.now());
		comment.setDescription(description);
		comment.setServiceOrder(serviceOrder);
		
		return commentRepository.save(comment);
	}

	private ServiceOrder search(Long serviceOrderId) {
		return serviceOrderRepository.findById(serviceOrderId)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada."));
	}
}
