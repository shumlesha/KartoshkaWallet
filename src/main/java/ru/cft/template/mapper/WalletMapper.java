package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import ru.cft.template.dto.wallet.WalletDTO;
import ru.cft.template.models.Wallet;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletDTO toDTO(Wallet wallet);
}