create table comment (
	id bigint not null auto_increment,
    service_order_id bigint not null,
    description text not null,
	send_date dateTime not null,
	
    primary key (id)
	
 );
 
 alter table comment add CONSTRAINT fk_comment_order_service
 FOREIGN key (service_order_id) REFERENCES service_order (id);