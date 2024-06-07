package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;//giảm bớt mã boilerplate bằng cách tự động tạo các phương thức getter cho các trường của lớp.
import lombok.RequiredArgsConstructor;//tạo một constructor với các đối số cho tất cả các trường final.
import lombok.Setter;// tạo các phương thức setter cho các trường của lớp.

import javax.persistence.*;//sử dụng để ánh xạ lớp với bảng cơ sở dữ liệu.
import java.util.LinkedHashSet;// mở rộng giao diện Set và duy trì thứ tự chèn.
import java.util.Set;//đại diện cho một tập hợp các phần tử duy nhất.

@Setter//cho biết các phương thức setter nên được tạo cho tất cả các trường trong lớp.
@Getter//tạo các phương thức getter cho tất cả các trường.
@RequiredArgsConstructor//yêu cầu trình biên dịch tạo một constructor sử dụng các đối số cho tất cả các trường final.
@Entity//đánh dấu lớp là một entity có thể được lưu trữ trong bảng cơ sở dữ liệu.
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    private String userName;
    private String encryptPassword;
    private String email;
    private Boolean isEnable;

    @ManyToMany
    @JoinTable(name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();
}
