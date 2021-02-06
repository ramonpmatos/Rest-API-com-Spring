create table service_order (
	id bigint not null auto_increment,
    client_id bigint not null,
    description text not null,
    price decimal(10,2) not null,
    status varchar(20) not null,
	open_date dateTime not null,
	end_date dateTime,
	
    primary key (id)
	
 );
 
 alter table service_order add constraint fk_order_service_client
 FOREIGN key (client_id) REFERENCES client (id);