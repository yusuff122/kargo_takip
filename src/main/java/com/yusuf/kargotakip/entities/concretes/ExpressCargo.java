package com.yusuf.kargotakip.entities.concretes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("EXPRESS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ExpressCargo extends Cargo {
}
