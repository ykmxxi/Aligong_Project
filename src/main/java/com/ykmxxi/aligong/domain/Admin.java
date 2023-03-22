package com.ykmxxi.aligong.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Table(indexes = {
	@Index(columnList = "phoneNumber"),
	@Index(columnList = "createdAt"),
	@Index(columnList = "modifiedAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(nullable = false, unique = true)
	private String email;

	@Setter
	@Column(nullable = false, unique = true)
	private String nickname;

	@Setter
	@Column(nullable = false)
	private String password;

	@Setter
	@Column(nullable = false)
	private String phoneNumber;

	@Setter
	private String memo;

	@ToString.Exclude // 순환참조 예방, OneToMany
	@OrderBy("id")
	@OneToMany(mappedBy = "admin")
	private final Set<AdminPlaceMap> adminPlaceMaps = new LinkedHashSet<>();

	@Column(nullable = false, insertable = false, updatable = false,
		columnDefinition = "datetime default CURRENT_TIMESTAMP")
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(nullable = false, insertable = false, updatable = false,
		columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	@LastModifiedDate
	private LocalDateTime modifiedAt;

	protected Admin() {
	}

	protected Admin(String email, String nickname, String password, String phoneNumber, String memo) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.memo = memo;
	}

	public static Admin of(String email, String nickname, String password, String phoneNumber, String memo) {
		return new Admin(email, nickname, password, phoneNumber, memo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		return id != null && id.equals(((Admin)obj).getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, nickname, phoneNumber, createdAt, modifiedAt);
	}

}
