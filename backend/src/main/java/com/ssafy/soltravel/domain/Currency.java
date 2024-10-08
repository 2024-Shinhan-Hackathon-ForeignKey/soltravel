package com.ssafy.soltravel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class Currency {

    @Id
    private Long id;

    private String currencyCode;

    private String currencyName;

    @OneToMany(mappedBy = "currency")
    private List<ForeignAccount> foreignAccounts;
}
