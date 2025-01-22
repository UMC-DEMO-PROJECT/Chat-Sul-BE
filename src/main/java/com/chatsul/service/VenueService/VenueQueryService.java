package com.chatsul.service.VenueService;

import java.util.List;

import com.chatsul.domain.Menu;
import com.chatsul.domain.Venue;

public interface VenueQueryService {

	List<Venue> getAllLocationList();

	Venue getVenueInfo(Long venueId);

	List<Menu> getMenuImageList(Long venueId);
}