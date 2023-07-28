package com.example.employer.service.mappers;

import com.bytes.springcloudproducts.model.Product;
import com.example.employer.model.MetadataEvent;
import com.example.employer.model.ProductEmp;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {

    ProductEmp map(Product product);

    MetadataEvent map(com.bytes.springcloudproducts.model.MetadataEvent product);

}
