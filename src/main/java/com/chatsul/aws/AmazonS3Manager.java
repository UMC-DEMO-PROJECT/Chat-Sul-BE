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

	public String uploadFile(String KeyName, MultipartFile file) throws IOException {
		System.out.println(KeyName);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), KeyName, file.getInputStream(), metadata));

		return amazonS3.getUrl(amazonConfig.getBucket(), KeyName).toString();
	}

	public String generateMenuKeyName(Uuid uuid) {
		return amazonConfig.getMenuPath() + '/' + uuid.getUuid();
	}
}
