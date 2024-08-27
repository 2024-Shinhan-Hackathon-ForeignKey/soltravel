package com.ssafy.soltravel.service.account_book;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClovaOcrService {

  private final Map<String, String> apiKeys;
  private final static String BASE_URL="asd";

}
