package com.chatsul.web.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequestDTO {

	List<MultipartFile> imageUrl;

}