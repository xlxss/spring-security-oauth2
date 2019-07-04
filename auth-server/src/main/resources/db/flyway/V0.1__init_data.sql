-- password=admin
INSERT INTO `sys_user` (`id`, `username`, `password`, `name`, `email`, `mobile`, `active`, `create_time`, `update_time`) VALUES ('1', 'admin', '$2a$10$8pj./OmeQQjj3Jy6EeUTA.48gswOEo7A/BnvMaS42b0WMRl.vDsRS', '管理员', 'aa@bb.cc', '13482832735', '', '2019-07-04 16:02:51', '2019-07-04 16:02:51');
INSERT INTO `sys_role` (`id`, `code`, `name`, `description`, `create_time`, `update_time`) VALUES ('1', 'admin', '管理员', NULL, '2019-07-04 16:38:03', '2019-07-04 16:38:03');
INSERT INTO `sys_authority` (`id`, `code`, `name`, `description`, `create_time`, `update_time`) VALUES ('1', 'auth_1', '权限_1', NULL, '2019-07-04 16:03:06', '2019-07-04 16:03:06');
INSERT INTO `sys_authority` (`id`, `code`, `name`, `description`, `create_time`, `update_time`) VALUES ('2', 'auth_2', '权限_2', NULL, '2019-07-04 16:03:09', '2019-07-04 16:03:09');
INSERT INTO `sys_authority` (`id`, `code`, `name`, `description`, `create_time`, `update_time`) VALUES ('3', 'auth_3', '权限_3', NULL, '2019-07-04 16:03:12', '2019-07-04 16:03:12');
INSERT INTO `sys_authority` (`id`, `code`, `name`, `description`, `create_time`, `update_time`) VALUES ('4', 'auth_4', '权限_4', NULL, '2019-07-04 16:03:15', '2019-07-04 16:03:15');
INSERT INTO `sys_authority` (`id`, `code`, `name`, `description`, `create_time`, `update_time`) VALUES ('5', 'auth_5', '权限_5', NULL, '2019-07-04 16:03:19', '2019-07-04 16:03:19');
INSERT INTO `sys_role_authority` (`role_id`, `authority_id`) VALUES ('1', '1');
INSERT INTO `sys_role_authority` (`role_id`, `authority_id`) VALUES ('1', '3');
INSERT INTO `sys_role_authority` (`role_id`, `authority_id`) VALUES ('1', '5');
INSERT INTO `sys_user_role` (`id`, `role_id`, `user_id`) VALUES ('1', '1', '1');
INSERT INTO `sys_user_authority` (`id`, `authority_id`, `user_id`) VALUES ('1', '5', '1');
