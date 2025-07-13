package kh.edu.istad.mobileapi.mapper;

import kh.edu.istad.mobileapi.domain.Account;
import kh.edu.istad.mobileapi.dto.AccountResponse;
import kh.edu.istad.mobileapi.dto.CreateAccountRequest;
import kh.edu.istad.mobileapi.dto.UpdateAccountRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toAccountPartially(UpdateAccountRequest updateAccountRequest,
                            @MappingTarget Account account);

    Account toAccount(CreateAccountRequest createAccountRequest);

    @Mapping(target = "actType", expression = "java(account.getActType().getName().toUpperCase())")
    AccountResponse fromAccount(Account account);
}
