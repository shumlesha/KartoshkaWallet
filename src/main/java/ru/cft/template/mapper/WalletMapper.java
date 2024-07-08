package ru.cft.template.mapper;
import org.mapstruct.Mapper;
import ru.cft.template.dto.wallet.WalletDto;
import ru.cft.template.mapper.base.BaseMapper;
import ru.cft.template.models.Wallet;

@Mapper(componentModel = "spring")
public interface WalletMapper extends BaseMapper<Wallet, WalletDto> { }