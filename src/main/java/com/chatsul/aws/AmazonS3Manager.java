package com.chatsul.aws;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.chatsul.config.AmazonConfig;
import com.chatsul.domain.Uuid;
import com.chatsul.repository.UuidRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

	private final AmazonS3 amazonS3;
	private final AmazonConfig amazonConfig;
	private final UuidRepository uuidRepository;

	public String uploadFile(String keyName, MultipartFile file) {
		System.out.println(keyName);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentDisposition("inline");

		// 업로드된 파일의 Content-Type을 가져와서 설정
		String contentType = file.getContentType();
		if (contentType != null && contentType.startsWith("image")) {
			metadata.setContentType(contentType);  // 이미지 파일이라면 해당 contentType을 설정
		}

		try {
			amazonS3.putObject(
				new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
		} catch (IOException e) {
			log.error("error at AmazonS3Manager uploadFile : {}", (Object)e.getStackTrace());
		}
		return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
	}

	public String generateMenuKeyName(Uuid uuid) {
		return amazonConfig.getMenuPath() + '/' + uuid.getUuid();
	}

	public String generateLostItemKeyName(Uuid uuid) {
		return amazonConfig.getLostItemPath() + '/' + uuid.getUuid();
	}
}
