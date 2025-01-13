package com.chatsul.service.AuthService;

import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.chatsul.apiPayload.exception.GeneralException;
import com.chatsul.converter.MemberConverter;
import com.chatsul.domain.Member;
import com.chatsul.jwt.principal.PrincipalDetails;
import com.chatsul.oauth.userInfo.KakaoUserInfo;
import com.chatsul.oauth.userInfo.NaverUserInfo;
import com.chatsul.oauth.userInfo.OAuth2UserInfo;
import com.chatsul.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 소셜로그인 유저 처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo userInfo = getOAuth2UserInfo(provider, oAuth2User.getAttributes());

        // 기존 정보가 있으면 로그인, 없는 경우 임시 회원가입
        Member member = saveOrUpdateMember(userInfo);
        
        // OAuth2User 반환
        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }

    private OAuth2UserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes) {
        return switch (provider) {
            case "kakao" -> new KakaoUserInfo(attributes);
            case "naver" -> new NaverUserInfo((Map<String, Object>) attributes.get("response"));
            default -> throw new GeneralException(ErrorStatus.INVALID_PROVIDER);
        };
    }

    private Member saveOrUpdateMember(OAuth2UserInfo userInfo) {
        String provider = userInfo.getProvider();
        String providerId = userInfo.getProviderId();

        Optional<Member> optionalMember = memberRepository.findByProviderAndProviderId(provider, providerId);

        if (optionalMember.isEmpty()) {
            Member member = MemberConverter.toSocialMember(provider, providerId, passwordEncoder.encode(userInfo.getProvider()) + UUID.randomUUID());
            return memberRepository.save(member);
        }

        return optionalMember.get();
    }
}
