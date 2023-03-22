package com.ykmxxi.aligong.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ykmxxi.aligong.constant.PlaceType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Table(indexes = {
	@Index(columnList = "placeName"),
	@Index(columnList = "address"),
	@Index(columnList = "phoneNumber"),
	@Index(columnList = "createdAt"),
	@Index(columnList = "modifiedAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Place {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(nullable = false, columnDefinition = "varchar(20) default 'COMMON'")
	@Enumerated(EnumType.STRING)
	private PlaceType placeType;

	@Setter
	@Column(nullable = false)
	private String placeName;

	@Setter
	@Column(nullable = false)
	private String address;

	@Setter
	@Column(nullable = false)
	private String phoneNumber;

	@Setter
	@Column(nullable = false, columnDefinition = "integer default 0")
	private Integer capacity;

	@Setter
	private String memo;

	@Column(nullable = false, insertable = false, updatable = false,
		columnDefinition = "datetime default CURRENT_TIMESTAMP")
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(nullable = false, insertable = false, updatable = false,
		columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	@LastModifiedDate
	private LocalDateTime modifiedAt;

	@ToString.Exclude
	@OrderBy("id")
	@OneToMany(mappedBy = "place")
	private final Set<Event> events = new LinkedHashSet<>();

	@ToString.Exclude
	@OrderBy("id")
	@OneToMany(mappedBy = "place")
	private final Set<AdminPlaceMap> adminPlaceMaps = new LinkedHashSet<>();

	protected Place() {
	}

	protected Place(
		PlaceType placeType,
		String placeName,
		String address,
		String phoneNumber,
		Integer capacity,
		String memo
	) {
		this.placeType = placeType;
		this.placeName = placeName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.capacity = capacity;
		this.memo = memo;
	}

	public static Place of(
		PlaceType placeType,
		String placeName,
		String address,
		String phoneNumber,
		Integer capacity,
		String memo
	) {
		return new Place(placeType, placeName, address, phoneNumber, capacity, memo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		return id != null && id.equals(((Place)obj).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(placeName, address, phoneNumber, createdAt, modifiedAt);
	}

}
