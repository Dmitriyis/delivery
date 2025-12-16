package com.delivery.adapters.out.postgres.courier;

import com.delivery.adapters.out.postgres.order.OrderDataModelEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "storage_place")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StoragePlaceDataModelEntity {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "totalVolume")
    private Integer totalVolume;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderDataModelEntity order;

    @ManyToOne
    @JoinColumn(name = "courier_id", referencedColumnName = "id")
    private CourierDataModelEntity courier;
}
