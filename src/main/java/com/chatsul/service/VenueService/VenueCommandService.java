package com.chatsul.service.VenueService;

import com.chatsul.domain.Member;
import com.chatsul.domain.Venue;
import com.chatsul.web.dto.VenueRequestDTO;

public interface VenueCommandService {
	Venue createVenue(Member member, VenueRequestDTO request);
}