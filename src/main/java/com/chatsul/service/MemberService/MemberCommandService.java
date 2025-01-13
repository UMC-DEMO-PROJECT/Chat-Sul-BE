package com.chatsul.service.MemberService;

import com.chatsul.domain.Member;
import com.chatsul.web.dto.MemberRequestDTO;

public interface MemberCommandService {
	Member joinMember(MemberRequestDTO.JoinDTO request);
}
