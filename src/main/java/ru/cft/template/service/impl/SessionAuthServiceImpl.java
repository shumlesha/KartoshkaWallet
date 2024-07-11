package ru.cft.template.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.dto.session.CreateSessionRequest;
import ru.cft.template.dto.session.RefreshSessionRequest;
import ru.cft.template.dto.session.SessionDto;
import ru.cft.template.dto.session.TokenResponse;
import ru.cft.template.exception.token.InvalidRefreshTokenException;
import ru.cft.template.mapper.SessionMapper;
import ru.cft.template.entity.Session;
import ru.cft.template.repository.SessionRepository;
import ru.cft.template.security.JwtTokenProvider;
import ru.cft.template.security.SessionUser;
import ru.cft.template.service.SessionAuthService;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionAuthServiceImpl implements SessionAuthService {
    private final SessionRepository sessionRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final SessionMapper sessionMapper;

    @Override
    @Transactional
    public SessionDto createSession(CreateSessionRequest createSessionRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        createSessionRequest.getEmail(),
                        createSessionRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SessionUser sessionUser = (SessionUser) authentication.getPrincipal();

        // return sessionUser.getSession();
        Session session = sessionUser.getSession();

        return sessionMapper.toDTO(session, jwtTokenProvider.getExpirationDate(session.getAccessToken()));
    }

    @Override
    public SessionDto getCurrentSession(SessionUser sessionUser) {
        Session session = sessionUser.getSession();
        log.info("All good!");
        return sessionMapper.toDTO(session, jwtTokenProvider.getExpirationDate(session.getAccessToken()));
    }

    @Override
    @Transactional
    public SessionDto refreshSession(RefreshSessionRequest refreshSessionRequest) {
        Session session = sessionRepository.findByRefreshToken(refreshSessionRequest.getRefreshToken())
                .orElseThrow(InvalidRefreshTokenException::new);

        TokenResponse tokenResponse = jwtTokenProvider.refreshUserTokens(session.getRefreshToken(), session.getUser());

        session.setAccessToken(tokenResponse.getAccessToken());
        session.setRefreshToken(tokenResponse.getRefreshToken());
        session.setEnabled(true);

        Session savedSession = sessionRepository.saveAndFlush(session);

        return sessionMapper.toDTO(savedSession, jwtTokenProvider.getExpirationDate(savedSession.getAccessToken()));
    }
}
