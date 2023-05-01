package com.sparta.spring_post.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @Column(name = "user_name", nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @JsonBackReference
    @OneToMany(mappedBy = "Users", cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "Users", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "Users", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikeList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "Users", cascade = CascadeType.REMOVE)
    private List<CommentLike> commentLikeList = new ArrayList<>();

    public Users(String username, String password, RoleType role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
