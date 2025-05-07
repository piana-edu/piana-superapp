package ir.piana.boot.spuerapp.auth.data.service;

import ir.piana.boot.spuerapp.auth.data.entity.MobileAsUserEntity;
import ir.piana.boot.spuerapp.auth.data.repository.MobileAsUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserManagementService {
    private final MobileAsUserRepo mobileAsUserRepo;

    @Transactional
    public MobileAsUserEntity findOrRegister(String mobile) {
        Optional<MobileAsUserEntity> byMobile = mobileAsUserRepo.findByMobile(mobile);
        if (byMobile.isPresent()) {
            return byMobile.get();
        }

        MobileAsUserEntity newMobileAsUserEntity = new MobileAsUserEntity();
        newMobileAsUserEntity.setMobile(mobile);
        newMobileAsUserEntity = mobileAsUserRepo.save(newMobileAsUserEntity);
        return newMobileAsUserEntity;
    }
}
