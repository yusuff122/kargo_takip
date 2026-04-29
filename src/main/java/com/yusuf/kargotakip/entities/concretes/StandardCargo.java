package com.yusuf.kargotakip.entities.concretes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("STANDARD")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class StandardCargo extends Cargo {
}
