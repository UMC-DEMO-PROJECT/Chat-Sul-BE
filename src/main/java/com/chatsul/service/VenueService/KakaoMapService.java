package com.chatsul.service.VenueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoMapService {

	// 카카오맵 API 키
	@Value("${kakao.api-key}")
	private String apiKey;

	public Map<String, Double> getCoordinates(String address) {
		// 카카오맵 API 호출 URL
		String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;

		// API 호출을 위한 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK " + apiKey);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		// API 호출
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

		// 반환된 좌표에서 위도, 경도 추출
		Map<String, Double> coordinates = new HashMap<>();
		if (response.getBody() != null) {
			List<Map<String, Object>> documents = (List<Map<String, Object>>)response.getBody().get("documents");
			if (documents != null && !documents.isEmpty()) {
				Map<String, Object> firstResult = documents.get(0);

				// 위도, 경도 정보는 문자열로 반환됨
				String latitudeStr = (String)firstResult.get("y");
				String longitudeStr = (String)firstResult.get("x");

				// DB에 저장하기 위해 String을 Double로 변환
				try {
					Double latitude = Double.parseDouble(latitudeStr);
					Double longitude = Double.parseDouble(longitudeStr);

					coordinates.put("latitude", latitude);
					coordinates.put("longitude", longitude);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
		return coordinates;
	}
}
