INSERT INTO `sys_permission` (id,available,name,parent_id,parent_ids,permission,resource_type,url) VALUES ('1', 1, '用户管理', '0', '0/', 'user_info:view', 'menu', 'user_info/userList');
INSERT INTO `sys_permission` (id,available,name,parent_id,parent_ids,permission,resource_type,url) VALUES ('2', 1, '用户添加', '1', '0/1', 'user_info:add', 'button', 'user_info/userAdd');
INSERT INTO `sys_permission` (id,available,name,parent_id,parent_ids,permission,resource_type,url) VALUES ('3', 1, '用户删除', '1', '0/1', 'user_info:del', 'button', 'user_info/userDel');

INSERT INTO `sys_role` (id,available,role,description) VALUES ('1', 1, '管理员', 'admin');
INSERT INTO `sys_role` (id,available,role,description) VALUES ('2', 1, 'VIP会员', 'vip');

INSERT INTO `sys_user` (uid,name,username,password,salt,state) VALUES ('1', '管理员', 'admin','123456', null, '0');
INSERT INTO `sys_user` (uid,name,username,password,salt,state) VALUES ('2', '贾辉辉', 'phoema','123456', null, '0');
INSERT INTO `sys_user` (uid,name,username,password,salt,state) VALUES ('3', '贾辉辉2', 'phoema2','123456', null, '0');
INSERT INTO `sys_user` (uid,name,username,password,salt,state) VALUES ('4', '贾辉辉3', 'phoema3','123456', null, '0');
INSERT INTO `sys_user` (uid,name,username,password,salt,state) VALUES ('5', '贾辉辉4', 'phoema4', '123456', null, '0');

INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('1', '2');


INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '2');

-- ----------------------------
-- Records of pkage
-- ----------------------------
INSERT INTO `pkage` (id,name,price,cycleday,score) VALUES ('1', '套餐1', '0',30,20);
INSERT INTO `pkage` (id,name,price,cycleday,score) VALUES ('2', '套餐2', '1',50,20);
-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` (id,name,note,max,min,price,score) VALUES ('1', '黄瓜1号', 'note', '100', '10', '10',20);
INSERT INTO `product` (id,name,note,max,min,price,score) VALUES ('2', '香蕉2号', 'note', '200', '20', '10',20);
INSERT INTO `product` (id,name,note,max,min,price,score) VALUES ('3', '辣椒3号', 'note', '300', '30', '10',20);
-- ----------------------------
-- Records of pkage_product
-- ----------------------------
INSERT INTO `pkage_product` (id,pkageid,productid,max,min,price_cost) VALUES ('1','1', '1', '100', '10', '0');
INSERT INTO `pkage_product` (id,pkageid,productid,max,min,price_cost) VALUES ('2','1', '2', '200', '20', '0');
INSERT INTO `pkage_product` (id,pkageid,productid,max,min,price_cost) VALUES ('3','2', '1', '300', '30', '0');
INSERT INTO `pkage_product` (id,pkageid,productid,max,min,price_cost) VALUES ('4','2', '2', '100', '40', '0');
-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` (id,name,score,userid,createtime) VALUES ('1', '支付购买', '100', '1', '2016-08-03 16:56:08');
INSERT INTO `score` (id,name,score,userid,createtime) VALUES ('2', '活动获取', '10', '1', '2016-08-03 16:56:08');
INSERT INTO `score` (id,name,score,userid,createtime) VALUES ('3', '签到', '1', '1', '2016-08-01 16:56:08');
INSERT INTO `score` (id,name,score,userid,createtime) VALUES ('4', '签到', '1', '1', '2016-08-02 16:56:08');
INSERT INTO `score` (id,name,score,userid,createtime) VALUES ('5', '签到', '1', '1', '2016-08-03 16:56:08');
INSERT INTO `score` (id,name,score,userid,createtime) VALUES ('6', '签到', '1', '1', '2016-08-04 16:56:08');
-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` (productid,name,stock,userid,createtime) VALUES ('1', '仓库1', '20', '1', null);
INSERT INTO `stock` (productid,name,stock,userid,createtime) VALUES ('2', '仓库2', '40', '1', null);
INSERT INTO `stock` (productid,name,stock,userid,createtime) VALUES ('3', '辣椒1号', '20', '1', '2016-08-03 17:14:30');
-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('1'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('2'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('3'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('4'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('5'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('6'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('7'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('8'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('9'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('10'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('11'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('12'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('13'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('14'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
INSERT INTO `farm` ( `id` , `createtime` , `feiwodu` , `haichongdu`, `name`, `pkageid`, `shirundu`, `state`,`updatetime` , `userid`, `zacaodu` ) VALUES 	('15'	, '2016-08-24 09:18:12', '10', '20',	 '我的农田1', '1', '30', '1', null, '1', '40');
