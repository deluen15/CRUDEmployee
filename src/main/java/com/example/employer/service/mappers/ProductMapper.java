package com.example.employer.service.mappers;

import com.bytes.springcloudproducts.model.Product;
import com.example.employer.model.Employer;
import com.example.employer.model.MetadataEvent;
import com.example.employer.model.ProductEmp;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {

    ProductEmp map(Product product);

    MetadataEvent map(com.bytes.springcloudproducts.model.MetadataEvent product);

    @Mapping(target = "id", source = "employer.id")
    @Mapping(target = "name", source = "employer.name")
    ProductEmp map(Employer employer, Product product);

}
