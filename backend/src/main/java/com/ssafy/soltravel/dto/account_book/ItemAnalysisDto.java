package com.ssafy.soltravel.dto.account_book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemAnalysisDto {

  private String item;
  private double price;
}
