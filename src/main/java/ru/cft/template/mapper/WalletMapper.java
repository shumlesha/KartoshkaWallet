package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import ru.cft.template.dto.wallet.WalletDto;
import ru.cft.template.entity.Wallet;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletDto toDTO(Wallet wallet);
}