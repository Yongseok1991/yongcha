package yong.app.domain.user;

import yong.app.domain.auth.RoleType;

import java.util.List;

public record YongUserRecord(String email, String password, List<RoleType> roleTypes) {}
