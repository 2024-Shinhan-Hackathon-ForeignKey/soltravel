package com.ssafy.soltravel.dto.account_book;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistodySaveRequestDto {

  private String accountNo;
  private String store;
  private Double paid;
  private List<Item> items;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class Item {
    private String item;
    private Double price;
    private Integer quantity;
  }
}
